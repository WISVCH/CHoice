package ch.wisv.choice.exam.controller

import ch.wisv.choice.exam.model.Exam
import ch.wisv.choice.exam.service.ExamService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/exam")
class ExamController

    @Autowired
    constructor(@Autowired val examService: ExamService) {

    @PostMapping
    fun createExam(@RequestBody exam: Exam)
            = examService.createExam(exam)

    @GetMapping
    fun getExams(): Collection<Exam>
            = examService.getExams()

    @GetMapping("/course/{code}")
    fun getExamsByCourse(@PathVariable code: String): Collection<Exam>
            = examService.getExamsByCourse(code)

    @GetMapping("/{examId}")
    fun getExamById(@PathVariable examId: Long): Exam
            = examService.getExamById(examId)

    @DeleteMapping
    fun deleteExam(@RequestBody exam: Exam)
            = examService.deleteExam(exam)

    @DeleteMapping("/{code}")
    fun deleteExamsByCourseCode(@PathVariable code: String)
            = examService.deleteExamsByCourse(code)
}
