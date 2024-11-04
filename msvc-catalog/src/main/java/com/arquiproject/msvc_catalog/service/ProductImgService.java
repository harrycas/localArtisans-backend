package com.arquiproject.msvc_catalog.service;

import com.arquiproject.msvc_catalog.exception.ResourceNotFoundException;
import com.arquiproject.msvc_catalog.model.Product;
import com.arquiproject.msvc_catalog.model.ProductImg;
import com.arquiproject.msvc_catalog.repository.ProductImgRepository;
import com.arquiproject.msvc_catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductImgService implements IProductImgService {
  private static final String UPLOAD_DIR = "msvc-catalog/uploads/";
  private final ProductImgRepository productImgRepository;
  private final ProductRepository productRepository;

  public ProductImgService(ProductImgRepository productImgRepository, ProductRepository productRepository) {
    this.productImgRepository = productImgRepository;
    this.productRepository = productRepository;
  }

  @Override
  public void addImageToProduct(Long productId, MultipartFile file) throws IOException {
    String originalFileName = file.getOriginalFilename();
    String fileExtension = "";

    // Extract the extension from the file (if there is one)
    if (originalFileName != null && originalFileName.contains(".")) {
      fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    // Create a unique name for the image file
    String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
    Path filePath = Paths.get(UPLOAD_DIR + uniqueFileName);
    Files.copy(file.getInputStream(), filePath);

    // Get Product by its id
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));

    // New Photo instance with thr url and userId
    ProductImg productImg = new ProductImg();
    productImg.setUrl(filePath.toString());
    productImg.setProduct(product);
    productImgRepository.save(productImg);
  }

  @Override
  public void removeImageFromProduct(Long productId, Long productImgId) {
    Optional<ProductImg> productImgOptional = productImgRepository.findById(productImgId);
    if (productImgOptional.isPresent()) {
      ProductImg productImg = productImgOptional.get();
      if (productImg.getProduct() != null && productImg.getProduct().getId().equals(productId)) {
        // Delete the photo from the Directory
        File file = new File(productImg.getUrl());
        if (file.exists()) {
          file.delete();
        }
        productImgRepository.delete(productImg);
      }
    }
  }

  @Override
  public List<ProductImg> getImagesByProductId(Long productId) {
    return productImgRepository.findByProductId(productId);
  }

  public void setPrimaryImage(Long productId, Long imageId) {

    // Set all images isPrimary attribute as False
    List<ProductImg> productImages = productImgRepository.findByProductId(productId);
    for (ProductImg img : productImages) {
      img.setPrimary(false);
    }

    // Set an image as Principal
    ProductImg primaryImage = productImgRepository.findById(imageId)
        .orElseThrow(() -> new ResourceNotFoundException("Image not found with id " + imageId));

    if (primaryImage.getProduct() == null || !primaryImage.getProduct().getId().equals(productId)) {
      throw new IllegalArgumentException("The image does not belong to the specified product");
    }

    primaryImage.setPrimary(true);
    productImgRepository.saveAll(productImages);
    productImgRepository.save(primaryImage);
  }

}
