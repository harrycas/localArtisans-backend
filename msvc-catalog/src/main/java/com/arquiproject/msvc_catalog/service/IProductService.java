package com.arquiproject.msvc_catalog.service;

import com.arquiproject.msvc_catalog.model.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(int id);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    boolean deleteProduct(int id);
    List<Product> findAllProduct();
    List<Product> findAllProductsByUserId(int userId);
    void updateProductsToInactive(List<Product> products);

}
