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

import io.restassured.RestAssured
import org.apache.http.HttpStatus
import org.hamcrest.Matchers.hasItems
import org.junit.Test

/**
 * Copyright (c) 2016  W.I.S.V. 'Christiaan Huygens'
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http:></http:>//www.gnu.org/licenses/>.
 */
class DocumentControllerTest : IntegrationTest() {

    private final val DOCUMENT_API_PATH = "/api/v1/document"

    @Test
    fun getDocumentsMetadata() {
        //@formatter:off
        RestAssured.given().
        `when`().
            get(DOCUMENT_API_PATH).
        then().
            statusCode(HttpStatus.SC_OK).
            body("content.name", hasItems("Tentamen", "Antwoorden"))
        // @formatter:on
    }

    @Test
    fun getDocumentByIdNotExists() {
        //@formatter:off
        RestAssured.given().
        `when`().
            get(DOCUMENT_API_PATH + "/2804").
        then().
            statusCode(HttpStatus.SC_NOT_FOUND)
        // @formatter:on
    }

    @Test
    fun getDocumentByExamIdExamNotExists() {
        //@formatter:off
        RestAssured.given().
        `when`().
            get(DOCUMENT_API_PATH + "/exam/2804").
        then().
            statusCode(HttpStatus.SC_NOT_FOUND)
        // @formatter:on
    }

    @Test
    fun getDocumentByExamIdDocumentNotExists() {
        //@formatter:off
        RestAssured.given().
        `when`().
            get(DOCUMENT_API_PATH + "/exam/2").
        then().
            statusCode(HttpStatus.SC_NOT_FOUND)
        // @formatter:on
    }

}