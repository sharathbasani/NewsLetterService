package com.NewsLetterService.NewsLetterService.mapper.impl;

import com.NewsLetterService.NewsLetterService.dtos.request.TopicRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.TopicResponse;
import com.NewsLetterService.NewsLetterService.entities.Topic;
import com.NewsLetterService.NewsLetterService.mapper.TopicMapper;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class TopicMapperImpl implements TopicMapper {

    private final ModelMapper modelMapper;

    public TopicMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Topic toEntity(TopicRequest dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Topic.class);
    }

    @Override
    public void entityFromDTO(TopicRequest dto, Topic entity) {
        if (dto != null) {
            modelMapper.map(dto, entity);
        }
    }

    @Override
    public TopicResponse toResponse(Topic entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, TopicResponse.class);
    }

    @Override
    public List<TopicResponse> toResponseList(List<Topic> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
