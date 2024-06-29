package com.arquiproject.msvc_catalog.repository;

import com.arquiproject.msvc_catalog.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query(value = "select p from Product p where p.userId=:userId")
    List<Product> findProductsByUserId(int userId);

}



// Interface has always PUBLIC methods, therefore it is not necessary to specify.
// Although, it DOES require a return value, even void
// When deleting is better to use boolean to verify is done correctly in the Service