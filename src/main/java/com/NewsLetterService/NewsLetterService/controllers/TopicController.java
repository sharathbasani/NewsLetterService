package com.NewsLetterService.NewsLetterService.controllers;

import com.NewsLetterService.NewsLetterService.dtos.request.TopicRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.TopicResponse;
import com.NewsLetterService.NewsLetterService.entities.Topic;
import com.NewsLetterService.NewsLetterService.mapper.TopicMapper;
import com.NewsLetterService.NewsLetterService.services.TopicService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicController{

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicMapper topicMapper;

    @PostMapping("/createTopic")
    public ResponseEntity<TopicResponse> createTopic(@RequestBody @NotNull TopicRequest topicRequest) {
        Topic topic = topicMapper.toEntity(topicRequest);
        Topic created = topicService.createTopic(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(topicMapper.toResponse(created));
    }

    @GetMapping("/getAllTopics")
    public ResponseEntity<List<TopicResponse>> getAllTopics() {
        return ResponseEntity.ok(topicMapper.toResponseList(topicService.getAllTopics()));
    }

    @GetMapping("/getTopic/{id}")
    public ResponseEntity<TopicResponse> getTopicById(@PathVariable Long id) {
        return ResponseEntity.ok(topicMapper.toResponse(topicService.getTopicById(id)));
    }

    @PatchMapping("/updateTopic/{id}")
    public ResponseEntity<TopicResponse> updateTopic(@PathVariable Long id, @RequestBody @NotNull TopicRequest topicRequest) {
        Topic topic = topicMapper.toEntity(topicRequest);
        Topic updated = topicService.updateTopic(id, topic);
        return ResponseEntity.ok(topicMapper.toResponse(updated));
    }

    @DeleteMapping("/deleteTopic/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}
