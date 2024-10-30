package com.arquiproject.msvc_catalog.service;

import com.arquiproject.msvc_catalog.model.ProductImg;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProductImgService {
  void addImageToProduct(Long productId, MultipartFile file) throws IOException;
  void removeImageFromProduct(Long productId, Long productImgId);
  List<ProductImg> getImagesByProductId(Long productId);
}
