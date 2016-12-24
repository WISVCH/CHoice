package ch.wisv.choice.document.service;

import ch.wisv.choice.document.model.DocumentType;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private DocumentTypeRepository documentTypeRepository;

    @Override
    public void createDocumentType(DocumentType documentType) {
        documentTypeRepository.saveAndFlush(documentType);
    }

    @Override
    public Collection<DocumentType> readDocumentTypes() {
        return documentTypeRepository.findAll();
    }

    @Override
    public void updateDocumentType(DocumentType documentType) {
        documentTypeRepository.saveAndFlush(documentType);
    }

    @Override
    public void deleteDocumentType(Long documentTypeId) {
        documentTypeRepository.delete(documentTypeId);
    }
}
