package ch.wisv.choice.document.controller;

import ch.wisv.choice.document.model.DocumentType;
import ch.wisv.choice.document.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/document/type")
public class DocumentTypeController {

    private DocumentTypeService documentTypeService;

    @Autowired
    public DocumentTypeController(DocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    @PostMapping
    public void createDocumentType(@RequestBody DocumentType documentType) {
        documentTypeService.createDocumentType(documentType);
    }

    @GetMapping
    public Collection<DocumentType> readDocumentTypes() {
        return documentTypeService.readDocumentTypes();
    }

    @PutMapping
    public void updateDocumentType(@RequestBody DocumentType documentType) {
        documentTypeService.updateDocumentType(documentType);
    }

    @DeleteMapping
    public void deleteDocumentType(@PathVariable Long documentTypeId) {
        documentTypeService.deleteDocumentType(documentTypeId);
    }
}
