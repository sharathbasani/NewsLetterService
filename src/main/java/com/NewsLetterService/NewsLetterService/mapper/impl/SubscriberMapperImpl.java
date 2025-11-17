package com.NewsLetterService.NewsLetterService.mapper.impl;

import com.NewsLetterService.NewsLetterService.dtos.request.SubscriberRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.SubscriberResponse;
import com.NewsLetterService.NewsLetterService.entities.Subscriber;
import com.NewsLetterService.NewsLetterService.mapper.SubscriberMapper;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class SubscriberMapperImpl implements SubscriberMapper {

    private final ModelMapper modelMapper;

    public SubscriberMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Subscriber toEntity(SubscriberRequest dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Subscriber.class);
    }

    @Override
    public void entityFromDTO(SubscriberRequest dto, Subscriber entity) {
        if (dto != null) {
            modelMapper.map(dto, entity);
        }
    }

    @Override
    public SubscriberResponse toResponse(Subscriber entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, SubscriberResponse.class);
    }

    @Override
    public List<SubscriberResponse> toResponseList(List<Subscriber> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
