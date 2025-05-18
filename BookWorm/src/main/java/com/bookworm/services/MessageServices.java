package com.bookworm.services;
import com.bookworm.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageServices {
    List<Message> getAllMessages();
    Optional<Message> getMessageById(Long id);
    Message saveMessage(Message message);
    void deleteMessage(Long id);
}