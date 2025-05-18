package com.bookworm.services;

import com.bookworm.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookServices {
    Book addBook(Book book);
    Optional<Book> getBookById(Long id);
    List<Book> getAllBooks();
    Book updateBook(Long id, Book book);
    void deleteBook(Long id);
}