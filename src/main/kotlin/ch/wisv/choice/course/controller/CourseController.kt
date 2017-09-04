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

    @GetMapping
    fun readAllCourses(): ResponseEntity<*> {
        return ResponseEnityBuilder.createResponseEntity(HttpStatus.OK, "List of all courses", courseService.getAllCourses())
    }

    @GetMapping("/active")
    fun getAllActiveCourse(): ResponseEntity<*> {
        var courses = courseService.getAllCourses()
        courses = filterNoActiveCourses(courses)

        return ResponseEnityBuilder.createResponseEntity(HttpStatus.OK, "List of all active courses", courses)
    }

    @GetMapping("/search/active")
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

    @GetMapping("/{code}")
    fun getCourseByCode(@PathVariable code: String): Course?
            = courseService.getCourseByCourseCode(code)

    @GetMapping("/{code}/predecessor")
    fun getCoursePredecessorByCode(@PathVariable code: String): Course?
            = courseService.getCoursePredecessorByCourseCode(code)


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
