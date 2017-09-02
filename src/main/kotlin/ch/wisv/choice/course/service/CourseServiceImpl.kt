package ch.wisv.choice.course.service

import ch.wisv.choice.course.model.Course
import ch.wisv.choice.util.CHoiceException
import org.springframework.stereotype.Service

@Service
class CourseServiceImpl(val courseRepository: CourseRepository) : CourseService {

    override fun readAllCourses(): Collection<Course>
            = courseRepository.findAll()

    override fun createCourse(course: Course): Course {
        assertIsValid(course)

        return courseRepository.saveAndFlush(course)
    }

    override fun getCourseByCourseCode(courseCode: String): Course?
            = courseRepository.findOne(courseCode)

    override fun getCoursePredecessorByCourseCode(courseCode: String): Course? {
        val (_, _, predecessor) = courseRepository.findOne(courseCode)
        return courseRepository.findOne(predecessor)
    }

    override fun deleteCourse(courseCode: String)
            = courseRepository.delete(courseCode)

    fun assertIsValid(course: Course) {
        val predecessor = course.predecessor

        if (predecessor != "") {
            if (predecessor != null && getCourseByCourseCode(predecessor) == null) {
                throw CHoiceException("Predecessor course doesn't exist!")
            }
        } else {
            course.predecessor = null
        }

        if (course.code == "") {
            throw CHoiceException("Course code cannot be empty!")
        }

        if (course.name == "") {
            throw CHoiceException("Name of the course cannot be empty!")
        }
    }
}
