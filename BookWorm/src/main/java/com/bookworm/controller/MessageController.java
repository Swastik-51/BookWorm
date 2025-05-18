package com.bookworm.controller;

import com.bookworm.model.Message;
import com.bookworm.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageRepository messageRepo;

    public MessageController(MessageRepository messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    @PostMapping
    public Message createMessage(@RequestBody Message message) {
        return messageRepo.save(message);
    }
}
