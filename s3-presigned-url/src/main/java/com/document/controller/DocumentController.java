package com.document.controller;

import com.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/presigned-url")
    public ResponseEntity<String> getPresignedUrl(
            @RequestParam String borrowerId,
            @RequestParam String loanId,
            @RequestParam String fileName) {
        try {
            String url = documentService.generatePresignedUrl(borrowerId, loanId, fileName);
            return ResponseEntity.ok(url);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}