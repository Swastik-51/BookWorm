package com.bookworm.services;

import com.bookworm.model.ReadingList;

import java.util.List;
import java.util.Optional;

public interface ReadingListServices {
    List<ReadingList> getAllReadingLists();
    Optional<ReadingList> getReadingListById(Long id);
    ReadingList saveReadingList(ReadingList readingList);
    void deleteReadingList(Long id);
}
