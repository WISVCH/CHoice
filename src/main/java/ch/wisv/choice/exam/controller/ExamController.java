package ch.wisv.choice.exam.controller;

import ch.wisv.choice.course.model.Course;
import ch.wisv.choice.exam.model.Exam;
import ch.wisv.choice.exam.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/exam")
public class ExamController {

    private ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public void createExam(@RequestBody Exam exam) {
        examService.createExam(exam);
    }

    @GetMapping
    public Collection<Exam> readExams() {
        return examService.readExams();
    }

    @GetMapping("/{course}")
    public Collection<Exam> readExamsByCourse(@PathVariable Course course) {
        return examService.readExamsByCourse(course);
    }

    @PutMapping
    public void updateExam(@RequestBody Exam exam) {
        examService.updateExam(exam);
    }

    @DeleteMapping
    public void deleteExam(@RequestBody Exam exam) {
        examService.deleteExam(exam);
    }

    @DeleteMapping("/{course}")
    public void deleteExamsByCourseCode(@PathVariable Course course) {
        examService.deleteExamsByCourse(course);
    }
}
