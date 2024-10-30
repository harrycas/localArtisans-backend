package com.arquiproject.msvc_catalog.service;

import com.arquiproject.msvc_catalog.model.Category;
import com.arquiproject.msvc_catalog.model.Product;

import java.util.List;

public interface ICategoryService {

    Category createCategory(Category category);

    List<Product> findAllProductsCategory(Long categoryId);

    Category findById(Long categoryId);

    List<Category> findAll();

}
