package com.arquiproject.msvc_catalog.controller;

import com.arquiproject.msvc_catalog.model.Category;
import com.arquiproject.msvc_catalog.model.Product;
import com.arquiproject.msvc_catalog.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categories = categoryService.findAll();
        System.out.println("Returning categories: " + categories); // Log for verification
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/createCategory")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        Category createdCategory = categoryService.createCategory(category);
        if(createdCategory != null){
            return new ResponseEntity<>(category, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/listProducts/{categoryId}")
    public ResponseEntity<List<Product>>findAllProductsCategory (@PathVariable Long categoryId){
        List<Product> products = categoryService.findAllProductsCategory(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
