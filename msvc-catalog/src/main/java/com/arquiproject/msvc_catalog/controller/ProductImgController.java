package com.arquiproject.msvc_catalog.controller;

import com.arquiproject.msvc_catalog.model.ProductImg;
import com.arquiproject.msvc_catalog.service.ProductImgService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/v1/productImg")
@CrossOrigin("*")
public class ProductImgController {
  private static final String UPLOAD_DIR = "msvc-catalog/uploads/";
  private final ProductImgService productImgService;

  public ProductImgController(ProductImgService productImgService) {
    this.productImgService = productImgService;
  }

  @PostMapping("/upload")
  public ResponseEntity<String> uploadImg(@RequestParam("productID") Long productID, @RequestParam("file") MultipartFile file) {
    try {
      productImgService.addImageToProduct(productID,file);
      return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
    } catch (IOException e) {
      return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteImg(@RequestParam("productID") Long productID, @RequestParam("productImgId") Long productImgId) {
    productImgService.removeImageFromProduct(productID,productImgId);
    return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
  }

  @GetMapping("/{filename}")
  public ResponseEntity<Resource> getPhoto(@PathVariable String filename) {
    try {
      Path filePath = Paths.get(UPLOAD_DIR).resolve(filename).normalize();
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists()) {
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (MalformedURLException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/product/{productID}")
  public ResponseEntity<List<ProductImg>> getImagesByProductId(@PathVariable Long productID) {
    List<ProductImg> productImgs = productImgService.getImagesByProductId(productID);
    return new ResponseEntity<>(productImgs, HttpStatus.OK);
  }

}
