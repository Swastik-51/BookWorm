package com.bookworm.model;

import java.time.LocalDateTime;

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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long message_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    @JsonBackReference
    private BookClub bookClub;

    // Default constructor
    public Message() {
        this.message_id = 0L;
        this.sender = null;
        this.content = null;
        this.timestamp = null;
        this.bookClub = null;
        this.type = "MESSAGE";
    }

    // Parameterized constructor
    public Message(User sender, String content, LocalDateTime timestamp, BookClub bookClub, String type) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.bookClub = bookClub;
        this.type = type;
    }

	public Long getMessage_id() {
		return message_id;
	}

	public void setMessage_id(Long message_id) {
		this.message_id = message_id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BookClub getBookClub() {
		return bookClub;
	}

	public void setBookClub(BookClub bookClub) {
		this.bookClub = bookClub;
	}

	@Override
	public String toString() {
		return "Message [message_id=" + message_id + ", sender=" + sender + ", content=" + content + ", timestamp="
				+ timestamp + ", type=" + type + ", bookClub=" + bookClub + "]";
	}

	
	
}