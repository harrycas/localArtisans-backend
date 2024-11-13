package com.arquiproject.svc_artisans.repository;

import com.arquiproject.svc_artisans.model.Order;
import com.arquiproject.svc_artisans.model.User;
import com.arquiproject.svc_artisans.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

  // Search for the User Shopping Cart
  @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.isCart = :isCart")
  Optional<Order> findByUserIdAndIsCart(@Param("userId") Long userId, @Param("isCart") boolean isCart);

  int countByDeliveryPersonAndStatusNot(User deliveryPerson, OrderStatus status);

  @Query("SELECT o FROM Order o WHERE o.deliveryPerson.id = :deliveryPersonId")
  List<Order> findByDeliveryPersonId(@Param("deliveryPersonId") Long deliveryPersonId);

  @Query("SELECT o FROM Order o WHERE o.deliveryPerson.id = :deliveryPersonId AND o.id = :orderId")
  Order findByDeliveryPersonIdAndOrderId(@Param("deliveryPersonId") Long deliveryPersonId, @Param("orderId") Long orderId);

}
