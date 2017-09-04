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

import ch.wisv.choice.document.model.Document
import ch.wisv.choice.document.model.DocumentDTO
import ch.wisv.choice.document.service.DocumentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/document")
@CrossOrigin
class DocumentController

    constructor(@Autowired val documentService: DocumentService) {

    /**
     * Create a new document from MultipartFile and DocumentDTO
     *
     * @param file: MultipartFile
     * @param dto: DocumentDTO
     *
     * return Document
     */
    @PostMapping
    fun createDocument(@RequestParam("file") file: MultipartFile, @RequestParam("dto") dto: DocumentDTO): Document
            = documentService.storeDocument(file, dto)

    /**
     * Get list of Document metadata
     *
     * @return Collection<Document>
     */
    @GetMapping
    fun getDocumentsMetadata(): Collection<Document>
            = documentService.getDocumentsMetadata()

    /**
     * Show document ByteArray as application/pdf
     *
     * @return ByteArray
     */
    @ResponseBody
    @GetMapping("/{documentId}", produces = arrayOf(MediaType.APPLICATION_PDF_VALUE))
    fun getDocumentById(@PathVariable documentId: Long): ByteArray
            = documentService.getDocumentBytesById(documentId)
}
