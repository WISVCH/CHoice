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

import io.restassured.RestAssured.given
import org.apache.http.HttpStatus
import org.hamcrest.Matchers.*
import org.junit.Test
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class ExamControllerTest : IntegrationTest() {

    @Test
    fun getExams() {
        //@formatter:off
        given().
        `when`().
            get("/api/v1/exam").
        then().
            statusCode(HttpStatus.SC_OK).
            body("content.name", hasItems("Tentamen", "Hertentamen"))
        // @formatter:on
    }

    @Test
    fun getExamsByCourse() {
        //@formatter:off
        given().
        `when`().
            get("/api/v1/exam/course/TI0011").
        then().
            statusCode(HttpStatus.SC_OK).
            body("content.name", hasItem("Tentamen"))
        // @formatter:on
    }

    @Test
    fun getExamsByCourseNotExists() {
        //@formatter:off
        given().
        `when`().
            get("/api/v1/exam/course/TB1234").
        then().
            statusCode(HttpStatus.SC_NOT_FOUND)
        // @formatter:on
    }

    @Test
    fun getExamsByCourseAndPredecessors() {
        //@formatter:off
        given().
        `when`().
            get("/api/v1/exam/course/TI0012/including").
        then().
            statusCode(HttpStatus.SC_OK).
            body("content.name", hasItem("Tentamen"))
        // @formatter:on
    }

    @Test
    fun getExamsByCourseAndPredecessorsIncludingNotExists() {
        //@formatter:off
        given().
        `when`().
            get("/api/v1/exam/course/TI0002/including").
        then().
            statusCode(HttpStatus.SC_NOT_FOUND)
        // @formatter:on
    }

    @Test
    fun getExamById() {
        //@formatter:off
        given().
        `when`().
            get("/api/v1/exam/1").
        then().
            statusCode(HttpStatus.SC_OK).
            body("content.name", `is`("Tentamen"))
        // @formatter:on
    }

    @Test
    fun getExamByIdNotExists() {
        //@formatter:off
        given().
        `when`().
            get("/api/v1/exam/2804").
        then().
            statusCode(HttpStatus.SC_NOT_FOUND)
        // @formatter:on
    }
}
