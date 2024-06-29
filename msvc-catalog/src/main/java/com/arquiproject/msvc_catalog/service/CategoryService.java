package com.arquiproject.msvc_catalog.service;

import com.arquiproject.msvc_catalog.model.Category;
import com.arquiproject.msvc_catalog.model.Product;
import com.arquiproject.msvc_catalog.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;

    //Injection by Constructor
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() { return categoryRepository.findAll();}

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Product> findAllProductsCategory(int categoryId) {
        return categoryRepository.findAllProductCategory(categoryId);
    }

    @Override
    public Category findById(int categoryId){
        return categoryRepository.findById(categoryId).orElse(null);
    }
}
