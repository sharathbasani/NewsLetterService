package com.NewsLetterService.NewsLetterService.mapper;

import com.NewsLetterService.NewsLetterService.dtos.request.TopicRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.TopicResponse;
import com.NewsLetterService.NewsLetterService.entities.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface TopicMapper {
    Topic toEntity(TopicRequest dto);

    void entityFromDTO(TopicRequest dto, @MappingTarget Topic entity);

    TopicResponse toResponse(Topic entity);

    List<TopicResponse> toResponseList(List<Topic> entities);
}
