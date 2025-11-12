package com.NewsLetterService.NewsLetterService.services;

import com.NewsLetterService.NewsLetterService.customExceptions.contentExceptions.ContentNotFoundException;
import com.NewsLetterService.NewsLetterService.dtos.request.ContentCreateRequest;
import com.NewsLetterService.NewsLetterService.dtos.request.ContentUpdateRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.ContentResponse;
import com.NewsLetterService.NewsLetterService.entities.Content;
import com.NewsLetterService.NewsLetterService.entities.Topic;
import com.NewsLetterService.NewsLetterService.mapper.ContentMapper;
import com.NewsLetterService.NewsLetterService.repositories.ContentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {
    @Autowired
    private final ContentRepo contentRepo;
    @Autowired
    private final TopicService topicService;
    @Autowired
    private final ContentMapper contentMapper;

    private Content getContentEntityById(Long id) {
        return contentRepo.findById(id).orElseThrow(() -> new ContentNotFoundException(id));
    }

    public ContentResponse getContentById(Long id) {
        Content content = getContentEntityById(id);
        return contentMapper.toResponse(content);
    }

    public ContentResponse createContent(ContentCreateRequest contentCreateRequest) {
        final Long topicId = contentCreateRequest.getTopicId();
        Topic topic = topicService.getTopicById(topicId);

        Content content = contentMapper.toEntity(contentCreateRequest);
        content.setTopic(topic);

        Content savedContent = contentRepo.save(content);
        return contentMapper.toResponse(savedContent);
    }

    public List<ContentResponse> getAllContent() {
        List<Content> contents = contentRepo.findAll();
        return contentMapper.toResponseList(contents);
    }

    public void deleteContentById(Long id) {
        Content content = getContentEntityById(id);
        contentRepo.delete(content);
    }

    public ContentResponse updateContent(Long id, ContentUpdateRequest contentUpdateRequest) {
        Content content = getContentEntityById(id);
        contentMapper.updateEntityFromDto(contentUpdateRequest, content);

        if (contentUpdateRequest.getTopicId() != null) {
            Topic topic = topicService.getTopicById(contentUpdateRequest.getTopicId());
            content.setTopic(topic);
        }

        Content updatedContent = contentRepo.save(content);
        return contentMapper.toResponse(updatedContent);
    }

}

