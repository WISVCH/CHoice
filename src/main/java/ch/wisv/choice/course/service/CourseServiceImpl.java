package ch.wisv.choice.course.service;

import ch.wisv.choice.course.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Collection<Course> readAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public void createCourse(Course course) {
        //save() in CrudRepository will call persist() if entity is new.
        courseRepository.saveAndFlush(course);
    }

    @Override
    public Course readCourse(String courseCode) {
        return courseRepository.findOne(courseCode);
    }

    @Override
    public void updateCourse(Course course) {
        //save() in CrudRepository will call merge() if entity exists.
        courseRepository.saveAndFlush(course);
    }

    @Override
    public void deleteCourse(String courseCode) {
        courseRepository.delete(courseCode);
    }
}
