package com.NewsLetterService.NewsLetterService.mapper;

import com.NewsLetterService.NewsLetterService.dtos.request.ContentCreateRequest;
import com.NewsLetterService.NewsLetterService.dtos.request.ContentUpdateRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.ContentResponse;
import com.NewsLetterService.NewsLetterService.entities.Content;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TopicMapperHelper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "topic", source = "topicId")
    Content toEntity(ContentCreateRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "topic", source = "topicId")
    void updateEntityFromDto(ContentUpdateRequest dto, @MappingTarget Content entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "body", source = "body")
    @Mapping(target = "scheduledAt", source = "scheduledAt")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "lastUpdatedAt", source = "lastUpdatedAt")
    ContentResponse toResponse(Content entity);

    List<ContentResponse> toResponseList(List<Content> entities);
}
