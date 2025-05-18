package com.bookworm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collection;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    @Column(name = "username", nullable = false, unique = true, length = 30)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "bio", length = 500)
    private String bio;

    @ElementCollection
    @CollectionTable(name = "user_reading_preferences", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "preference")
    private List<String> readingPreferences;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ReadingList> readingLists;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_bookclub",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "bookclub_id"))
    @JsonIgnore
    private List<BookClub> bookClubs;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
        this.role = Role.USER;
    }

    public User(String username, String email, String password, String bio) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.role = Role.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getReadingPreferences() {
        return readingPreferences;
    }

    public void setReadingPreferences(List<String> readingPreferences) {
        this.readingPreferences = readingPreferences;
    }

    public List<ReadingList> getReadingLists() {
        return readingLists;
    }

    public void setReadingLists(List<ReadingList> readingLists) {
        this.readingLists = readingLists;
    }

    public List<BookClub> getBookClubs() {
        return bookClubs;
    }

    public void setBookClubs(List<BookClub> bookClubs) {
        this.bookClubs = bookClubs;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User [user_id=" + user_id + ", username=" + username + ", email=" + email + ", password=" + password
                + ", bio=" + bio + ", readingPreferences=" + readingPreferences + ", readingLists=" + readingLists
                + ", bookClubs=" + bookClubs + "]";
    }
}
