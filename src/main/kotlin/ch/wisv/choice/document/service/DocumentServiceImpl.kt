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

package ch.wisv.choice.document.service

import ch.wisv.choice.document.model.Document
import ch.wisv.choice.document.model.DocumentDTO
import ch.wisv.choice.document.model.File
import ch.wisv.choice.exam.service.ExamService
import ch.wisv.choice.util.CHoiceException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class DocumentServiceImpl(val documentRepository: DocumentRepository,
                          val examService: ExamService) : DocumentService {
    override fun storeDocument(file: MultipartFile, dto: DocumentDTO): Document {
        val document = Document(file = File(null, file.bytes), name = dto.name, exam = dto.exam)

        return storeDocument(document)
    }

    override fun storeDocument(document: Document): Document = documentRepository.saveAndFlush(document)

    override fun getDocumentsMetadata(): Collection<Document> {
        val documents = documentRepository.findAll()
        documents.forEach { it.file = File(null, ByteArray(0)) }

        return documents
    }

    override fun getDocumentBytesById(id: Long): ByteArray {
        val document = documentRepository.findOne(id) ?: throw CHoiceException("Document with id $id does not exists!")

        return document.file.bytes
    }

    override fun getDocumentBytesByExamId(id: Long): ByteArray {
        val exam = examService.getExamById(id)
        val document = documentRepository.findByExam(exam) ?: throw CHoiceException("Document with id $id does not exists!")

        return document.file.bytes
    }
}
