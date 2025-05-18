package com.bookworm.services;

import com.bookworm.model.BookClub;

import java.util.List;
import java.util.Optional;

public interface BookClubServices {
    List<BookClub> getAllBookClubs();
    Optional<BookClub> getBookClubById(Long id);
    BookClub saveBookClub(BookClub bookClub);
    void deleteBookClub(Long id);
}
