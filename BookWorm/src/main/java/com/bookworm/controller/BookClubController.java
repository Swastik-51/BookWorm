package com.bookworm.controller;

import com.bookworm.model.BookClub;
import com.bookworm.model.User;
import com.bookworm.model.Message;
import com.bookworm.dto.MessageRequest;
import com.bookworm.repository.BookClubRepository;
import com.bookworm.repository.UserRepository;
import com.bookworm.repository.MessageRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

@RestController
@RequestMapping("/api/clubs")
@CrossOrigin
public class BookClubController {
    private final BookClubRepository bookClubRepo;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookClubController.class);

    public BookClubController(BookClubRepository bookClubRepo, UserRepository userRepository, MessageRepository messageRepository) {
        this.bookClubRepo = bookClubRepo;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public List<Map<String, Object>> getAllBookClubs() {
        List<BookClub> clubs = bookClubRepo.findAll();
        Random rand = new Random();
        return clubs.stream().map(club -> {
            Map<String, Object> clubMap = new HashMap<>();
            clubMap.put("id", club.getId());
            clubMap.put("name", club.getName());
            clubMap.put("description", club.getDescription());
            clubMap.put("imageUrl", club.getImageUrl());
            // Member count
            clubMap.put("memberCount", club.getMembers() != null ? club.getMembers().size() : 0);
            // Random current book
            if (club.getCurrentReads() != null && !club.getCurrentReads().isEmpty()) {
                var book = club.getCurrentReads().get(rand.nextInt(club.getCurrentReads().size()));
                Map<String, Object> bookInfo = new HashMap<>();
                bookInfo.put("id", book.getId());
                bookInfo.put("title", book.getTitle());
                bookInfo.put("author", book.getAuthor());
                bookInfo.put("coverImage", book.getCoverImage());
                clubMap.put("currentBook", bookInfo);
            } else {
                clubMap.put("currentBook", null);
            }
            return clubMap;
        }).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookClub> getClubDetails(@PathVariable Long id) {
        return bookClubRepo.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BookClub createBookClub(@RequestBody BookClub bookClub) {
        return bookClubRepo.save(bookClub);
    }

    @PostMapping("/{clubId}/join")
    public ResponseEntity<BookClub> joinClub(@PathVariable Long clubId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            BookClub club = bookClubRepo.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Book club not found"));
            
            if (!club.getMembers().contains(user)) {
                club.getMembers().add(user);
                // Ensure bidirectional relationship
                if (user.getBookClubs() == null || !user.getBookClubs().contains(club)) {
                    user.getBookClubs().add(club);
                }
                userRepository.save(user);
                bookClubRepo.save(club);
            }
            return ResponseEntity.ok(club);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{clubId}/discussions")
    public ResponseEntity<List<Message>> getClubDiscussions(@PathVariable Long clubId) {
        try {
            BookClub club = bookClubRepo.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Book club not found"));
            return ResponseEntity.ok(club.getDiscussions());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{clubId}/discussions")
    public ResponseEntity<List<Message>> createDiscussion(@PathVariable Long clubId, @RequestBody MessageRequest request) {
        try {
            if (request.getContent() == null || request.getContent().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            BookClub club = bookClubRepo.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Book club not found"));
            
            Message message = new Message();
            message.setSender(user);
            message.setContent(request.getContent());
            message.setType(request.getType() != null ? request.getType() : "MESSAGE");
            message.setTimestamp(LocalDateTime.now());
            message.setBookClub(club);
            
            messageRepository.save(message);
            // Fetch updated discussions
            BookClub updatedClub = bookClubRepo.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Book club not found after saving message"));
            return ResponseEntity.ok(updatedClub.getDiscussions());
        } catch (Exception e) {
            logger.error("Error creating discussion for club {}: {}", clubId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
