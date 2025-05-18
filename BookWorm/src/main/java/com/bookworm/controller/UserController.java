package com.bookworm.controller;

import com.bookworm.model.User;
import com.bookworm.repository.UserRepository;
import com.bookworm.repository.BookProgressRepository;
import com.bookworm.model.BookProgress;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserRepository repo;
    private final BookProgressRepository bookProgressRepo;

    public UserController(UserRepository repo, BookProgressRepository bookProgressRepo) {
        this.repo = repo;
        this.bookProgressRepo = bookProgressRepo;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return repo.save(user);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        String username = authentication.getName();
        User user = repo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = user.getUser_id();

        // Fetch BookProgress entries
        var currentlyReadingProgress = bookProgressRepo.findByUserIdAndStatus(userId, "Reading");
        var readBooksProgress = bookProgressRepo.findByUserIdAndStatus(userId, "Completed");

        // Map to Book objects (with progress info)
        var currentlyReading = currentlyReadingProgress.stream()
            .map(bp -> {
                var book = bp.getBook();
                Map<String, Object> bookWithProgress = new HashMap<>();
                bookWithProgress.put("id", book.getId());
                bookWithProgress.put("title", book.getTitle());
                bookWithProgress.put("author", book.getAuthor());
                bookWithProgress.put("coverImage", book.getCoverImage());
                bookWithProgress.put("progress", bp.getTotalPages() != null && bp.getTotalPages() > 0
                    ? (bp.getCurrentPage() * 100.0 / bp.getTotalPages())
                    : null);
                bookWithProgress.put("currentPage", bp.getCurrentPage());
                bookWithProgress.put("totalPages", bp.getTotalPages());
                bookWithProgress.put("status", bp.getStatus());
                return bookWithProgress;
            })
            .toList();

        var readBooks = readBooksProgress.stream()
            .map(bp -> {
                var book = bp.getBook();
                Map<String, Object> bookWithProgress = new HashMap<>();
                bookWithProgress.put("id", book.getId());
                bookWithProgress.put("title", book.getTitle());
                bookWithProgress.put("author", book.getAuthor());
                bookWithProgress.put("coverImage", book.getCoverImage());
                bookWithProgress.put("progress", 100); // Completed
                bookWithProgress.put("currentPage", bp.getCurrentPage());
                bookWithProgress.put("totalPages", bp.getTotalPages());
                bookWithProgress.put("status", bp.getStatus());
                return bookWithProgress;
            })
            .toList();

        var bookClubs = user.getBookClubs();
        Map<String, Object> profile = new HashMap<>();
        profile.put("user_id", user.getUser_id());
        profile.put("username", user.getUsername());
        profile.put("email", user.getEmail());
        profile.put("bio", user.getBio());
        profile.put("currentlyReading", currentlyReading);
        profile.put("readBooks", readBooks);
        profile.put("bookClubs", bookClubs);
        return ResponseEntity.ok(profile);
    }
}
