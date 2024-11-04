package com.arquiproject.msvc_catalog.controller;

import com.arquiproject.msvc_catalog.model.Category;
import com.arquiproject.msvc_catalog.model.ProductImg;
import com.arquiproject.msvc_catalog.model.DTOs.ProductInfo;
import com.arquiproject.msvc_catalog.model.Product;
import com.arquiproject.msvc_catalog.service.CategoryService;
import com.arquiproject.msvc_catalog.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long productId) {
        return productService.getProductById(productId)
            .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get DTO ProductInfo for svc-artisans
    @GetMapping("/info/{id}")
    public ProductInfo getProductInfoById(@PathVariable Long id) {
        Product product = productService.getProductById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
        return convertToProductInfo(product);
    }

    @PostMapping("/create/{categoryId}/{userId}")
    public ResponseEntity<Product> createProduct(@RequestBody Product product, @PathVariable Long categoryId, @PathVariable Long userId) {
        try {
            Category category = categoryService.findById(categoryId);
            if (category == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            product.setCategory(category);
            product.setUserId(userId);
            product.setActive(true);
            Product createdProduct = productService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product productDetails, @PathVariable Long categoryId) {
        Category category = categoryService.findById(categoryId);
        productDetails.setCategory(category);
        Product updatedProduct = productService.updateProduct(productDetails);
        if (updatedProduct != null) return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/findAllProducts")
    public ResponseEntity<List<Product>> findAllProducts() {
        List<Product> product = productService.findAllProduct();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/usersProduct/{userId}")
    public ResponseEntity<List<Product>> findAllProductsByUserId(@PathVariable Long userId) {
        List<Product> products = productService.findAllProductsByUserId(userId);
        if (products != null) return new ResponseEntity<>(products, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/userDelete/{userId}")
    public ResponseEntity<Void> updateProductsToInactive(@PathVariable Long userId) {
        try {
            productService.updateProductsToInactive(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mapper to convert Product a ProductInfo
    private ProductInfo convertToProductInfo(Product product) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(product.getId());
        productInfo.setName(product.getName());
        productInfo.setDescription(product.getDescription());
        productInfo.setPrice(product.getPrice());
        productInfo.setActive(product.isActive());
        productInfo.setUserId(product.getUserId());
        productInfo.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        // Getting the principal image of the product
        product.getImages().stream()
            .filter(ProductImg::isPrimary)
            .findFirst()
            .ifPresent(img -> {
                // Extract only the fileName from the url
                String fileName = Paths.get(img.getUrl()).getFileName().toString();
                productInfo.setPrimaryImageUrl(fileName);
            });

        return productInfo;
    }

}