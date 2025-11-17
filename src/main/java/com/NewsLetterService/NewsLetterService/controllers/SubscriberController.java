package com.NewsLetterService.NewsLetterService.controllers;

import java.util.List;

import com.NewsLetterService.NewsLetterService.dtos.request.SubscriberRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.SubscriberResponse;
import com.NewsLetterService.NewsLetterService.dtos.response.SubscriptionResponse;
import com.NewsLetterService.NewsLetterService.entities.Subscription;
import com.NewsLetterService.NewsLetterService.entities.Subscriber;
import com.NewsLetterService.NewsLetterService.mapper.SubscriberMapper;
import com.NewsLetterService.NewsLetterService.mapper.SubscriptionMapper;
import com.NewsLetterService.NewsLetterService.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/subscriber")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private SubscriberMapper subscriberMapper;

    @Autowired
    private SubscriptionMapper subscriptionMapper;

    @PostMapping("/createSubscriber")
    public ResponseEntity<SubscriberResponse> createSubscriber(@RequestBody @NotNull SubscriberRequest request) {
        Subscriber subscriber = subscriberMapper.toEntity(request);
        Subscriber created = subscriberService.createSubscriber(subscriber);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriberMapper.toResponse(created));
    }

    @GetMapping("/getAllSubscribers")
    public ResponseEntity<List<SubscriberResponse>> getAllSubscribers() {
        return ResponseEntity.ok(subscriberMapper.toResponseList(subscriberService.getAllSubscribers()));
    }

    @GetMapping("/getSubscriber/{id}")
    public ResponseEntity<SubscriberResponse> getSubscriberById(@PathVariable Long id) {
        return ResponseEntity.ok(subscriberMapper.toResponse(subscriberService.getSubscriberById(id)));
    }

    @PatchMapping("/updateSubscriber/{id}")
    public ResponseEntity<SubscriberResponse> updateSubscriber(@PathVariable Long id, @RequestBody @NotNull SubscriberRequest request) {
        Subscriber subscriber = subscriberMapper.toEntity(request);
        Subscriber updated = subscriberService.updateSubscriber(id, subscriber);
        return ResponseEntity.ok(subscriberMapper.toResponse(updated));
    }

    @DeleteMapping("/deleteSubscriber/{id}")
    public ResponseEntity<Void> deleteSubscriber(@PathVariable Long id) {
        subscriberService.deleteSubscriber(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllSubscriptions/{id}")
    public ResponseEntity<List<SubscriptionResponse>> getAllSubscriptions(@PathVariable Long id) {
        List<Subscription> subs = subscriberService.getAllSubscriptions(id);
        return ResponseEntity.ok(subscriptionMapper.toResponseList(subs));
    }

}
