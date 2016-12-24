package ch.wisv.choice.document.controller;

import ch.wisv.choice.document.model.Document;
import ch.wisv.choice.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public void createDocument(@RequestBody Document document) {
        documentService.createDocument(document);
    }

    @GetMapping
    public Collection<Document> readDocuments() {
        return documentService.readDocuments();
    }

    @PutMapping
    public void updateDocument(@RequestBody Document document) {
        documentService.updateDocument(document);
    }

    @DeleteMapping
    public void deleteDocument(@PathVariable Long documentId) {
        documentService.deleteDocument(documentId);
    }
}
