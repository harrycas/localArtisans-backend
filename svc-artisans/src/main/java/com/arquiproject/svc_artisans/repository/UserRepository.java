package com.arquiproject.svc_artisans.repository;

import com.arquiproject.svc_artisans.model.Order;
import com.arquiproject.svc_artisans.model.Review;
import com.arquiproject.svc_artisans.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByEmailAndPassword(String email,String password);

    @Query(value = "select o from Order o where o.user.userId = :userId")
    List<Order> findAllUserOrders(int userId);

    @Query(value = "select r from Review r where r.user.userId = :userId")
    List<Review> findAllUserReviews(int userId);

}
