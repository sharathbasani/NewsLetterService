package com.NewsLetterService.NewsLetterService.mapper.impl;

import com.NewsLetterService.NewsLetterService.dtos.response.SubscriptionResponse;
import com.NewsLetterService.NewsLetterService.entities.Subscription;
import com.NewsLetterService.NewsLetterService.mapper.SubscriptionMapper;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class SubscriptionMapperImpl implements SubscriptionMapper {

    private final ModelMapper modelMapper;

    public SubscriptionMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Subscription toEntity(SubscriptionResponse dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Subscription.class);
    }

    @Override
    public SubscriptionResponse toResponse(Subscription subscription) {
        if (subscription == null) {
            return null;
        }
        SubscriptionResponse response = new SubscriptionResponse();
        if (subscription.getSubscriber() != null) {
            response.setSubscriberId(subscription.getSubscriber().getId());
            response.setSubscriberName(subscription.getSubscriber().getName());
        }
        if (subscription.getTopic() != null) {
            response.setTopicId(subscription.getTopic().getId());
            response.setTopicName(subscription.getTopic().getName());
        }
        response.setId(subscription.getId());
        response.setCreatedAt(subscription.getCreatedAt());
        response.setLastUpdatedAt(subscription.getLastUpdatedAt());
        return response;
    }

    @Override
    public List<SubscriptionResponse> toResponseList(List<Subscription> subscriptions) {
        if (subscriptions == null) {
            return null;
        }
        return subscriptions.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
