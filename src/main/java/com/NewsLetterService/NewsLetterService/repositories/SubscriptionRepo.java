package com.NewsLetterService.NewsLetterService.repositories;

import com.NewsLetterService.NewsLetterService.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {
}
