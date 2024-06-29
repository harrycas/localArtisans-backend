package com.arquiproject.msvc_catalog.service;

import com.arquiproject.msvc_catalog.model.Product;
import com.arquiproject.msvc_catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService implements IProductService{

    final private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productFound = productRepository.findById(product.getProductId()).orElse(null);
        if (productFound != null) return productRepository.save(product);
        else return null;
    }

    @Override
    public boolean deleteProduct(int id) {
        boolean deleted = false;
        try {
            productRepository.deleteById(id);
            deleted = true;
        } catch (Exception e) {
            System.out.println("Error al eliminar el producto: " + id + ": " + e.getMessage());
        }
        return deleted;
    }

    @Override
    public List<Product> findAllProduct() {return productRepository.findAll();}

    @Override
    public List<Product> findAllProductsByUserId(int userId){
        List<Product> products = productRepository.findProductsByUserId(userId);
        return products;
    }

    @Override
    public void updateProductsToInactive(List<Product> products) {
        for (Product product : products) {
            product.setActive(false);
        }
        productRepository.saveAll(products);
    }

}
