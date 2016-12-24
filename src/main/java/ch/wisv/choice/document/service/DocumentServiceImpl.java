package ch.wisv.choice.document.service;

import ch.wisv.choice.document.model.Document;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DocumentServiceImpl implements DocumentService {

    private DocumentRepository documentRepository;

    @Override
    public void createDocument(Document document) {
        documentRepository.saveAndFlush(document);
    }

    @Override
    public Collection<Document> readDocuments() {
        return documentRepository.findAll();
    }

    @Override
    public void updateDocument(Document document) {
        documentRepository.saveAndFlush(document);
    }

    @Override
    public void deleteDocument(Long documentId) {
        documentRepository.delete(documentId);
    }
}
