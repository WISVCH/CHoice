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
import ch.wisv.choice.util.ResponseEntityBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/exam", produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
@CrossOrigin
class ExamController(val examService: ExamService,
                     val courseService: CourseService) {

    @GetMapping()
    fun getExams(): ResponseEntity<*> {
        val exams = examService.getExams()

        // Remove ByteArray
        exams.forEach { exam -> exam.document!!.bytes = kotlin.ByteArray(0) }

        return ResponseEntityBuilder.createResponseEntity(HttpStatus.OK, "Successful", exams)
    }

    @GetMapping("/course/{code}")
    fun getExamsByCourse(@PathVariable code: String): Collection<Exam>
            = examService.getExamsByCourse(code)

    @GetMapping("/course/{code}/including")
    fun getExamByCourseAndPredecessors(@PathVariable code: String): ResponseEntity<*> {
        val course = courseService.getCourseByCourseCode(code)
        var exams: HashSet<Exam> = HashSet()

        if (course != null) {
            exams = getPredecessorsExams(course, HashSet())

            // Remove ByteArray
            exams.forEach { exam -> exam.document!!.bytes = kotlin.ByteArray(0) }
        }

        return ResponseEntityBuilder.createResponseEntity(HttpStatus.OK, "Successful", exams)
    }

    @GetMapping("/{examId}")
    fun getExamById(@PathVariable examId: Long): Exam
            = examService.getExamById(examId)

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
