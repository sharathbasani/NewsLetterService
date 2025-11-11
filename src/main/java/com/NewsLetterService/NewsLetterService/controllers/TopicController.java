package com.NewsLetterService.NewsLetterService.controllers;

import com.NewsLetterService.NewsLetterService.entities.Topic;
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

    @PostMapping("/createTopic")
    public ResponseEntity<Topic> createTopic(@NotNull Topic topic) {
        return ResponseEntity.status(HttpStatus.CREATED).body(topicService.createTopic(topic));
    }

    @GetMapping("/getAllTopics")
    public ResponseEntity<List<Topic>> getAllTopics() {
        return ResponseEntity.ok(topicService.getAllTopics());
    }

    @GetMapping("/getTopic/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.getTopicById(id));
    }

    @PatchMapping("/updateTopic/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @NotNull Topic topic) {
        return ResponseEntity.ok(topicService.updateTopic(id, topic));
    }

    @DeleteMapping("/deleteTopic/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}
