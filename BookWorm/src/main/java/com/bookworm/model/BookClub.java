package com.bookworm.model;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "book_club")
public class BookClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long club_id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @ManyToMany
    @JoinTable(
        name = "bookclub_user",
        joinColumns = @JoinColumn(name = "club_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private List<User> members;

    @ManyToMany
    @JoinTable(
        name = "bookclub_current_books",
        joinColumns = @JoinColumn(name = "club_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @JsonIgnore
    private List<Book> currentReads;

    @OneToMany(mappedBy = "bookClub", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Message> discussions;

    // Default constructor
    public BookClub() {
        this.club_id = 0L;
        this.name = null;
        this.description = null;
        this.imageUrl = null;
        this.members = null;
        this.currentReads = null;
        this.discussions = new ArrayList<>();
    }

    // Parameterized constructor
    public BookClub(String name, String description) {
        this.name = name;
        this.description = description;
    }

	public Long getId() {
		return club_id;
	}

	public void setId(Long id) {
		this.club_id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public List<Book> getCurrentReads() {
		return currentReads;
	}

	public void setCurrentReads(List<Book> currentReads) {
		this.currentReads = currentReads;
	}

	public List<Message> getDiscussions() {
		return discussions;
	}

	public void setDiscussions(List<Message> discussions) {
		this.discussions = discussions;
	}

	@Override
	public String toString() {
		return "BookClub [id=" + club_id + ", name=" + name + ", description=" + description + ", imageUrl=" + imageUrl + ", members=" + members
				+ ", currentReads=" + currentReads + ", discussions=" + discussions + "]";
	}
}
