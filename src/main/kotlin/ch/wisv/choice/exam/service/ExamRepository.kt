package ch.wisv.choice.exam.service

import ch.wisv.choice.exam.model.Exam
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExamRepository : JpaRepository<Exam, Long> {

    fun findAllByCourse_Code(code: String): List<Exam>

    fun deleteAllByCourse_Code(code: String)
}
