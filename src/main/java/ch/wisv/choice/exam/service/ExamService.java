package ch.wisv.choice.exam.service;


import ch.wisv.choice.course.model.Course;
import ch.wisv.choice.exam.model.Exam;

import java.util.Collection;

public interface ExamService {

    void createExam(Exam exam);

    Collection<Exam> readExams();

    Collection<Exam> readExamsByCourse(Course course);

    void updateExam(Exam exam);

    void deleteExam(Exam exam);

    void deleteExamsByCourse(Course course);
}
