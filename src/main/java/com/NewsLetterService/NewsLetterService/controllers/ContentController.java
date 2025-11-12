package com.NewsLetterService.NewsLetterService.controllers;

import com.NewsLetterService.NewsLetterService.dtos.request.ContentUpdateRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.ContentResponse;
import com.NewsLetterService.NewsLetterService.dtos.request.ContentCreateRequest;
import com.NewsLetterService.NewsLetterService.services.ContentService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

    @PostMapping("/createContent")
    public ResponseEntity<ContentResponse> createContent(@RequestBody @NotNull ContentCreateRequest contentCreateRequest) {
        ContentResponse created = contentService.createContent(contentCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/getAllContent")
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

    @PutMapping("/updateContent/{id}")
    public ResponseEntity<ContentResponse> updateContent(@PathVariable Long id, @RequestBody @NotNull ContentUpdateRequest contentUpdateRequest) {
        ContentResponse updatedContent = contentService.updateContent(id, contentUpdateRequest);
        return ResponseEntity.ok(updatedContent);
    }
}
