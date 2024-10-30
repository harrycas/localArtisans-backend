package com.arquiproject.msvc_catalog.service;

import com.arquiproject.msvc_catalog.model.Product;
import com.arquiproject.msvc_catalog.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{

    final private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> getProductById(Long id) {return productRepository.findById(id);}

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productFound = productRepository.findById(product.getId()).orElse(null);
        if (productFound != null) return productRepository.save(product);
        else return null;
    }

    @Override
    public boolean deleteProduct(Long id) {
        boolean deleted = false;
        try {
            productRepository.deleteById(id);
            deleted = true;
        } catch (Exception e) {
            System.out.println("Error when deleting product: " + id + ": " + e.getMessage());
        }
        return deleted;
    }

    @Override
    public List<Product> findAllProduct() {return productRepository.findAll();}

    @Override
    public List<Product> findAllProductsByUserId(Long userId){
      return productRepository.findByUserId(userId);
    }

    @Transactional
    @Override
    public void updateProductsToInactive(Long userId) {
        try {
            int updatedCount = productRepository.markProductsAsInactiveByUserId(userId);
            if (updatedCount > 0) {
                System.out.println(updatedCount + " products set inactive for user " + userId);
            } else {
                System.out.println("No products to change state for user " + userId);
            }
        } catch (Exception e) {
            System.err.println("Error when updating products state: " + e.getMessage());
            throw new RuntimeException("Can not update products state", e);
        }
    }

}
