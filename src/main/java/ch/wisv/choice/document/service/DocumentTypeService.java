package ch.wisv.choice.document.service;

import ch.wisv.choice.document.model.DocumentType;

import java.util.Collection;

public interface DocumentTypeService {

    void createDocumentType(DocumentType documentType);

    Collection<DocumentType> readDocumentTypes();

    void updateDocumentType(DocumentType documentType);

    void deleteDocumentType(Long documentTypeId);
}
