package ch.wisv.choice.document.service

import ch.wisv.choice.course.model.Course
import ch.wisv.choice.document.model.Document
import ch.wisv.choice.exam.model.Exam
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DocumentRepository : JpaRepository<Document, Long> {

    fun findAllByExam(exam: Exam): Collection<Document>

    fun findAllByExam_Course(course: Course): Collection<Document>

    fun deleteAllByExam(exam: Exam): Nothing

    fun deleteAllByExam_Course(course: Course): Nothing
}
