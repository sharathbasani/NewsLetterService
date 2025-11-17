package com.NewsLetterService.NewsLetterService.repositories;

import com.NewsLetterService.NewsLetterService.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {
    boolean existsTopicByName(String name);
}
