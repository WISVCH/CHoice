package ch.wisv.choice.document.service

import ch.wisv.choice.course.service.CourseService
import ch.wisv.choice.document.model.Document
import ch.wisv.choice.document.model.DocumentDTO
import ch.wisv.choice.exam.service.ExamService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class DocumentServiceImpl(val documentRepository: DocumentRepository,
                          val examService: ExamService,
                          val courseService: CourseService) : DocumentService {
    override fun storeDocument(file: MultipartFile, dto: DocumentDTO): Document {
        val document = Document(bytes = file.bytes, name = dto.name)

        return storeDocument(document)
    }

    override fun storeDocument(document: Document): Document {
        return documentRepository.saveAndFlush(document)
    }

    override fun getDocumentsMetadata(): Collection<Document> {
        val documents = documentRepository.findAll()
        documents.forEach { it.bytes = kotlin.ByteArray(0) }
        return documents
    }

//    override fun getDocumentsMetadataByExam(examId: Long): Collection<Document> {
////        val exam = examService.getExamById(examId)
////        val documents = documentRepository.findAllByExam(exam)
////        documents.forEach { it.bytes = kotlin.ByteArray(0) }
////        return documents
//    }
//
//    override fun getDocumentsMetadataByCourseCode(courseCode: String): Collection<Document> {
//        val course = courseService.getCourseByCourseCode(courseCode)
//        if (course != null) {
//            val documents = documentRepository.findAllByExam_Course(course)
//            documents.forEach { it.bytes = kotlin.ByteArray(0) }
//            return documents
//        }
//        return emptyList()
//    }

    override fun getDocumentBytesById(id: Long): ByteArray
            = documentRepository.findOne(id).bytes


//    override fun deleteDocument(documentId: Long)
//            = documentRepository.delete(documentId)
//
//    override fun deleteDocumentsByExam(examId: Long) {
//        val exam = examService.getExamById(examId)
//        documentRepository.deleteAllByExam(exam)
//    }
//
//    override fun deleteDocumentsByCourse(courseCode: String) {
//        val course = courseService.getCourseByCourseCode(courseCode)
//        course?.let { documentRepository.deleteAllByExam_Course(it) }
//    }
}
