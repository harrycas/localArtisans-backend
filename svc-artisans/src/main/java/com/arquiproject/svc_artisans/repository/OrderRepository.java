package com.arquiproject.svc_artisans.repository;

import com.arquiproject.svc_artisans.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
