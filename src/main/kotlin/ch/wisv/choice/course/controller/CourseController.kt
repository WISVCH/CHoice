/*
 * Copyright (c) 2016  W.I.S.V. 'Christiaan Huygens'
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.wisv.choice.course.controller

import ch.wisv.choice.course.model.Course
import ch.wisv.choice.course.model.CourseDTO
import ch.wisv.choice.course.model.Study
import ch.wisv.choice.course.model.StudyProgram
import ch.wisv.choice.course.service.CourseService
import ch.wisv.choice.exam.model.Exam
import ch.wisv.choice.exam.service.ExamService
import ch.wisv.choice.util.CHoiceException
import ch.wisv.choice.util.ResponseEntityBuilder.Companion.createResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/v1/course")
class CourseController(val courseService: CourseService, val examService: ExamService) {

    /**
     * Get list of courses.
     *
     * @return ResponseEntity<*>
     */
    @GetMapping
    fun getAllCourses(): ResponseEntity<*> =
            createResponseEntity(HttpStatus.OK, "List of all courses", courseService.getAllCourses())

    /**
     * Get list of all active courses.
     *
     * @return ResponseEntity<*>
     */
    @GetMapping("/active")
    fun getAllActiveCourse(): ResponseEntity<*> {
        var courses = courseService.getAllCourses()
        courses = filterNoActiveCourses(courses)

        val coursesDTO = courses.stream().map { course -> CourseDTO(course, getPredecessorsExams(course)) }
                .collect(Collectors.toList())

        return createResponseEntity(HttpStatus.OK, "List of all active courses", coursesDTO)
    }

    /**
     * Get list of all active courses that match the search query.
     *
     * @param request: HttpServletRequest
     *
     * @return ResponseEntity<*>
     */
    @GetMapping("/active/search")
    fun searchActiveCourse(request: HttpServletRequest): ResponseEntity<*> {
        var courses = courseService.getAllCourses()
        courses = filterNoActiveCourses(courses)

        try {
            if (request.getParameter("study") != null && request.getParameter("study") != "") {
                val studyEnum = Study.valueOf(request.getParameter("study"))

                courses = courses.filter { item -> item.study == studyEnum }
            }

            if (request.getParameter("program") != null && request.getParameter("program") != "") {
                val studyProgramEnum = StudyProgram.valueOf(request.getParameter("program"))

                courses = courses.filter { item -> item.studyProgram == studyProgramEnum }
            }
        } catch (e: IllegalArgumentException) {
            return createResponseEntity(HttpStatus.BAD_REQUEST, "Invalid study or program input.")
        }

        if (request.getParameter("query") != null && request.getParameter("query") != "") {
            val searchParts = request.getParameter("query").split(" ")

            courses = courses.filter { (code, name) ->
                searchParts.parallelStream().anyMatch { item ->
                    code.lowercase().contains(item.lowercase()) || name.lowercase().contains(item.lowercase())
                }
            }
        }

        val coursesDTO = courses.stream().map { course -> CourseDTO(course, getPredecessorsExams(course)) }
                .collect(Collectors.toList())

        coursesDTO.forEachIndexed { index, courseDTO -> coursesDTO[index].exam = courseDTO.exam?.sortedByDescending{it.date} }

        return createResponseEntity(HttpStatus.OK, "List of all courses that match the search query", coursesDTO)
    }

    /**
     * Get course by course code.
     *
     * @return ResponseEntity<*>
     */
    @GetMapping("/{code}")
    fun getCourseByCode(@PathVariable code: String): ResponseEntity<*> {
        return try {
            val course = courseService.getCourseByCourseCode(code)

            createResponseEntity(HttpStatus.OK, "Course $code", course)
        } catch (e: CHoiceException) {
            createResponseEntity(HttpStatus.BAD_REQUEST, e.message!!)
        }
    }

    /**
     * Get predecessor of a course.
     *
     * @return ResponseEntity<*>
     */
    @GetMapping("/{code}/predecessor")
    fun getCoursePredecessorByCode(@PathVariable code: String): ResponseEntity<*> {
        return try {
            val predecessor = courseService.getCoursePredecessorByCourseCode(code)

            if (predecessor != null) {
                createResponseEntity(HttpStatus.OK, "Predecessor of Course $code", predecessor)
            } else {
                createResponseEntity(HttpStatus.CONFLICT, "Course does not have a predecessor.")
            }
        } catch (e: CHoiceException) {
            createResponseEntity(HttpStatus.BAD_REQUEST, e.message!!)
        }
    }

    /**
     * Filter the no active courses.
     *
     * @param courses: Collection<Course>
     *
     * @return Collection<Course>
     */
    private fun filterNoActiveCourses(courses: Collection<Course>): Collection<Course> {
        val predecessorCourses = LinkedHashSet<Course>()

        courses.forEach { item ->
            if (item.predecessor != null) {
                predecessorCourses.add(courseService.getCourseByCourseCode(item.predecessor!!))
            }
        }

        return courses.filter { item -> !predecessorCourses.contains(item) }
    }

    private fun getPredecessorsExams(course: Course) = getPredecessorsExams(course, HashSet())

    private fun getPredecessorsExams(course: Course?, exams: HashSet<Exam>): Set<Exam> {
        if (course != null) {
            val predecessorExams = examService.getExamsByCourse(course.code)
            exams.addAll(predecessorExams)

            if (course.predecessor != null) {
                val predecessor = courseService.getCourseByCourseCode(course.predecessor!!)
                getPredecessorsExams(predecessor, exams)
            }
        }

        return sortExamByCourseCodeAndDate(exams)
    }

    private fun sortExamByCourseCodeAndDate(exams: HashSet<Exam>): Set<Exam> {
        return exams.stream().sorted { o1, o2 ->
            var compare = o1.course.code.compareTo(o2.course.code)
            if (compare == 0) {
                compare = if (o1.date.isBefore(o2.date)) -1 else 1
            }

            compare
        }
        .collect(Collectors.toSet())
    }
}
