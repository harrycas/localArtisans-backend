package com.arquiproject.svc_artisans.model.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "Información de pago para crear un PaymentIntent en Stripe")
public class PaymentInfoRequest {
  @Schema(description = "Monto total de la transacción", example = "5000")
  private int totalAmount;
  @Schema(description = "Moneda en la que se realizará la transacción", example = "USD")
  private String currency;
  @Schema(description = "Lista de productos, cantidades y sus vendedores")
  private List<ProductPaymentInfo> products;

  @Data
  @Schema(description = "Información de un producto en la transacción")
  public static class ProductPaymentInfo {
    @Schema(description = "ID del producto", example = "001")
    private Long productId;
    @Schema(description = "ID del vendedor asociado a este producto", example = "025")
    private Long vendorId;
    @Schema(description = "Monto correspondiente a este producto en centavos", example = "2500")
    private int amount;
  }
}

