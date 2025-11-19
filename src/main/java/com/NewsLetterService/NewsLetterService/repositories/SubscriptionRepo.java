package com.NewsLetterService.NewsLetterService.repositories;

import com.NewsLetterService.NewsLetterService.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {
    List<Subscription> findBySubscriberId(Long id);

    boolean existsBySubscriberIdAndTopicId(Long subscriberId, Long topicId);

    @Query("SELECT s.subscriber.email FROM Subscription s WHERE s.topic.id = :topicId")
    List<String> findSubscriberEmailsByTopic(Long topicId);
}
