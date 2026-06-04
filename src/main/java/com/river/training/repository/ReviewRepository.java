package com.river.training.repository;

import com.river.training.entity.Review;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByApplicationId(Long applicationId);

    boolean existsByApplicationId(Long applicationId);
}
