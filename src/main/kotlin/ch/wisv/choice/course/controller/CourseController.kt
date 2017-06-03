package ch.wisv.choice.course.controller

import ch.wisv.choice.course.model.Course
import ch.wisv.choice.course.service.CourseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/course")
class CourseController

    @Autowired
    constructor(@Autowired val courseService: CourseService) {

    @PostMapping
    fun createCourse(@RequestBody course: Course)
            = courseService.createCourse(course)

    @GetMapping
    fun readAllCourses(): Collection<Course>
            = courseService.readAllCourses()

    @GetMapping("/{code}")
    fun getCourseByCode(@PathVariable code: String): Course
            = courseService.getCourseByCourseCode(code)

    @GetMapping("/{code}/predecessor")
    fun getCoursePredecessorByCode(@PathVariable code: String): Course
            = courseService.getCoursePredecessorByCourseCode(code)

    @DeleteMapping("/{code}")
    fun deleteCourse(@PathVariable code: String)
            = courseService.deleteCourse(code)
}
