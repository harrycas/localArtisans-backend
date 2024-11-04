package com.arquiproject.svc_artisans.model.DTOs;

import lombok.Data;

@Data
public class ProductInfo {
  private Long productId;
  private String name;
  private String description;
  private double price;
  private boolean active;
  private Long userId;
  private Long categoryId;
  private String primaryImageUrl;
}
