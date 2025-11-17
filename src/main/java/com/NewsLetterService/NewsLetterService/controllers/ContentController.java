package com.NewsLetterService.NewsLetterService.controllers;

import com.NewsLetterService.NewsLetterService.dtos.request.ContentRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.ContentResponse;
import com.NewsLetterService.NewsLetterService.services.ContentService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content")
@RequiredArgsConstructor
public class ContentController {
    @Autowired
    private final ContentService contentService;

    @PostMapping("/createContent")
    public ResponseEntity<ContentResponse> createContent(@RequestBody @NotNull ContentRequest contentRequest) {
        ContentResponse created = contentService.createContent(contentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/getAllContents")
    public ResponseEntity<List<ContentResponse>> getAllContent() {
        return ResponseEntity.ok(contentService.getAllContent());
    }

    @GetMapping("/getContent/{id}")
    public ResponseEntity<ContentResponse> getContentById(@PathVariable Long id) {
        return ResponseEntity.ok(contentService.getContentById(id));
    }

    @DeleteMapping("/deleteContent/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        contentService.deleteContentById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/updateContent/{id}")
    public ResponseEntity<ContentResponse> updateContent(@PathVariable Long id, @RequestBody @NotNull ContentRequest contentRequest) {
        ContentResponse updatedContent = contentService.updateContent(id, contentRequest);
        return ResponseEntity.ok(updatedContent);
    }
}
