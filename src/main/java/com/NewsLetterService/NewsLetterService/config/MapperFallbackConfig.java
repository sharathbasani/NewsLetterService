package com.NewsLetterService.NewsLetterService.config;

import com.NewsLetterService.NewsLetterService.dtos.request.ContentRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.ContentResponse;
import com.NewsLetterService.NewsLetterService.dtos.response.SubscriptionResponse;
import com.NewsLetterService.NewsLetterService.dtos.response.TopicResponse;
import com.NewsLetterService.NewsLetterService.entities.Content;
import com.NewsLetterService.NewsLetterService.entities.Subscription;
import com.NewsLetterService.NewsLetterService.entities.Topic;
import com.NewsLetterService.NewsLetterService.mapper.ContentMapper;
import com.NewsLetterService.NewsLetterService.mapper.SubscriptionMapper;
import com.NewsLetterService.NewsLetterService.mapper.TopicMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MapperFallbackConfig {

    @Bean
    @ConditionalOnMissingBean(ContentMapper.class)
    public ContentMapper contentMapperFallback() {
        return new ContentMapper() {
            @Override
            public Content toEntity(ContentRequest dto) {
                if (dto == null) return null;
                Content.ContentBuilder b = Content.builder();
                b.title(dto.getTitle()).body(dto.getBody()).scheduledAt(dto.getScheduledAt());
                return b.build();
            }

            @Override
            public void updateEntityFromDto(ContentRequest dto, Content entity) {
                if (dto == null || entity == null) return;
                entity.setTitle(dto.getTitle());
                entity.setBody(dto.getBody());
                entity.setScheduledAt(dto.getScheduledAt());
            }

            @Override
            public ContentResponse toResponse(Content entity) {
                if (entity == null) return null;
                ContentResponse.ContentResponseBuilder b = ContentResponse.builder();
                Topic t = entity.getTopic();
                b.topicName(t == null ? null : t.getName());
                b.id(entity.getId())
                        .title(entity.getTitle())
                        .body(entity.getBody())
                        .scheduledAt(entity.getScheduledAt())
                        .createdAt(entity.getCreatedAt())
                        .lastUpdatedAt(entity.getLastUpdatedAt());
                return b.build();
            }

            @Override
            public List<ContentResponse> toResponseList(List<Content> entities) {
                if (entities == null) return null;
                List<ContentResponse> out = new ArrayList<>();
                for (Content c : entities) out.add(toResponse(c));
                return out;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(TopicMapper.class)
    public TopicMapper topicMapperFallback() {
        return new TopicMapper() {
            @Override
            public Topic toEntity(com.NewsLetterService.NewsLetterService.dtos.request.TopicRequest dto) {
                if (dto == null) return null;
                Topic t = new Topic();
                t.setName(dto.getName());
                t.setDescription(dto.getDescription());
                return t;
            }

            @Override
            public void entityFromDTO(com.NewsLetterService.NewsLetterService.dtos.request.TopicRequest dto, Topic entity) {
                if (dto == null || entity == null) return;
                entity.setName(dto.getName());
                entity.setDescription(dto.getDescription());
            }

            @Override
            public TopicResponse toResponse(Topic entity) {
                if (entity == null) return null;
                return new TopicResponse(entity.getId(), entity.getName(), entity.getDescription(), entity.getCreatedAt(), entity.getLastUpdatedAt(), null);
            }

            @Override
            public List<TopicResponse> toResponseList(List<Topic> entities) {
                if (entities == null) return null;
                List<TopicResponse> out = new ArrayList<>();
                for (Topic t : entities) out.add(toResponse(t));
                return out;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(SubscriptionMapper.class)
    public SubscriptionMapper subscriptionMapperFallback() {
        return new SubscriptionMapper() {
            @Override
            public Subscription toEntity(SubscriptionResponse dto) {
                if (dto == null) return null;
                Subscription s = new Subscription();
                // only id mapping; associations set in service
                s.setId(dto.getId());
                return s;
            }

            @Override
            public SubscriptionResponse toResponse(Subscription entity) {
                if (entity == null) return null;
                SubscriptionResponse.SubscriptionResponseBuilder b = SubscriptionResponse.builder();
                b.id(entity.getId());
                if (entity.getSubscriber() != null) {
                    b.subscriberId(entity.getSubscriber().getId());
                    b.subscriberName(entity.getSubscriber().getName());
                }
                if (entity.getTopic() != null) {
                    b.topicId(entity.getTopic().getId());
                    b.topicName(entity.getTopic().getName());
                }
                b.createdAt(entity.getCreatedAt()).lastUpdatedAt(entity.getLastUpdatedAt());
                return b.build();
            }

            @Override
            public List<SubscriptionResponse> toResponseList(List<Subscription> entities) {
                if (entities == null) return null;
                List<SubscriptionResponse> out = new ArrayList<>();
                for (Subscription s : entities) out.add(toResponse(s));
                return out;
            }
        };
    }
}
