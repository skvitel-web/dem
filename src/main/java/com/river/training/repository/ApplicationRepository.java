package com.river.training.repository;

import com.river.training.entity.Application;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("SELECT a FROM Application a JOIN FETCH a.course WHERE a.user.id = :userId ORDER BY a.createdAt DESC")
    List<Application> findByUserIdWithCourse(@Param("userId") Long userId);

    @EntityGraph(attributePaths = {"course", "user"})
    Page<Application> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @EntityGraph(attributePaths = {"course", "user"})
    Page<Application> findByStatusOrderByCreatedAtDesc(String status, Pageable pageable);
}
