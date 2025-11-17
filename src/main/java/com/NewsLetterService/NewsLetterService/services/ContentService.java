package com.NewsLetterService.NewsLetterService.services;

import com.NewsLetterService.NewsLetterService.customExceptions.contentExceptions.ContentNotFoundException;
import com.NewsLetterService.NewsLetterService.customExceptions.contentExceptions.DuplicateContentException;
import com.NewsLetterService.NewsLetterService.dtos.request.ContentRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.ContentResponse;
import com.NewsLetterService.NewsLetterService.entities.Content;
import com.NewsLetterService.NewsLetterService.entities.Topic;
import com.NewsLetterService.NewsLetterService.mapper.ContentMapper;
import com.NewsLetterService.NewsLetterService.repositories.ContentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {
    @Autowired
    private ContentRepo contentRepo;
    @Autowired
    private TopicService topicService;
    @Autowired
    private ContentMapper contentMapper;

    private Content getContentEntityById(Long id) {
        return contentRepo.findById(id).orElseThrow(() -> new ContentNotFoundException(id));
    }

    public ContentResponse getContentById(Long id) {
        Content content = getContentEntityById(id);
        return contentMapper.toResponse(content);
    }

    public ContentResponse createContent(ContentRequest contentRequest) {
        if(contentRepo.existsContentByTitle(contentRequest.getTitle())) {
            throw new DuplicateContentException(contentRequest.getTitle());
        }

        final Long topicId = contentRequest.getTopicId();
        Topic topic = topicService.getTopicById(topicId);

        Content content = contentMapper.toEntity(contentRequest);
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

    public ContentResponse updateContent(Long id, ContentRequest contentRequest) {
        Content content = getContentEntityById(id);
        contentMapper.updateEntityFromDto(contentRequest, content);

        if (contentRequest.getTopicId() != null) {
            Topic topic = topicService.getTopicById(contentRequest.getTopicId());
            content.setTopic(topic);
        }

        Content updatedContent = contentRepo.save(content);
        return contentMapper.toResponse(updatedContent);
    }

}

