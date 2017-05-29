package ch.wisv.choice.exam.service

import ch.wisv.choice.exam.model.Exam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ExamServiceImpl

    @Autowired
    constructor(@Autowired val examRepository: ExamRepository) : ExamService {

    override fun createExam(exam: Exam): Exam
            = examRepository.saveAndFlush(exam)

    override fun getExams(): Collection<Exam>
            = examRepository.findAll()

    override fun getExamsByCourse(code: String): Collection<Exam>
            = examRepository.findAllByCourse_Code(code)

    override fun getExamById(id: Long): Exam
            = examRepository.findOne(id)

    override fun deleteExam(exam: Exam)
            = examRepository.delete(exam)

    override fun deleteExamsByCourse(code: String)
            = examRepository.deleteAllByCourse_Code(code)
}
