package com.bookworm.repository;

import com.bookworm.model.BookClub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookClubRepository extends JpaRepository<BookClub, Long> {
}
