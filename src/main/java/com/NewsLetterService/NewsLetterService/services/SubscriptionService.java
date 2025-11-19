package com.NewsLetterService.NewsLetterService.services;

import com.NewsLetterService.NewsLetterService.customExceptions.subscriptionExceptions.DuplicateSubscriptionException;
import com.NewsLetterService.NewsLetterService.customExceptions.subscriptionExceptions.SubscriptionNotFoundException;
import com.NewsLetterService.NewsLetterService.dtos.request.SubscriptionRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.SubscriptionResponse;
import com.NewsLetterService.NewsLetterService.entities.Subscriber;
import com.NewsLetterService.NewsLetterService.entities.Subscription;
import com.NewsLetterService.NewsLetterService.entities.Topic;
import com.NewsLetterService.NewsLetterService.mapper.SubscriptionMapper;
import com.NewsLetterService.NewsLetterService.repositories.SubscriptionRepo;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepo subscriptionRepo;
    @Autowired
    private TopicService topicService;
    @Autowired
    private SubscriberService subscriberService;
    @Autowired
    private SubscriptionMapper subscriptionMapper;

    public SubscriptionResponse createSubscription(@NotNull SubscriptionRequest request) {
        Long subscriberId = request.getSubscriberId();
        Long topicId = request.getTopicId();

        Subscriber subscriber = subscriberService.getSubscriberById(subscriberId);
        Topic topic = topicService.getTopicById(topicId);

        if (subscriptionRepo.existsBySubscriberIdAndTopicId(subscriberId, topicId)) {
            throw new DuplicateSubscriptionException(subscriberId, topicId);
        }

        Subscription subscription = Subscription.builder()
                .subscriber(subscriber)
                .topic(topic)
                .build();
        subscriptionRepo.save(subscription);

        return subscriptionMapper.toResponse(subscription);
    }

    public List<SubscriptionResponse> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepo.findAll();
        return subscriptionMapper.toResponseList(subscriptions);
    }

    public Subscription getSubscriptionById(Long id) {
        return subscriptionRepo.findById(id).orElseThrow(() -> new SubscriptionNotFoundException(id));
    }

    public SubscriptionResponse updateSubscription(Long id, Long subscriberId, Long topicId) {
        Subscription existingSubscription = getSubscriptionById(id);

        if (subscriberId != null && !subscriberId.equals(existingSubscription.getSubscriber().getId())) {
            Subscriber subscriber = subscriberService.getSubscriberById(subscriberId);
            existingSubscription.setSubscriber(subscriber);
        }

        if (topicId != null && !topicId.equals(existingSubscription.getTopic().getId())) {
            Topic topic = topicService.getTopicById(topicId);
            existingSubscription.setTopic(topic);
        }

        Subscription updatedSubscription = subscriptionRepo.save(existingSubscription);
        return subscriptionMapper.toResponse(updatedSubscription);
    }

    public void deleteSubscription(Long id) {
        Subscription existing = getSubscriptionById(id);
        subscriptionRepo.delete(existing);
    }

    public List<String> findSubscriberEmailsByTopicId(Long topicId) {
        return subscriptionRepo.findSubscriberEmailsByTopic(topicId);
    }
}
