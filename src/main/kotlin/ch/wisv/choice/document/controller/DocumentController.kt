/*
 * Copyright (c) 2016  W.I.S.V. 'Christiaan Huygens'
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.wisv.choice.document.controller

import ch.wisv.choice.document.service.DocumentService
import ch.wisv.choice.exam.service.ExamService
import ch.wisv.choice.util.CHoiceException
import ch.wisv.choice.util.ResponseEntityBuilder.Companion.createResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/v1/document")
class DocumentController(val documentService: DocumentService, val examService: ExamService) {

    /**
     * Get list of Document metadata
     *
     * @return Collection<Document>
     */
    @GetMapping
    fun getDocumentsMetadata(): ResponseEntity<*>
            = createResponseEntity(HttpStatus.OK, "List of all the document metadata", documentService.getDocumentsMetadata())

    /**
     * Show document ByteArray as application/pdf
     *
     * @return ByteArray
     */
    @ResponseBody
    @GetMapping("/{documentId}", produces = arrayOf(MediaType.APPLICATION_PDF_VALUE))
    fun getDocumentById(@PathVariable documentId: Long, response: HttpServletResponse): ByteArray {
        return try {
            documentService.getDocumentBytesById(documentId)
        } catch (e: CHoiceException) {
            response.status = org.apache.http.HttpStatus.SC_NOT_FOUND
            ByteArray(0)
        }
    }

    /**
     * Show document ByteArray as application/pdf
     *
     * @return ByteArray
     */
    @ResponseBody
    @GetMapping("/exam/{examId}", produces = arrayOf(MediaType.APPLICATION_PDF_VALUE))
    fun getDocumentByExamId(@PathVariable examId: Long, response: HttpServletResponse): ByteArray {
        return try {
            val exam = examService.getExamById(examId)
            val examDate = exam.date.toString()
            val examName = exam.course.name.replace(' ', '_')
            val filename = "$examName[$examDate].pdf"
            response.contentType = "application/pdf"
            response.setHeader("Content-Disposition", "attachment; filename=\"$filename\""
            )
            documentService.getDocumentBytesByExamId(examId)
        } catch (e: CHoiceException) {
            response.status = org.apache.http.HttpStatus.SC_NOT_FOUND
            ByteArray(0)
        }
    }
}
