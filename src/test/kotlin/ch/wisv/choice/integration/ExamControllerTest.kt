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

package ch.wisv.choice.integration

import ch.wisv.choice.course.model.Course
import ch.wisv.choice.course.service.CourseService
import ch.wisv.choice.document.model.Document
import ch.wisv.choice.document.service.DocumentService
import ch.wisv.choice.exam.model.Exam
import ch.wisv.choice.exam.service.ExamService
import io.restassured.RestAssured.given
import org.apache.http.HttpStatus
import org.hamcrest.Matchers.hasItem
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

@ActiveProfiles("test")
class ExamControllerTest : IntegrationTest() {

    @Autowired
    lateinit var examService : ExamService
    @Autowired
    lateinit var courseService : CourseService
    @Autowired
    lateinit var documentService : DocumentService

    @Test
    fun createExam() {

    }

    @Test
    fun getExams() {
        val course = Course("TI0001", "Test Course")
        courseService.createCourse(course)

        val exam = Exam(null, course, LocalDate.now(), "Examen")
        examService.createExam(exam)

        val document = Document(1, ByteArray(0), "Examen", exam)
        documentService.storeDocument(document)

        //@formatter:off
        given().
        `when`().
            get("/api/v1/exam").
        then().
            statusCode(HttpStatus.SC_OK).
            body("content.name", hasItem(exam.name))
        // @formatter:on
    }

    @Test
    fun getExamsByCourse() {
    }

    @Test
    fun getExamById() {
    }

    @Test
    fun deleteExam() {
    }

    @Test
    fun deleteExamsByCourseCode() {
    }

}
