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

import ch.wisv.choice.course.model.Study
import ch.wisv.choice.course.model.StudyProgram
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.apache.http.HttpStatus
import org.hamcrest.Matchers.*
import org.junit.Test
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class CourseControllerTest : IntegrationTest() {

    @Test
    fun getAllCourses() {
        //@formatter:off
        RestAssured.given().
        `when`().
            get("/api/v1/course").
        then().
            statusCode(HttpStatus.SC_OK).
            body("content.name", hasItems("Test Course 1", "Test Course 1.2", "Test Course 2"))
        // @formatter:on
    }

    @Test
    fun getAllActiveCourse() {
        //@formatter:off
        RestAssured.given().
        `when`().
            get("/api/v1/course/active").
        then().
            statusCode(HttpStatus.SC_OK).
            body("content.name", hasItems("Test Course 1.2", "Test Course 2"))
                .and().
            body("content.name", not(hasItem("Test Course 1")))
        // @formatter:on
    }

    @Test
    fun searchActiveCourseWithStudy() {
        val options = HashMap<String, Any>()
        options.put("study", Study.AM.toString())

        //@formatter:off
        RestAssured.given().
        `when`().
            params(options).contentType(ContentType.JSON).
            get("/api/v1/course/active/search").
        then().
            statusCode(HttpStatus.SC_OK).
            body("content.name", hasItems("Test Course 1.2"))
                .and().
            body("content.name", not(hasItems("Test Course 2")))
        // @formatter:on
    }

    @Test
    fun searchActiveCourseWithStudyNotExists() {
        val options = HashMap<String, Any>()
        options.put("study", "TB")

        //@formatter:off
        RestAssured.given().
        `when`().
            params(options).contentType(ContentType.JSON).
            get("/api/v1/course/active/search").
        then().
            statusCode(HttpStatus.SC_BAD_REQUEST)
        // @formatter:on
    }

    @Test
    fun searchActiveCourseWithStudyProgram() {
        val options = HashMap<String, Any>()
        options.put("program", StudyProgram.FIRST_YEAR.toString())

        //@formatter:off
        RestAssured.given().
        `when`().
            params(options).contentType(ContentType.JSON).
            get("/api/v1/course/active/search").
        then().
            statusCode(HttpStatus.SC_OK).
            body("content.name", hasItems("Test Course 1.2"))
                .and().
            body("content.name", not(hasItems("Test Course 2")))
        // @formatter:on
    }

    @Test
    fun searchActiveCourseWithStudyProgramNotExists() {
        val options = HashMap<String, Any>()
        options.put("program", "EERSTE_JAAR")

        //@formatter:off
        RestAssured.given().
        `when`().
            params(options).contentType(ContentType.JSON).
            get("/api/v1/course/active/search").
        then().
            statusCode(HttpStatus.SC_BAD_REQUEST)
        // @formatter:on
    }

    @Test
    fun searchActiveCourseWithQuery() {
        val options = HashMap<String, Any>()
        options.put("query", "Test Course")

        //@formatter:off
        RestAssured.given().
        `when`().
            params(options).contentType(ContentType.JSON).
            get("/api/v1/course/active/search").
        then().
            statusCode(HttpStatus.SC_OK).
            body("content.name", hasItems("Test Course 1.2", "Test Course 2"))
                .and().
            body("content.name", not(hasItems("Test Course 1")))
        // @formatter:on
    }

    @Test
    fun searchActiveCourseWithQueryNotExists() {
        val options = HashMap<String, Any>()
        options.put("query", "EERSTE_JAAR")

        //@formatter:off
        RestAssured.given().
        `when`().
            params(options).contentType(ContentType.JSON).
            get("/api/v1/course/active/search").
        then().
            statusCode(HttpStatus.SC_OK).
            body("content.name", not(hasItems("Test Course 1", "Test Course 1.2", "Test Course 2")))
        // @formatter:on
    }

    @Test
    fun getCourseByCode() {
        //@formatter:off
        RestAssured.given().
        `when`().
            get("/api/v1/course/TI0012").
        then().
            statusCode(HttpStatus.SC_OK).
            body("content.name", `is`("Test Course 1.2"))
        // @formatter:on
    }

    @Test
    fun getCourseByCodeNotExists() {
        //@formatter:off
        RestAssured.given().
        `when`().
            get("/api/v1/course/TW0012").
        then().
            statusCode(HttpStatus.SC_BAD_REQUEST)
        // @formatter:on
    }

    @Test
    fun getCoursePredecessorByCode() {
        //@formatter:off
        RestAssured.given().
        `when`().
            get("/api/v1/course/TI0012/predecessor").
        then().
            statusCode(HttpStatus.SC_OK).
            body("content.name", `is`("Test Course 1"))
        // @formatter:on
    }

    @Test
    fun getCoursePredecessorByCodeNoPredecessor() {
        //@formatter:off
        RestAssured.given().
        `when`().
            get("/api/v1/course/TI0021/predecessor").
        then().
            statusCode(HttpStatus.SC_CONFLICT)
        // @formatter:on
    }

    @Test
    fun getCoursePredecessorByCodeNotExists() {
        //@formatter:off
        RestAssured.given().
        `when`().
            get("/api/v1/course/TW0021/predecessor").
        then().
            statusCode(HttpStatus.SC_BAD_REQUEST)
        // @formatter:on
    }
}