package com.NewsLetterService.NewsLetterService.services;

import com.NewsLetterService.NewsLetterService.customExceptions.subscriberExceptions.SubscriberNotFoundException;
import com.NewsLetterService.NewsLetterService.entities.Subscriber;
import com.NewsLetterService.NewsLetterService.repositories.SubscriberRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriberService {

    @Autowired
    private SubscriberRepo subscriberRepo;

    public ResponseEntity<Subscriber> createSubscriber(Subscriber subscriber) {
        Subscriber createdSubscriber = subscriberRepo.save(subscriber);
        return new ResponseEntity<>(createdSubscriber, HttpStatus.CREATED);
    }

    public ResponseEntity<Subscriber> updateSubscriber(Subscriber subscriber) {
        subscriberRepo.findById(subscriber.getId()).orElseThrow(() -> new SubscriberNotFoundException(subscriber.getId()));
        Subscriber updatedSubscriber = subscriberRepo.save(subscriber);
        return new ResponseEntity<>(updatedSubscriber, HttpStatus.OK);
    }

    public ResponseEntity<Subscriber> deleteSubscriber(Long id) {
        Subscriber subscriber = subscriberRepo.findById(id).orElseThrow(() -> new SubscriberNotFoundException(id));
        if(subscriber != null) {
            subscriberRepo.delete(subscriber);
            return new ResponseEntity<>(subscriber, HttpStatus.OK);
        }
        throw new EntityNotFoundException("Subscriber with id " + id + " not found");
    }

    public ResponseEntity<List<Subscriber>> getAllSubscribers() {
        List<Subscriber> subscribers = subscriberRepo.findAll();
        return new ResponseEntity<>(subscribers, HttpStatus.OK);
    }

    public ResponseEntity<Subscriber> getSubscriberById(Long id) {
        Subscriber subscriber = subscriberRepo.findById(id).orElseThrow(() -> new SubscriberNotFoundException(id));
        return new ResponseEntity<>(subscriber, HttpStatus.OK);
    }
}
