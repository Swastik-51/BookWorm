package com.bookworm.services.impl;


import com.bookworm.model.ReadingList;
import com.bookworm.repository.ReadingListRepository;
import com.bookworm.services.ReadingListServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReadingListServiceImpl implements ReadingListServices {

    @Autowired
    private ReadingListRepository readingListRepository;

    @Override
    public List<ReadingList> getAllReadingLists() {
        return readingListRepository.findAll();
    }

    @Override
    public Optional<ReadingList> getReadingListById(Long id) {
        return readingListRepository.findById(id);
    }

    @Override
    public ReadingList saveReadingList(ReadingList readingList) {
        return readingListRepository.save(readingList);
    }

    @Override
    public void deleteReadingList(Long id) {
        readingListRepository.deleteById(id);
    }
}
