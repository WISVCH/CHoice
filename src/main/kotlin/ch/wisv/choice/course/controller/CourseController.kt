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
import ch.wisv.choice.course.model.Study
import ch.wisv.choice.course.model.StudyProgram
import ch.wisv.choice.course.service.CourseService
import ch.wisv.choice.util.ResponseEnityBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/v1/course")
@CrossOrigin
class CourseController

    constructor(@Autowired val courseService: CourseService) {

    /**
     * Get list of courses.
     *
     * @return ResponseEntity<*>
     */
    @GetMapping
    fun getAllCourses(): ResponseEntity<*> {
        return ResponseEnityBuilder.createResponseEntity(HttpStatus.OK, "List of all courses", courseService.getAllCourses())
    }

    /**
     * Get list of all active courses.
     *
     * @return ResponseEntity<*>
     */
    @GetMapping("/active")
    fun getAllActiveCourse(): ResponseEntity<*> {
        var courses = courseService.getAllCourses()
        courses = filterNoActiveCourses(courses)

        return ResponseEnityBuilder.createResponseEntity(HttpStatus.OK, "List of all active courses", courses)
    }

    /**
     * Get list of all active courses that match the search query.
     *
     * @param request: HttpServletRequest
     *
     * @return ResponseEntity<*>
     */
    @GetMapping("/active/search")
    fun searchForCourse(request: HttpServletRequest): ResponseEntity<*> {
        var courses = courseService.getAllCourses()
        courses = filterNoActiveCourses(courses)

        if (request.getParameter("study") != null && request.getParameter("study") != "") {
            val studyEnum = Study.valueOf(request.getParameter("study"))

            courses = courses.filter { item -> item.study!! == studyEnum }
        }

        if (request.getParameter("program") != null && request.getParameter("program") != "") {
            val studyProgramEnum = StudyProgram.valueOf(request.getParameter("program"))

            courses = courses.filter { item -> item.studyProgram!! == studyProgramEnum }
        }

        if (request.getParameter("query") != null && request.getParameter("query") != "") {
            val searchParts = request.getParameter("query").split(" ")

            courses = courses.filter { (code, name) ->
                searchParts.parallelStream().anyMatch { item ->
                    code.toLowerCase().contains(item.toLowerCase()) || name.toLowerCase().contains(item.toLowerCase())
                }
            }
        }

        return ResponseEnityBuilder.createResponseEntity(HttpStatus.OK, "List of all courses that match the search query", courses)
    }

    /**
     * Get course by course code.
     *
     * @return ResponseEntity<*>
     */
    @GetMapping("/{code}")
    fun getCourseByCode(@PathVariable code: String): ResponseEntity<*> {
        val course = courseService.getCourseByCourseCode(code)

        return if (course != null) {
            ResponseEnityBuilder.createResponseEntity(HttpStatus.OK, "Course $code", course)
        } else {
            ResponseEnityBuilder.createResponseEntity(HttpStatus.CONFLICT, "Course with course code $code not found.")
        }
    }

    /**
     * Get predecessor of a course.
     *
     * @return ResponseEntity<*>
     */
    @GetMapping("/{code}/predecessor")
    fun getCoursePredecessorByCode(@PathVariable code: String): ResponseEntity<*> {
        val predecessor = courseService.getCoursePredecessorByCourseCode(code)

        return if (predecessor != null) {
            ResponseEnityBuilder.createResponseEntity(HttpStatus.OK, "Predecessor of Course $code", predecessor)
        } else {
            ResponseEnityBuilder.createResponseEntity(HttpStatus.CONFLICT, "Course does not have a predecessor.")
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
                predecessorCourses.add(courseService.getCourseByCourseCode(item.predecessor!!)!!)
            }
        }

        return courses.filter { item -> !predecessorCourses.contains(item) }
    }
}
