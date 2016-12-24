package ch.wisv.choice.exam.service;

import ch.wisv.choice.course.model.Course;
import ch.wisv.choice.exam.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long>{

    Collection<Exam> findByCourse(Course course);
}
