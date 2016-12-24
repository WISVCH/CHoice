package ch.wisv.choice.document.service;


import ch.wisv.choice.document.model.Document;

import java.util.Collection;

public interface DocumentService {

    void createDocument(Document document);

    Collection<Document> readDocuments();

    void updateDocument(Document document);

    void deleteDocument(Long documentId);
}
