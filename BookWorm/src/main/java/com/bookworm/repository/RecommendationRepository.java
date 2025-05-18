package com.bookworm.repository;

import com.bookworm.model.Recommendation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> 
{
	@Query("SELECT r FROM Recommendation r WHERE r.book.genre = :genre")
	List<Recommendation> findByBookGenre(@Param("genre") String genre);
}
