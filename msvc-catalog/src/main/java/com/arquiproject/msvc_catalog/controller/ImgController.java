package com.arquiproject.msvc_catalog.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/images")
@CrossOrigin("*")
public class ImgController {

  private static final String UPLOAD_DIR = "msvc-catalog/uploads/";

  @GetMapping("/{filename}")
  public ResponseEntity<Resource> getImage(@PathVariable String filename) {
    try {
      Path imagePath = Paths.get(UPLOAD_DIR, filename);
      Resource image = new UrlResource(imagePath.toUri());

      if (image.exists() && image.isReadable()) {
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(image);
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
    } catch (MalformedURLException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}

