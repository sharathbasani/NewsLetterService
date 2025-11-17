package com.NewsLetterService.NewsLetterService.config;

import com.NewsLetterService.NewsLetterService.mapper.ContentMapper;
import com.NewsLetterService.NewsLetterService.mapper.TopicMapper;
import com.NewsLetterService.NewsLetterService.mapper.SubscriptionMapper;
import com.NewsLetterService.NewsLetterService.mapper.SubscriberMapper;
import com.NewsLetterService.NewsLetterService.mapper.impl.ContentMapperImpl;
import com.NewsLetterService.NewsLetterService.mapper.impl.TopicMapperImpl;
import com.NewsLetterService.NewsLetterService.mapper.impl.SubscriptionMapperImpl;
import com.NewsLetterService.NewsLetterService.mapper.impl.SubscriberMapperImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }

    @Bean
    public ContentMapper contentMapper(ModelMapper modelMapper) {
        return new ContentMapperImpl(modelMapper);
    }

    @Bean
    public TopicMapper topicMapper(ModelMapper modelMapper) {
        return new TopicMapperImpl(modelMapper);
    }

    @Bean
    public SubscriptionMapper subscriptionMapper(ModelMapper modelMapper) {
        return new SubscriptionMapperImpl(modelMapper);
    }

    @Bean
    public SubscriberMapper subscriberMapper(ModelMapper modelMapper) {
        return new SubscriberMapperImpl(modelMapper);
    }
}
