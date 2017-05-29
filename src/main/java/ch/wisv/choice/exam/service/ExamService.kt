package ch.wisv.choice.exam.service


import ch.wisv.choice.exam.model.Exam

interface ExamService {

    fun createExam(exam: Exam): Exam

    fun getExams(): Collection<Exam>

    fun getExamsByCourse(code: String): Collection<Exam>

    fun getExamById(id: Long): Exam

    fun deleteExam(exam: Exam)

    fun deleteExamsByCourse(code: String)
}
