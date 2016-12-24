package ch.wisv.choice.course.service;

import ch.wisv.choice.course.model.Course;

import java.util.Collection;

public interface CourseService {

    Collection<Course> readAllCourses();

    void createCourse(Course course);

    Course readCourse(String courseCode);

    void updateCourse(Course course);

    void deleteCourse(String courseCode);

}
