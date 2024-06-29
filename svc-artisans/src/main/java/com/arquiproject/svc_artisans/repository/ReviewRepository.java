package com.arquiproject.svc_artisans.repository;

import com.arquiproject.svc_artisans.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
