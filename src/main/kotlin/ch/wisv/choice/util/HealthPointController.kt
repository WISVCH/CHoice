package ch.wisv.choice.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/status")
class HealthPointController() {

    /**
     * Returns an Ok http status as long as the server is running
     *
     * @return ResponseEntity<*>
     */
    @GetMapping
    fun getStatus(): ResponseEntity<*> =
            ResponseEntityBuilder.createResponseEntity(HttpStatus.OK, "")
}