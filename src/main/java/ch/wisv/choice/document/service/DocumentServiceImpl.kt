package ch.wisv.choice.document.service

import ch.wisv.choice.course.service.CourseService
import ch.wisv.choice.document.model.Document
import ch.wisv.choice.document.model.DocumentDTO
import ch.wisv.choice.exam.service.ExamService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class DocumentServiceImpl : DocumentService {

    @Autowired
    lateinit var documentRepository: DocumentRepository
    @Autowired
    lateinit var examService: ExamService
    @Autowired
    lateinit var courseService: CourseService

    override fun storeDocument(file: MultipartFile, dto: DocumentDTO) {
        val doc = Document()
        doc.file = file
        doc.name = dto.name
        doc.exam = dto.exam
        documentRepository.saveAndFlush(doc)
    }

    override fun getDocumentsMetadata(): Collection<Document> {
        val documents = documentRepository.findAll()
        documents.forEach { it.file = null }
        return documents
    }

    override fun getDocumentsMetadataByExam(examId: Long): Collection<Document> {
        val exam = examService.getExamById(examId)
        val documents = documentRepository.findAllByExam(exam)
        documents.forEach { it.file = null }
        return documents
    }

    override fun getDocumentsMetadataByCourseCode(courseCode: String): Collection<Document> {
        val course = courseService.getCourseByCourseCode(courseCode)
        val documents = documentRepository.findAllByExam_Course(course)
        documents.forEach { it.file = null }
        return documents
    }

    override fun getDocumentById(id: Long): MultipartFile?
            = documentRepository.findOne(id).file


    override fun deleteDocument(documentId: Long)
            = documentRepository.delete(documentId)

    override fun deleteDocumentsByExam(examId: Long) {
        val exam = examService.getExamById(examId)
        documentRepository.deleteAllByExam(exam)
    }

    override fun deleteDocumentsByCourse(courseCode: String) {
        val course = courseService.getCourseByCourseCode(courseCode)
        documentRepository.deleteAllByExam_Course(course)
    }
}
