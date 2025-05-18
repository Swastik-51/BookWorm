package com.bookworm.repository;

import com.bookworm.model.ReadingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingListRepository extends JpaRepository<ReadingList, Long> {
}
