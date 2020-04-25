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

package ch.wisv.choice.exam.controller

import ch.wisv.choice.course.model.Course
import ch.wisv.choice.course.service.CourseService
import ch.wisv.choice.exam.model.Exam
import ch.wisv.choice.exam.service.ExamService
import ch.wisv.choice.util.CHoiceException
import ch.wisv.choice.util.ResponseEntityBuilder.Companion.createResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/exam", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class ExamController(val examService: ExamService,
                     val courseService: CourseService) {

    @GetMapping()
    fun getExams(): ResponseEntity<*>
            = createResponseEntity(HttpStatus.OK, "List of all the exams", examService.getExams())

    @GetMapping("/course/{code}")
    fun getExamsByCourse(@PathVariable code: String): ResponseEntity<*> {
        return try {
            createResponseEntity(HttpStatus.OK, "List of all the exams of course " + code, examService.getExamsByCourse(code))
        } catch (e: CHoiceException) {
            createResponseEntity(HttpStatus.NOT_FOUND, e.message!!)
        }
    }

    @GetMapping("/course/{code}/including")
    fun getExamByCourseAndPredecessors(@PathVariable code: String): ResponseEntity<*> {
        return try {
            val course = courseService.getCourseByCourseCode(code)
            val exams: HashSet<Exam> = getPredecessorsExams(course, HashSet())

            createResponseEntity(HttpStatus.OK, "List of all the exams of course $code including exams of predecessors", exams)
        } catch (e: CHoiceException) {
            createResponseEntity(HttpStatus.NOT_FOUND, e.message!!)
        }
    }

    @GetMapping("/{examId}")
    fun getExamById(@PathVariable examId: Long): ResponseEntity<*> {
        return try {
            createResponseEntity(HttpStatus.OK, "Data about exam " + examId, examService.getExamById(examId))
        } catch (e: CHoiceException) {
            createResponseEntity(HttpStatus.NOT_FOUND, e.message!!)
        }
    }

    private fun getPredecessorsExams(course: Course?, exams: HashSet<Exam>): HashSet<Exam> {
        if (course != null) {
            val predecessorExams = examService.getExamsByCourse(course.code)
            exams.addAll(predecessorExams)

            if (course.predecessor != null) {
                val predecessor = courseService.getCourseByCourseCode(course.predecessor!!)
                getPredecessorsExams(predecessor, exams)
            }
        }

        return exams
    }
}
