package com.bookworm.services;

import com.bookworm.model.User;
import java.util.List;
import java.util.Optional;

public interface UserServices {
    User createUser(User user);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
