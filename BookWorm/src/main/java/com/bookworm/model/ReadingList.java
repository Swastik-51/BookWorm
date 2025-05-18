package com.bookworm.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "reading_list")
public class ReadingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id")
    private Long list_id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "readingList", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<BookProgress> books;

   
    // Default constructor
    public ReadingList() {
        this.list_id = 0L;
        this.name = null;
        this.user = null;
        this.books = null;
    }

    // Parameterized constructor
    public ReadingList(String name, User user) {
        this.name = name;
        this.user = user;
    }

	public Long getList_id() {
		return list_id;
	}

	public void setList_id(Long list_id) {
		this.list_id = list_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<BookProgress> getBooks() {
		return books;
	}

	public void setBooks(List<BookProgress> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "ReadingList [list_id=" + list_id + ", name=" + name + ", user=" + user + ", books=" + books + "]";
	}

	
}

