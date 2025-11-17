package com.NewsLetterService.NewsLetterService.controllers;

import com.NewsLetterService.NewsLetterService.dtos.request.SubscriptionRequest;
import com.NewsLetterService.NewsLetterService.dtos.response.SubscriptionResponse;
import com.NewsLetterService.NewsLetterService.mapper.SubscriptionMapper;
import com.NewsLetterService.NewsLetterService.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private SubscriptionMapper subscriptionMapper;

    @PostMapping("/createSubscription")
    public ResponseEntity<SubscriptionResponse> createSubscription(@RequestBody SubscriptionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionService.createSubscription(request));
    }

    @GetMapping("/getAllSubscriptions")
    public ResponseEntity<List<SubscriptionResponse>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }

    @GetMapping("/getSubscription/{id}")
    public ResponseEntity<SubscriptionResponse> getSubscriptionById(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionMapper.toResponse(subscriptionService.getSubscriptionById(id)));
    }

    @PatchMapping("/updateSubscription/{id}")
    public ResponseEntity<SubscriptionResponse> updateSubscription(@PathVariable Long id, @RequestBody SubscriptionRequest request) {
        return ResponseEntity.ok(subscriptionService.updateSubscription(id, request.getSubscriberId(), request.getTopicId()));
    }

    @DeleteMapping("/deleteSubscription/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }
}
