package ch.wisv.choice.integration

import ch.wisv.choice.ApplicationTest
import io.restassured.RestAssured
import io.restassured.config.RedirectConfig
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(ApplicationTest::class), webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
abstract class IntegrationTest {

    @Value("\${local.server.port}")
    var port: Int = 0

    @Before
    fun setXAuthIntegrationTest() {
        RestAssured.port = port
        RestAssured.config().redirect(RedirectConfig.redirectConfig().followRedirects(false))
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

}
