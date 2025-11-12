package com.NewsLetterService.NewsLetterService.mapper;

import com.NewsLetterService.NewsLetterService.dtos.request.TopicRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.TopicResponse;
import com.NewsLetterService.NewsLetterService.entities.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ContentMapper.class})
public interface TopicMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    @Mapping(target = "contents", ignore = true)
    Topic toEntity(TopicRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    @Mapping(target = "contents", ignore = true)
    void entityFromDTO(TopicRequest dto, @MappingTarget Topic entity);

    @Mapping(target = "contents", source = "contents")
    TopicResponse toResponse(Topic entity);

    List<TopicResponse> toResponseList(List<Topic> entities);
}
