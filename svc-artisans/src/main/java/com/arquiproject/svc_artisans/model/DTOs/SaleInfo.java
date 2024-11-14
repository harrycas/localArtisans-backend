package com.arquiproject.svc_artisans.model.DTOs;

import com.arquiproject.svc_artisans.model.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SaleInfo {
  private Long orderId;
  private String productName;
  private double productPrice;
  private int quantity;
  private LocalDateTime orderDate;
  private Long categoryId;
  private OrderStatus status;
}
