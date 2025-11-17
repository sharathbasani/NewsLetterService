package com.NewsLetterService.NewsLetterService.mapper;

import com.NewsLetterService.NewsLetterService.dtos.request.ContentRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.ContentResponse;
import com.NewsLetterService.NewsLetterService.entities.Content;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ContentMapper {

    Content toEntity(ContentRequest dto);

    void updateEntityFromDto(ContentRequest dto, @MappingTarget Content entity);

    @Mapping(target = "topicName", source = "topic.name")
    ContentResponse toResponse(Content entity);

    List<ContentResponse> toResponseList(List<Content> entities);
}
