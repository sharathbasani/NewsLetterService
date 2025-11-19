package com.NewsLetterService.NewsLetterService.mapper.impl;

import com.NewsLetterService.NewsLetterService.dtos.request.ContentRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.ContentResponse;
import com.NewsLetterService.NewsLetterService.entities.Content;
import com.NewsLetterService.NewsLetterService.mapper.ContentMapper;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ContentMapperImpl implements ContentMapper {

    private final ModelMapper modelMapper;

    public ContentMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Content toEntity(ContentRequest dto) {
        if (dto == null) {
            return null;
        }
        Content content = new Content();
        if(dto.getTitle() != null) content.setTitle(dto.getTitle());
        if(dto.getBody() != null) content.setBody(dto.getBody());
        if (dto.getScheduledAt() != null) content.setScheduledAt(dto.getScheduledAt());
        return content;
    }

    @Override
    public void updateEntityFromDto(ContentRequest dto, Content entity) {
        if (dto != null) {
            if (dto.getTitle() != null) entity.setTitle(dto.getTitle());
            if (dto.getBody() != null) entity.setBody(dto.getBody());
            if (dto.getScheduledAt() != null) entity.setScheduledAt(dto.getScheduledAt());
        }
    }

    @Override
    public ContentResponse toResponse(Content entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, ContentResponse.class);
    }

    @Override
    public List<ContentResponse> toResponseList(List<Content> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
