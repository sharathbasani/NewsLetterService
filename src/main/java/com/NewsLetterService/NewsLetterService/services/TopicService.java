package com.NewsLetterService.NewsLetterService.services;

import com.NewsLetterService.NewsLetterService.customExceptions.topicExceptions.DuplicateTopicException;
import com.NewsLetterService.NewsLetterService.customExceptions.topicExceptions.TopicNotFoundException;
import com.NewsLetterService.NewsLetterService.entities.Topic;
import com.NewsLetterService.NewsLetterService.repositories.TopicRepo;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepo topicRepo;

    public Topic createTopic(@NotNull Topic topic) {
        if(topicRepo.existsTopicByName(topic.getName())) {
            throw new DuplicateTopicException(topic.getName());
        }
        return topicRepo.save(topic);
    }

    public List<Topic> getAllTopics() {
        return topicRepo.findAll();
    }

    public Topic getTopicById(Long id) {
        return topicRepo.findById(id).orElseThrow(() -> new TopicNotFoundException(id));
    }

    public Topic updateTopic(Long id, @NotNull Topic topic) {
        Topic existingTopic = getTopicById(id);
        existingTopic.setName(topic.getName());
        existingTopic.setDescription(topic.getDescription());
        return topicRepo.save(existingTopic);
    }

    public void deleteTopic(Long id) {
        Topic topic = getTopicById(id);
        topicRepo.delete(topic);
    }
}

