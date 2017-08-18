package ch.wisv.choice.document.service


import ch.wisv.choice.document.model.Document
import ch.wisv.choice.document.model.DocumentDTO
import org.springframework.web.multipart.MultipartFile

interface DocumentService {

    fun storeDocument(file: MultipartFile, dto: DocumentDTO)

    fun storeDocument(document: Document)

    fun getDocumentsMetadata(): Collection<Document>

    fun getDocumentsMetadataByExam(examId: Long): Collection<Document>

    fun getDocumentsMetadataByCourseCode(courseCode: String): Collection<Document>

    fun getDocumentBytesById(id: Long): ByteArray

    fun deleteDocument(documentId: Long)

    fun deleteDocumentsByExam(examId: Long)

    fun deleteDocumentsByCourse(courseCode: String)
}
