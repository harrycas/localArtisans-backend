package com.arquiproject.msvc_catalog.repository;

import com.arquiproject.msvc_catalog.model.Category;
import com.arquiproject.msvc_catalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "select p from Product p where p.category.categoryId = :categoryId")
    List<Product> findAllProductCategory(int categoryId);

}