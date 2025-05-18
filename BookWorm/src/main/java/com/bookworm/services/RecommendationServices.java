package com.bookworm.services;



import com.bookworm.model.Recommendation;

import java.util.List;
import java.util.Optional;

public interface RecommendationServices {
    List<Recommendation> getAllRecommendations();
    Optional<Recommendation> getRecommendationById(Long id);
    Recommendation saveRecommendation(Recommendation recommendation);
    void deleteRecommendation(Long id);
}
