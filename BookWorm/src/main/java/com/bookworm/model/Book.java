package com.bookworm.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "genre")
    private String genre;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "cover_image", length = 500)
    private String coverImage;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BookProgress> progressEntries;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Recommendation> recommendations;

    @ManyToMany(mappedBy = "currentReads", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BookClub> clubsReadingThis;

    // Default constructor
    public Book() {}

    // Parameterized constructor
    public Book(String title, String author, String genre, String description, Double averageRating) {
		super();
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.description = description;
		this.averageRating = averageRating;
	}
    // Getters and setters

    public Long getId() {
        return id;
    }

	public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public List<BookProgress> getProgressEntries() {
        return progressEntries;
    }

    public void setProgressEntries(List<BookProgress> progressEntries) {
        this.progressEntries = progressEntries;
    }

    

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public List<BookClub> getClubsReadingThis() {
        return clubsReadingThis;
    }

    public void setClubsReadingThis(List<BookClub> clubsReadingThis) {
        this.clubsReadingThis = clubsReadingThis;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + ", genre=" + genre +
                ", description=" + description + ", averageRating=" + averageRating + ", coverImage=" + coverImage + "]";
    }
}
