package ch.wisv.choice.integration

import io.restassured.RestAssured.given
import org.apache.http.HttpStatus
import org.junit.Test

class ExamControllerTest : IntegrationTest() {
    @Test
    fun createExam() {

        //@formatter:off
        given().
        `when`().
            get("/exam").
        then().
            statusCode(HttpStatus.SC_OK)

        //@formatter:on
    }

    @Test
    fun getExams() {
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