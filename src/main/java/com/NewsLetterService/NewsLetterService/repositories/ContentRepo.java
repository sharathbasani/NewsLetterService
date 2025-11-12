package com.NewsLetterService.NewsLetterService.repositories;

import com.NewsLetterService.NewsLetterService.entities.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepo extends JpaRepository<Content, Long> {
}
