package ch.wisv.choice.util

import ch.wisv.choice.course.model.Course
import ch.wisv.choice.course.service.CourseRepository
import ch.wisv.choice.course.service.CourseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("dev")
class TestDataRunner(@Autowired val courseRepository: CourseRepository) : CommandLineRunner {

    /**
     * Callback used to run the bean.
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    override fun run(vararg args: String?) {
        val predecessor = Course("TI1500", "Testing via error", null)
        courseRepository.saveAndFlush(predecessor)

        val course = Course("TI1505", "Testing via trail and error", predecessor.code)
        courseRepository.saveAndFlush(course)
    }
}