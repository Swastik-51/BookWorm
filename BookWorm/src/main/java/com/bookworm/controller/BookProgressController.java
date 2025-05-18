package com.bookworm.controller;

import com.bookworm.model.BookProgress;
import com.bookworm.repository.BookProgressRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.bookworm.repository.UserRepository;
import com.bookworm.model.User;
import com.bookworm.repository.ReadingListRepository;
import com.bookworm.repository.BookRepository;
import com.bookworm.model.Book;
import com.bookworm.model.ReadingList;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/book-progress")
public class BookProgressController {
    private final BookProgressRepository progressRepo;
    private final UserRepository userRepository;
    private final ReadingListRepository readingListRepository;
    private final BookRepository bookRepository;

    public BookProgressController(BookProgressRepository progressRepo, UserRepository userRepository, ReadingListRepository readingListRepository, BookRepository bookRepository) {
        this.progressRepo = progressRepo;
        this.userRepository = userRepository;
        this.readingListRepository = readingListRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<BookProgress> getAllBookProgress() {
        return progressRepo.findAll();
    }

    @PostMapping
    public BookProgress createBookProgress(@RequestBody BookProgress progress) {
        Book book = bookRepository.findById(progress.getBook().getId())
            .orElseThrow(() -> new RuntimeException("Book not found"));
        ReadingList readingList = readingListRepository.findById(progress.getReadingList().getList_id())
            .orElseThrow(() -> new RuntimeException("Reading list not found"));
        progress.setBook(book);
        progress.setReadingList(readingList);
        return progressRepo.save(progress);
    }

    @GetMapping("/my")
    public List<BookProgress> getMyBookProgress(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return progressRepo.findAll().stream()
            .filter(progress -> progress.getReadingList().getUser().getUser_id().equals(user.getUser_id()))
            .toList();
    }

    @PutMapping("/{progressId}")
    public ResponseEntity<?> updateBookProgress(@PathVariable Long progressId, @RequestBody BookProgress updatedProgress, Authentication authentication) {
        try {
            BookProgress progress = progressRepo.findById(progressId)
                .orElseThrow(() -> new RuntimeException("Book progress not found"));

            // Verify the user owns this progress entry
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            if (!progress.getReadingList().getUser().getUser_id().equals(user.getUser_id())) {
                return ResponseEntity.status(403).body("Not authorized to update this progress");
            }

            // Update only the fields that are provided
            if (updatedProgress.getCurrentPage() != null) {
                progress.setCurrentPage(updatedProgress.getCurrentPage());
            }
            if (updatedProgress.getTotalPages() != null) {
                progress.setTotalPages(updatedProgress.getTotalPages());
            }
            if (updatedProgress.getStatus() != null) {
                progress.setStatus(updatedProgress.getStatus());
            }

            BookProgress savedProgress = progressRepo.save(progress);
            return ResponseEntity.ok(savedProgress);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while updating progress");
        }
    }
}
