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
package ch.wisv.choice.security

import ch.wisv.choice.util.ResponseEntityBuilder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1")
class SecurityController {

    @GetMapping("/login")
    fun login(): ResponseEntity<*> {
        val headers = HttpHeaders()
        headers.location = URI.create("http://localhost:3000/choice/")

        return ResponseEntity<Any>(headers, HttpStatus.MOVED_PERMANENTLY)
    }

    @GetMapping("/authentication")
    fun check(): ResponseEntity<*> {
        return ResponseEntityBuilder.createResponseEntity(HttpStatus.ACCEPTED, "", null)
    }
}
