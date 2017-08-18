package ch.wisv.choice.integration

import ch.wisv.choice.course.model.Course
import ch.wisv.choice.course.service.CourseService
import ch.wisv.choice.document.service.DocumentService
import ch.wisv.choice.exam.model.Exam
import ch.wisv.choice.exam.service.ExamService
import io.restassured.RestAssured.given
import org.apache.http.HttpStatus
import org.hamcrest.Matchers.hasItem
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

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

        //@formatter:off
        given().
        `when`().
            get("/exam").
        then().
            statusCode(HttpStatus.SC_OK).
            body("name", hasItem(exam.name)).
            body("course.code", hasItem(exam.course.code))
        //@formatter:on
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
