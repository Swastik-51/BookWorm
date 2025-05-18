package com.bookworm.controller;

import com.bookworm.model.ReadingList;
import com.bookworm.repository.ReadingListRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.bookworm.repository.UserRepository;
import com.bookworm.model.User;

import java.util.List;

@RestController
@RequestMapping("/api/reading-lists")
public class ReadingListController {
    private final ReadingListRepository readingListRepo;
    private final UserRepository userRepository;

    public ReadingListController(ReadingListRepository readingListRepo, UserRepository userRepository) {
        this.readingListRepo = readingListRepo;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<ReadingList> getAllReadingLists() {
        return readingListRepo.findAll();
    }

    @GetMapping("/my")
    public List<ReadingList> getMyReadingLists(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return readingListRepo.findAll().stream()
            .filter(list -> list.getUser().getUser_id().equals(user.getUser_id()))
            .toList();
    }

    @PostMapping
    public ReadingList createReadingList(@RequestBody ReadingList list, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        list.setUser(user);
        return readingListRepo.save(list);
    }

    @DeleteMapping("/{listId}")
    public void deleteReadingList(@PathVariable Long listId) {
        readingListRepo.deleteById(listId);
    }
}
