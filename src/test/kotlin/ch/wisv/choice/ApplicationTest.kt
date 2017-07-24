package ch.wisv.choice

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.test.context.ActiveProfiles

@Configuration
@ComponentScan
@EnableAutoConfiguration
@ActiveProfiles("test")
class ApplicationTest {

    @Component
    inner class TestRunner : CommandLineRunner {

        override fun run(vararg evt: String) {

        }

    }

    fun main(args: Array<String>) {
        SpringApplication.run(ApplicationTest::class.java, *args)
    }
}