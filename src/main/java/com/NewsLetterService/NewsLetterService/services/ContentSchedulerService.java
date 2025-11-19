package com.NewsLetterService.NewsLetterService.services;

import com.NewsLetterService.NewsLetterService.entities.Content;
import com.NewsLetterService.NewsLetterService.repositories.ContentRepo;
import com.NewsLetterService.NewsLetterService.repositories.SubscriptionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentSchedulerService {

    private final ContentRepo contentRepository;
    private final SubscriptionRepo subscriptionRepository;
    private final EmailService emailService;

    // Runs every minute
    @Scheduled(cron = "0 * * * * *")
    public void sendScheduledContent() {
        LocalTime now = LocalTime.now().withSecond(0).withNano(0);

        // 1. Find content whose scheduled time = now
        List<Content> contents = contentRepository.findByScheduledAt(now);
        if (contents.isEmpty()) {
            return;
        }

        for (Content content : contents) {
            // 2. Get all subscriber emails for this topic
            List<String> subscriberEmails = subscriptionRepository
                    .findSubscriberEmailsByTopic(content.getTopic().getId());

            // 3. Send content email to all subscribers
            for (String email : subscriberEmails) {
                emailService.sendEmail(
                        email,
                        content.getTitle(),
                        content.getBody()
                );
            }
        }
    }
}
