package com.arquiproject.svc_artisans.repository;

import com.arquiproject.svc_artisans.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

  // Search for the User Shopping Cart
  @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.isCart = :isCart")
  Optional<Order> findByUserIdAndIsCart(@Param("userId") Long userId, @Param("isCart") boolean isCart);

  // Search for every sale
  List<Order> findByIsCartFalse();

}
