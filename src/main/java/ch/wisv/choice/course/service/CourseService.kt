package ch.wisv.choice.course.service

import ch.wisv.choice.course.model.Course

interface CourseService {

    fun readAllCourses(): Collection<Course>

    fun createCourse(course: Course): Course

    fun getCourseByCourseCode(courseCode: String): Course

    fun getCoursePredecessorByCourseCode(courseCode: String): Course

    fun deleteCourse(courseCode: String)

}
