package com.bookworm.services.impl;



import com.bookworm.model.BookProgress;
import com.bookworm.repository.BookProgressRepository;
import com.bookworm.services.BookProgressServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookProgressServiceImpl implements BookProgressServices {

    @Autowired
    private BookProgressRepository bookProgressRepository;

    @Override
    public List<BookProgress> getAllProgress() {
        return bookProgressRepository.findAll();
    }

    @Override
    public Optional<BookProgress> getProgressById(Long id) {
        return bookProgressRepository.findById(id);
    }

    @Override
    public BookProgress saveProgress(BookProgress bookProgress) {
        return bookProgressRepository.save(bookProgress);
    }

    @Override
    public void deleteProgress(Long id) {
        bookProgressRepository.deleteById(id);
    }
}
