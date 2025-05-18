package com.bookworm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "recommendation")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    private Long recommendation_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    @Column(name = "reason", length = 255)
    private String reason;

    // Default constructor
    public Recommendation() {
        this.recommendation_id = 0L;
        this.user = null;
        this.book = null;
        this.reason = null;
    }

    // Parameterized constructor
    public Recommendation(User user, Book book, String reason) {
        this.user = user;
        this.book = book;
        this.reason = reason;
    }

	

	public Long getRecommendation_id() {
		return recommendation_id;
	}

	public void setRecommendation_id(Long recommendation_id) {
		this.recommendation_id = recommendation_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "Recommendation [recommendation_id=" + recommendation_id + ", user=" + user + ", book=" + book
				+ ", reason=" + reason + "]";
	}

	
    
}