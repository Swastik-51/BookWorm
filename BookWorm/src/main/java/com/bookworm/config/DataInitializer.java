package com.bookworm.config;

import com.bookworm.model.Book;
import com.bookworm.model.BookClub;
import com.bookworm.model.User;
import com.bookworm.model.Role;
import com.bookworm.repository.BookRepository;
import com.bookworm.repository.BookClubRepository;
import com.bookworm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookClubRepository bookClubRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        // Create admin user if not exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@bookworm.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }

        // First, clear book clubs and their relationships
        List<BookClub> existingClubs = bookClubRepository.findAll();
        for (BookClub club : existingClubs) {
            club.setCurrentReads(null); // Clear the relationship
            bookClubRepository.save(club);
        }
        bookClubRepository.deleteAll();
        System.out.println("[DataInitializer] Book clubs cleared successfully");

        // Then clear books
        bookRepository.deleteAll();
        System.out.println("[DataInitializer] Books cleared successfully");

        // Add sample books
        List<Book> books = Arrays.asList(
            createBook("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 4.5,
                "A story of the fabulously wealthy Jay Gatsby and his love for the beautiful Daisy Buchanan.",
                "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=500"),
            createBook("To Kill a Mockingbird", "Harper Lee", "Fiction", 4.8,
                "The story of racial injustice and the loss of innocence in the American South.",
                "https://images.unsplash.com/photo-1463320898484-cdee8141c787?w=500"),
            createBook("1984", "George Orwell", "Science Fiction", 4.6,
                "A dystopian social science fiction novel and cautionary tale.",
                "https://images.unsplash.com/photo-1507842217343-583bb7270b66?w=500"),
            createBook("Pride and Prejudice", "Jane Austen", "Romance", 4.7,
                "The story follows Elizabeth Bennet as she deals with issues of manners, upbringing, morality, education, and marriage.",
                "https://images.unsplash.com/photo-1519681393784-d120267933ba?w=500"),
            createBook("The Hobbit", "J.R.R. Tolkien", "Fantasy", 4.7,
                "The story of Bilbo Baggins, a hobbit who embarks on an epic adventure.",
                "https://images.unsplash.com/photo-1465101046530-73398c7f28ca?w=500"),
            createBook("The Catcher in the Rye", "J.D. Salinger", "Fiction", 4.3,
                "The story of teenage alienation and loss of innocence in the post-war world.",
                "https://images.unsplash.com/photo-1455885664032-7cbbda5d1a5a?w=500"),
            createBook("The Alchemist", "Paulo Coelho", "Fiction", 4.5,
                "A philosophical novel about a young shepherd's journey to find his personal legend.",
                "https://images.unsplash.com/photo-1464983953574-0892a716854b?w=500"),
            createBook("The Little Prince", "Antoine de Saint-Exupéry", "Fiction", 4.8,
                "A poetic tale about a young prince who visits various planets in space.",
                "https://images.unsplash.com/photo-1460474647541-4edd0cd0c746?w=500"),
            createBook("The Da Vinci Code", "Dan Brown", "Mystery", 4.2,
                "A mystery thriller novel that follows symbologist Robert Langdon.",
                "https://images.unsplash.com/photo-1460779890686-230c3f73c6b9?w=500"),
            createBook("The Kite Runner", "Khaled Hosseini", "Fiction", 4.7,
                "The story of Amir, a young boy from Kabul, and his journey to redemption.",
                "https://images.unsplash.com/photo-1465101178521-c1a9136a3b99?w=500"),
            createBook("The Book Thief", "Markus Zusak", "Historical Fiction", 4.8,
                "The story of Liesel Meminger, a young girl living in Nazi Germany.",
                "https://images.unsplash.com/photo-1465101046530-73398c7f28ca?w=500"),
            createBook("The Fault in Our Stars", "John Green", "Young Adult", 4.5,
                "A story about two teenagers who meet at a cancer support group.",
                "https://images.unsplash.com/photo-1465101178521-c1a9136a3b99?w=500"),
            createBook("The Hunger Games", "Suzanne Collins", "Science Fiction", 4.6,
                "A dystopian novel set in a post-apocalyptic world.",
                "https://images.unsplash.com/photo-1465101046530-73398c7f28ca?w=500"),
            createBook("The Lord of the Rings", "J.R.R. Tolkien", "Fantasy", 4.9,
                "An epic high-fantasy novel about the quest to destroy the One Ring.",
                "https://images.unsplash.com/photo-1465101178521-c1a9136a3b99?w=500"),
            createBook("The Chronicles of Narnia", "C.S. Lewis", "Fantasy", 4.7,
                "A series of fantasy novels about the magical land of Narnia.",
                "https://images.unsplash.com/photo-1465101046530-73398c7f28ca?w=500"),
            createBook("The Giver", "Lois Lowry", "Science Fiction", 4.4,
                "A dystopian novel about a seemingly utopian society.",
                "https://images.unsplash.com/photo-1465101178521-c1a9136a3b99?w=500"),
            createBook("The Help", "Kathryn Stockett", "Historical Fiction", 4.7,
                "A story about African American maids working in white households in the 1960s.",
                "https://images.unsplash.com/photo-1465101046530-73398c7f28ca?w=500"),
            createBook("The Secret Life of Bees", "Sue Monk Kidd", "Fiction", 4.5,
                "A story about a young girl's journey of self-discovery.",
                "https://images.unsplash.com/photo-1465101178521-c1a9136a3b99?w=500"),
            createBook("The Time Traveler's Wife", "Audrey Niffenegger", "Science Fiction", 4.4,
                "A love story about a man with a genetic disorder that causes him to time travel.",
                "https://images.unsplash.com/photo-1465101046530-73398c7f28ca?w=500")
        );
        bookRepository.saveAll(books);
        System.out.println("[DataInitializer] Books updated successfully");

        // Add sample book clubs
        List<BookClub> clubs = Arrays.asList(
            createBookClub("Lit Lounge",
                "A cozy club for book lovers to share and discuss their favorite reads.",
                "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=500",
                null),
            createBookClub("Page Turners",
                "For those who can't put a good book down—join us for lively discussions!",
                "https://images.unsplash.com/photo-1465101046530-73398c7f28ca?w=500",
                null),
            createBookClub("Novel Navigators",
                "Explore new worlds and genres with fellow adventurers.",
                "https://images.unsplash.com/photo-1463320898484-cdee8141c787?w=500",
                null),
            createBookClub("Bookish Vibes",
                "A club for sharing recommendations, reviews, and bookish fun.",
                "https://images.unsplash.com/photo-1460474647541-4edd0cd0c746?w=500",
                null)
        );
        bookClubRepository.saveAll(clubs);
        System.out.println("[DataInitializer] Book clubs updated successfully");
    }

    private Book createBook(String title, String author, String genre, double rating, String description, String coverImage) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setAverageRating(rating);
        book.setDescription(description);
        book.setCoverImage(coverImage);
        return book;
    }

    private BookClub createBookClub(String name, String description, String imageUrl, List<Book> currentReads) {
        BookClub club = new BookClub();
        club.setName(name);
        club.setDescription(description);
        club.setImageUrl(imageUrl);
        club.setCurrentReads(currentReads);
        return club;
    }
} 