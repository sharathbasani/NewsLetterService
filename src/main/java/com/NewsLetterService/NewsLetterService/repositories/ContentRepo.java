package com.NewsLetterService.NewsLetterService.repositories;

import com.NewsLetterService.NewsLetterService.entities.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ContentRepo extends JpaRepository<Content, Long> {
    boolean existsContentByTitle(String title);

    @Query("SELECT c FROM Content c WHERE c.scheduledAt = :time")
    List<Content> findByScheduledAt(LocalTime time);
}
