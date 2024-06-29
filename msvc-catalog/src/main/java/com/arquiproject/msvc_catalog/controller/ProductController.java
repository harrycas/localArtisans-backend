package com.arquiproject.msvc_catalog.controller;

import com.arquiproject.msvc_catalog.model.Category;
import com.arquiproject.msvc_catalog.model.Product;
import com.arquiproject.msvc_catalog.service.CategoryService;
import com.arquiproject.msvc_catalog.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {

    // ATTRIBUTES
    private final ProductService productService;
    private final CategoryService categoryService;

    // CONSTRUCTOR
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    // METHODS
    @GetMapping("/one/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int productId) {  //Paso por URL(IDs o datos pequeños y NO sensibles)
        Product product = productService.getProductById(productId);
        if (product != null) return new ResponseEntity<>(product, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create/{categoryId}/{userId}")
    public ResponseEntity<Product> createProduct(@RequestBody Product product, @PathVariable int categoryId, @PathVariable int userId) {
        Category category = categoryService.findById(categoryId);
        product.setCategory(category);
        product.setUserId(userId);
        product.setActive(true);
        Product createdProduct = productService.createProduct(product);
        if(createdProduct != null)  return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product productDetails, @PathVariable int categoryId) {
        Category category = categoryService.findById(categoryId);
        productDetails.setCategory(category);
        Product updatedProduct = productService.updateProduct(productDetails);
        if (updatedProduct != null) return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/findAllProducts")
    public ResponseEntity<List<Product>> findAllProducts() {
        List<Product> product = productService.findAllProduct();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/usersProduct/{userId}")
    public ResponseEntity<List<Product>> findAllProductsByUserId(@PathVariable int userId) {
        List<Product> products = productService.findAllProductsByUserId(userId);
        if (products != null) return new ResponseEntity<>(products, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/userDelete/{userId}")
    public ResponseEntity<Void> updateProductsToInactive(@PathVariable int userId) {
        List<Product> products = productService.findAllProductsByUserId(userId);
        productService.updateProductsToInactive(products);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}