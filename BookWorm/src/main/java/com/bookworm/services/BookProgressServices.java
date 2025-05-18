package com.bookworm.services;


import com.bookworm.model.BookProgress;

import java.util.List;
import java.util.Optional;

public interface BookProgressServices {
    List<BookProgress> getAllProgress();
    Optional<BookProgress> getProgressById(Long id);
    BookProgress saveProgress(BookProgress bookProgress);
    void deleteProgress(Long id);
}
