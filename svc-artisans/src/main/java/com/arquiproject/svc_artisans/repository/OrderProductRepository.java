package com.arquiproject.svc_artisans.repository;

import com.arquiproject.svc_artisans.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    /*//Para mas vendidos con NATIVE QUERY
    @Query(value = "SELECT p.id, p.name, p.price, SUM(op.quantity) AS total_sold " +
            "FROM Products p " +
            "JOIN OrderProducts op ON p.id = op.product_id " +
            "JOIN Orders o ON op.order_id = o.id " +
            "GROUP BY p.id, p.name, p.price " +
            "ORDER BY total_sold DESC " +
            "LIMIT 10", nativeQuery = true)
    List<Object> findTopSellingProducts();*/
}
