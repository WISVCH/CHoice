package ch.wisv.choice.course.controller;

import ch.wisv.choice.course.model.Course;
import ch.wisv.choice.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public Collection<Course> readAllCourses() {
        return courseService.readAllCourses();
    }

    @PostMapping
    public void createCourse(@RequestBody Course course) {
        courseService.createCourse(course);
    }

    @GetMapping("/{courseCode}")
    public Course readCourse(@PathVariable String courseCode) {
        return courseService.readCourse(courseCode);
    }

    @PutMapping
    public void updateCourse(@RequestBody Course course) {
        courseService.updateCourse(course);
    }

    @DeleteMapping("/{courseCode}")
    public void deleteCourse(@PathVariable String courseCode) {
        courseService.deleteCourse(courseCode);
    }
}
