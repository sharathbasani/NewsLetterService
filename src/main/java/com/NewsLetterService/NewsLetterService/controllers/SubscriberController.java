package com.NewsLetterService.NewsLetterService.controllers;
import com.NewsLetterService.NewsLetterService.entities.Subscriber;
import com.NewsLetterService.NewsLetterService.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriber")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @GetMapping("/getAllSubscribers")
    public ResponseEntity<List<Subscriber>> getAllSubscribers() {
        return subscriberService.getAllSubscribers();
    }

    @GetMapping("/getSubscriber/{id}")
    public ResponseEntity<Subscriber> getSubscriberById(@PathVariable Long id) {
        return subscriberService.getSubscriberById(id);
    }

    @PostMapping("/createSubscriber")
    public ResponseEntity<Subscriber> createSubscriber(@RequestBody Subscriber subscriber) {
        return subscriberService.createSubscriber(subscriber);
    }

    @PatchMapping("/updateSubscriber")
    public ResponseEntity<Subscriber> updateSubscriber(@RequestBody Subscriber subscriber) {
        return subscriberService.updateSubscriber(subscriber);
    }

    @DeleteMapping("/deleteSubscriber/{id}")
    public ResponseEntity<Subscriber> deleteSubscriber(@PathVariable Long id) {
        return subscriberService.deleteSubscriber(id);
    }

}
