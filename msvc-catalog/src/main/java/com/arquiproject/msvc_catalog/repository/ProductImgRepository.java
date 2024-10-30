package com.arquiproject.msvc_catalog.repository;

import com.arquiproject.msvc_catalog.model.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImg,Long> {
  List<ProductImg> findByProductId(Long productId);
}
