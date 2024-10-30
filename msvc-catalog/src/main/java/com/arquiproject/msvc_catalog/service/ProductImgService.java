package com.arquiproject.msvc_catalog.service;

import com.arquiproject.msvc_catalog.model.ProductImg;
import com.arquiproject.msvc_catalog.repository.ProductImgRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ProductImgService implements IProductImgService {
  private static final String UPLOAD_DIR = "msvc-catalog/uploads/";
  private final ProductImgRepository productImgRepository;

  public ProductImgService(ProductImgRepository productImgRepository) {
    this.productImgRepository = productImgRepository;
  }

  @Override
  public void addImageToProduct(Long productId, MultipartFile file) throws IOException {
    String fileName = file.getOriginalFilename();
    Path filePath = Paths.get(UPLOAD_DIR + fileName);
    Files.copy(file.getInputStream(), filePath);

    // New Photo instance with thr url and userId
    ProductImg productImg = new ProductImg();
    productImg.setUrl(filePath.toString());
    productImg.setProductId(productId);
    productImgRepository.save(productImg);
  }

  @Override
  public void removeImageFromProduct(Long productId, Long productImgId) {
    Optional<ProductImg> productImgOptional = productImgRepository.findById(productImgId);
    if (productImgOptional.isPresent()) {
      ProductImg productImg = productImgOptional.get();
      if (productImg.getProductId()==productId) {
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
}
