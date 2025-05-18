package com.bookworm.repository;

import com.bookworm.model.BookProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookProgressRepository extends JpaRepository<BookProgress, Long> {
    @Query("SELECT bp FROM BookProgress bp WHERE bp.readingList.user.user_id = :userId AND bp.status = :status")
    List<BookProgress> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status);

    @Query("SELECT bp FROM BookProgress bp WHERE bp.readingList.user.user_id = :userId")
    List<BookProgress> findByUserId(@Param("userId") Long userId);
}
