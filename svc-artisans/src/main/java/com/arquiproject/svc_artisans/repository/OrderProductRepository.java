package com.arquiproject.svc_artisans.repository;

import com.arquiproject.svc_artisans.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {

  @Query(value = "select op from OrderProduct op where op.order.id=:orderId and op.productId=:productId")
  Optional<OrderProduct> findByOrderIdAndProductId(Long orderId,Long productId);

}
