package com.arquiproject.msvc_catalog.service;

import com.arquiproject.msvc_catalog.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Optional<Product> getProductById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    boolean deleteProduct(Long id);
    List<Product> findAllProduct();
    List<Product> findAllProductsByUserId(Long userId);
    void updateProductsToInactive(Long userId);

}
