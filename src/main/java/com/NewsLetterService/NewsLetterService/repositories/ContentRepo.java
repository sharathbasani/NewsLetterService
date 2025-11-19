package com.NewsLetterService.NewsLetterService.repositories;

import com.NewsLetterService.NewsLetterService.entities.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ContentRepo extends JpaRepository<Content, Long> {
        boolean existsContentByTitle(String title);

        @Query(value = "SELECT * FROM content " +
                        "WHERE scheduled_at >= :startTime " +
                        "AND scheduled_at < :endTime", nativeQuery = true)
        List<Content> findScheduledInMinute(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
