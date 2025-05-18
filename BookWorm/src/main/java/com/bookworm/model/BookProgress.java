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
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "book_progress")
public class BookProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id")
    private Long progress_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "current_page")
    private Integer currentPage;

    @Column(name = "total_pages")
    private Integer totalPages;

    @Column(name = "status", length = 20)
    private String status; // e.g., "Reading", "Completed", "To Read"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reading_list_id", nullable = false)
    @JsonBackReference
    private ReadingList readingList;

    // Default constructor
    public BookProgress() {
        this.progress_id = 0L;
        this.book = null;
        this.currentPage = 0;
        this.totalPages = 0;
        this.status = null;
        this.readingList = null;
    }

    // Parameterized constructor
    public BookProgress(Book book, Integer currentPage, Integer totalPages, String status, ReadingList readingList) {
        this.book = book;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.status = status;
        this.readingList = readingList;
    }

	public Long getId() {
		return progress_id;
	}

	public void setId(Long id) {
		this.progress_id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ReadingList getReadingList() {
		return readingList;
	}

	public void setReadingList(ReadingList readingList) {
		this.readingList = readingList;
	}

	@Override
	public String toString() {
		return "BookProgress [id=" + progress_id + ", book=" + book + ", currentPage=" + currentPage + ", totalPages="
				+ totalPages + ", status=" + status + ", readingList=" + readingList + "]";
	}
}