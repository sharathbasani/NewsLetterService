package com.NewsLetterService.NewsLetterService.services;

import com.NewsLetterService.NewsLetterService.entities.Content;
import com.NewsLetterService.NewsLetterService.repositories.ContentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentSchedulerService {

    private final ContentRepo contentRepository;
    private final SubscriptionService subscriptionService;
    private final EmailService emailService;

    // Runs every minute
    @Scheduled(cron = "0 * * * * *")
    public void sendScheduledContent() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime startTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        LocalTime endTime = startTime.plusMinutes(1);

        List<Content> contents = contentRepository.findScheduledInMinute(startTime.format(formatter), endTime.format(formatter));
        if (contents.isEmpty()) {
            return;
        }

        for (Content content : contents) {
            List<String> subscriberEmails = subscriptionService.findSubscriberEmailsByTopicId(content.getTopic().getId());

            for (String email : subscriberEmails) {
                emailService.sendEmailAsync(email, content.getTitle(), content.getBody());
            }
        }
    }
}
