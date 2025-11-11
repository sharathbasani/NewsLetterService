package com.NewsLetterService.NewsLetterService.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NewsLetterService.NewsLetterService.entities.Subscriber;
import com.NewsLetterService.NewsLetterService.services.SubscriberService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/subscriber")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @PostMapping("/createSubscriber")
    public ResponseEntity<Subscriber> createSubscriber(@RequestBody @NotNull Subscriber subscriber) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriberService.createSubscriber(subscriber));
    }

    @GetMapping("/getAllSubscribers")
    public ResponseEntity<List<Subscriber>> getAllSubscribers() {
        return ResponseEntity.ok(subscriberService.getAllSubscribers());
    }

    @GetMapping("/getSubscriber/{id}")
    public ResponseEntity<Subscriber> getSubscriberById(@PathVariable Long id) {
        return ResponseEntity.ok(subscriberService.getSubscriberById(id));
    }

    @PatchMapping("/updateSubscriber/{id}")
    public ResponseEntity<Subscriber> updateSubscriber(@PathVariable Long id, @RequestBody @NotNull Subscriber subscriber) {
        return ResponseEntity.ok(subscriberService.updateSubscriber(id, subscriber));
    }

    @DeleteMapping("/deleteSubscriber/{id}")
    public ResponseEntity<Void> deleteSubscriber(@PathVariable Long id) {
        subscriberService.deleteSubscriber(id);
        return ResponseEntity.noContent().build();
    }

}
