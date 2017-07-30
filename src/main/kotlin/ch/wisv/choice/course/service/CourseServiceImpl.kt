package ch.wisv.choice.course.service

import ch.wisv.choice.course.model.Course
import org.springframework.stereotype.Service

@Service
class CourseServiceImpl(val courseRepository: CourseRepository) : CourseService {

    override fun readAllCourses(): Collection<Course>
            = courseRepository.findAll()

    override fun createCourse(course: Course): Course {
        val predecessor = course.predecessor
        if (predecessor != null && getCourseByCourseCode(predecessor) == null) {
            throw Exception("Predecessor Course doesn't exist!")
        }
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
}
