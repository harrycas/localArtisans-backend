package com.arquiproject.svc_artisans.model.DTOs;

import lombok.Data;

@Data
public class PaymentInfoRequest {
  private int amount;
  private String currency;
  private String receiptEmail;
}
