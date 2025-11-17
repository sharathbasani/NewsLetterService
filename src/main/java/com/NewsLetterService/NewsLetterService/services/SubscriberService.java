package com.NewsLetterService.NewsLetterService.services;

import java.util.List;

import com.NewsLetterService.NewsLetterService.entities.Subscription;
import com.NewsLetterService.NewsLetterService.repositories.SubscriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NewsLetterService.NewsLetterService.customExceptions.subscriberExceptions.SubscriberNotFoundException;
import com.NewsLetterService.NewsLetterService.entities.Subscriber;
import com.NewsLetterService.NewsLetterService.repositories.SubscriberRepo;

import jakarta.validation.constraints.NotNull;

@Service
public class SubscriberService {

    @Autowired
    private SubscriptionRepo subscriptionRepo;
    @Autowired
    private SubscriberRepo subscriberRepo;

    public Subscriber createSubscriber(@NotNull Subscriber subscriber) {
        return subscriberRepo.save(subscriber);
    }

    public List<Subscriber> getAllSubscribers() {
        return subscriberRepo.findAll();
    }

    public Subscriber getSubscriberById(@NotNull Long id) {
        return subscriberRepo.findById(id).orElseThrow(() -> new SubscriberNotFoundException(id));
    }

    public Subscriber updateSubscriber(@NotNull Long id, @NotNull Subscriber subscriber) {
        Subscriber existingSubscriber = getSubscriberById(id);
        existingSubscriber.setName(subscriber.getName());
        existingSubscriber.setEmail(subscriber.getEmail());
        return subscriberRepo.save(existingSubscriber);
    }

    public void deleteSubscriber(@NotNull Long id) {
        Subscriber subscriber = getSubscriberById(id);
        subscriberRepo.delete(subscriber);
    }

    public List<Subscription> getAllSubscriptions(Long id) {
        return subscriptionRepo.findBySubscriberId(id);
    }
}
