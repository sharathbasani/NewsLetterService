package com.NewsLetterService.NewsLetterService.mapper;

import com.NewsLetterService.NewsLetterService.entities.Topic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicMapperHelper {

    default Topic fromId(Long id) {
        if (id == null) return null;
        Topic topic = new Topic();
        topic.setId(id);
        return topic;
    }
}

