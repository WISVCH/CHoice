package ch.wisv.choice.exam.service;

import ch.wisv.choice.course.model.Course;
import ch.wisv.choice.exam.model.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ExamServiceImpl implements ExamService {

    private ExamRepository examRepository;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }


    @Override
    public void createExam(Exam exam) {
        examRepository.saveAndFlush(exam);
    }

    @Override
    public Collection<Exam> readExams() {
        return examRepository.findAll();
    }

    @Override
    public Collection<Exam> readExamsByCourse(Course course) {
        return examRepository.findByCourse(course);
    }

    @Override
    public void updateExam(Exam exam) {
        examRepository.saveAndFlush(exam);
    }

    @Override
    public void deleteExam(Exam exam) {
        examRepository.delete(exam);
    }

    @Override
    public void deleteExamsByCourse(Course course) {
        Collection<Exam> exams = examRepository.findByCourse(course);
        examRepository.delete(exams);
    }
}
