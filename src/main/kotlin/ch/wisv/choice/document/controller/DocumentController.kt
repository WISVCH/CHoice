package ch.wisv.choice.document.controller

import ch.wisv.choice.document.model.Document
import ch.wisv.choice.document.model.DocumentDTO
import ch.wisv.choice.document.service.DocumentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/document")
class DocumentController

    @Autowired
    constructor(@Autowired val documentService: DocumentService) {

    @PostMapping
    fun createDocument(@RequestParam("file") file: MultipartFile, @RequestParam("dto") dto: DocumentDTO)
            = documentService.storeDocument(file, dto)

    @GetMapping
    fun getDocumentsMetadata(): Collection<Document>
            = documentService.getDocumentsMetadata()

//    @GetMapping("/exam/{examId}")
//    fun getDocumentsMetadataByExam(@PathVariable examId: Long): Collection<Document>
//            = documentService.getDocumentsMetadataByExam(examId)
//
//    @GetMapping("/course/{code}")
//    fun getDocumentsMetadataByCourseCode(@PathVariable code: String): Collection<Document>
//            = documentService.getDocumentsMetadataByCourseCode(code)

    @ResponseBody
    @GetMapping("/{documentId}", produces = arrayOf(MediaType.APPLICATION_PDF_VALUE))
    fun getDocumentById(@PathVariable documentId: Long): ByteArray
            = documentService.getDocumentBytesById(documentId)
}
