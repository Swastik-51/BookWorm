package com.bookworm.services.impl;


import com.bookworm.model.BookClub;
import com.bookworm.repository.BookClubRepository;
import com.bookworm.services.BookClubServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookClubServiceImpl implements BookClubServices {

    @Autowired
    private BookClubRepository bookClubRepository;

    @Override
    public List<BookClub> getAllBookClubs() {
        return bookClubRepository.findAll();
    }

    @Override
    public Optional<BookClub> getBookClubById(Long id) {
        return bookClubRepository.findById(id);
    }

    @Override
    public BookClub saveBookClub(BookClub bookClub) {
        return bookClubRepository.save(bookClub);
    }

    @Override
    public void deleteBookClub(Long id) {
        bookClubRepository.deleteById(id);
    }
}
