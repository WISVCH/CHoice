package ch.wisv.choice.exam.service

import ch.wisv.choice.exam.model.Exam
import ch.wisv.choice.util.CHoiceException
import org.springframework.stereotype.Service

@Service
class ExamServiceImpl(val examRepository: ExamRepository) : ExamService {

    override fun createExam(exam: Exam): Exam {
        val filtered = getExamsByCourse(exam.course.code).filter { item -> item.date.equals(exam.date) }

        if (filtered.isNotEmpty()) {
            throw CHoiceException("Already have an exam of this course on date " + exam.date)
        }

        return examRepository.saveAndFlush(exam)
    }

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
