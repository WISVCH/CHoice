package ch.wisv.choice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.test.context.ActiveProfiles

@SpringBootApplication
@ActiveProfiles("test")
class ApplicationTest {

//    @Component
//    inner class TestRunner : CommandLineRunner {
//
//        override fun run(vararg evt: String) {
//
//        }
//
//    }

    fun main(args: Array<String>) {
        SpringApplication.run(ApplicationTest::class.java, *args)
    }
}