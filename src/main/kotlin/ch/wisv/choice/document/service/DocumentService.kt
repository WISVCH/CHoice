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
import org.springframework.web.multipart.MultipartFile

interface DocumentService {

    fun storeDocument(file: MultipartFile, dto: DocumentDTO): Document

    fun storeDocument(document: Document): Document

    fun getDocumentsMetadata(): Collection<Document>

    fun getDocumentBytesById(id: Long): ByteArray
}
