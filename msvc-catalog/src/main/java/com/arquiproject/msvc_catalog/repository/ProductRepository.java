package com.arquiproject.msvc_catalog.repository;

import com.arquiproject.msvc_catalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query(value = "select p from Product p where p.userId=:userId")
    List<Product> findByUserId(Long userId);

    @Modifying
    @Query("UPDATE Product p SET p.active = false WHERE p.userId = :userId")
    int markProductsAsInactiveByUserId(Long userId);

}



// Interface has always PUBLIC methods, therefore it is not necessary to specify.
// Although, it DOES require a return value, even void
// When deleting is better to use boolean to verify is done correctly in the Service