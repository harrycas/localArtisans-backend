package com.arquiproject.svc_artisans.model.DTOs;

import lombok.Data;

@Data
public class CheckoutRequest {
  private Long userId;
  private double totalAmount;
  private String deliveryAddress;
  private String deliveryCity;
  private String deliveryPostalCode;
}

