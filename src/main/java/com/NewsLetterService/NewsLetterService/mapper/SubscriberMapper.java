package com.NewsLetterService.NewsLetterService.mapper;

import com.NewsLetterService.NewsLetterService.dtos.request.SubscriberRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.SubscriberResponse;
import com.NewsLetterService.NewsLetterService.entities.Subscriber;
import org.mapstruct.MappingTarget;

import java.util.List;

public interface SubscriberMapper {
    Subscriber toEntity(SubscriberRequest dto);

    void entityFromDTO(SubscriberRequest dto, @MappingTarget Subscriber entity);

    SubscriberResponse toResponse(Subscriber entity);

    List<SubscriberResponse> toResponseList(List<Subscriber> entities);
}
