package com.bookworm.services.impl;


import com.bookworm.model.Recommendation;
import com.bookworm.repository.RecommendationRepository;
import com.bookworm.services.RecommendationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendationServiceImpl implements RecommendationServices {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Override
    public List<Recommendation> getAllRecommendations() {
        return recommendationRepository.findAll();
    }

    @Override
    public Optional<Recommendation> getRecommendationById(Long id) {
        return recommendationRepository.findById(id);
    }

    @Override
    public Recommendation saveRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }

    @Override
    public void deleteRecommendation(Long id) {
        recommendationRepository.deleteById(id);
    }
}
