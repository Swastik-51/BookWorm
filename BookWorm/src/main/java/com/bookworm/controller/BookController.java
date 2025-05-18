package com.bookworm.controller;

import com.bookworm.model.Book;
import com.bookworm.repository.BookRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
public class BookController {

    private final BookRepository repo;

    public BookController(BookRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return repo.findAll();
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return repo.save(book);
    }
    
    // Search books by title
    @GetMapping("/search/title")
    public List<Book> searchByTitle(@RequestParam String title) {
        return repo.findByTitleContainingIgnoreCase(title);
    }

    // Search books by author
    @GetMapping("/search/author")
    public List<Book> searchByAuthor(@RequestParam String author) {
        return repo.findByAuthorContainingIgnoreCase(author);
    }

    // Search books by genre
    @GetMapping("/search/genre")
    public List<Book> searchByGenre(@RequestParam String genre) {
        return repo.findByGenreContainingIgnoreCase(genre);
    }

    // General search endpoint: /api/books/search?query=...
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false) String query) {
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.ok(repo.findAll());
        }
        
        String searchTerm = query.trim().toLowerCase();
        List<Book> results = repo.findAll().stream()
            .filter(book -> 
                book.getTitle().toLowerCase().contains(searchTerm) ||
                book.getAuthor().toLowerCase().contains(searchTerm) ||
                book.getGenre().toLowerCase().contains(searchTerm)
            )
            .toList();
        
        return ResponseEntity.ok(results);
    }

    // Featured books endpoint: returns 5 random books
    @GetMapping("/featured")
    public ResponseEntity<List<Book>> getFeaturedBooks() {
        List<Book> all = repo.findAll();
        java.util.Collections.shuffle(all);
        return ResponseEntity.ok(all.stream().limit(5).toList());
    }

    // Recommendations endpoint: returns 5 random books
    @GetMapping("/recommendations")
    public ResponseEntity<List<Book>> getRecommendations() {
        List<Book> all = repo.findAll();
        java.util.Collections.shuffle(all);
        return ResponseEntity.ok(all.stream().limit(5).toList());
    }
}
