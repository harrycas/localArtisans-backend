package com.arquiproject.svc_artisans.controller;

import com.arquiproject.svc_artisans.model.DTOs.PaymentInfoRequest;
import com.arquiproject.svc_artisans.service.PaymentService;
import com.stripe.model.Account;
import com.stripe.model.PaymentIntent;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @Operation(
      summary = "Crear cuenta del artesano con Stripe",
      description = "Stripe generará el id del usuario que se guardará en la BD para futuras transacciones, luego a partir de ese id " +
          "se generará el link de registro para el artesano, donde se hará la verificación y añadirá su cuenta bancaria",
      responses = {
          @ApiResponse(responseCode = "200", description = "Cuenta creada exitosamente"),
          @ApiResponse(responseCode = "404", description = "No se pudo crear la cuenta"),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor al crear la cuenta")
      })
  @PostMapping("/artisan/create-account")
  public ResponseEntity<String> createArtisanAccount(
      @Parameter(description = "Correo electrónico del artesano")
      @RequestParam String email,
      @Parameter(description = "ID del artesano")
      @RequestParam Long vendorId) {
    try {

      // Create connected account
      Account connectedAccount = paymentService.createConnectedAccount(email, vendorId);
      String accountId = connectedAccount.getId();

      // Generate Joining Link
      String joinLinkUrl = paymentService.createAccountLink(accountId);

      return ResponseEntity.ok(joinLinkUrl); // Enviar enlace al frontend
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating artisan account: " + e.getMessage());
    }
  }

  @Operation(
      summary = "Crear objeto PaymentIntent",
      description = "Genera un PaymentIntent con la información proporcionada para iniciar un proceso de pago en Stripe."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "PaymentIntent creado exitosamente"),
      @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
      @ApiResponse(responseCode = "500", description = "Error en el servidor al crear el PaymentIntent")
  })
  @PostMapping("/payment-intent")
  public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest) throws StripeException {
    // Llama al servicio para crear el PaymentIntent
    PaymentIntent paymentIntent = paymentService.createPaymentIntent(
        paymentInfoRequest.getTotalAmount(),
        paymentInfoRequest.getCurrency()
    );

    // Convierte el PaymentIntent a JSON y lo envía como respuesta
    String paymentStr = paymentIntent.toJson();
    return new ResponseEntity<>(paymentStr, HttpStatus.OK);
  }

  @Operation(
      summary = "Crear Transferencias",
      description = "Genera las transferencias correspondientes después de confirmar el pago."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Transferencias creadas exitosamente"),
      @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
      @ApiResponse(responseCode = "500", description = "Error en el servidor al crear las transferencias")
  })
  @PostMapping("/transfers")
  public ResponseEntity<String> createTransfers(@RequestBody PaymentInfoRequest paymentInfoRequest) throws StripeException {
    // Procesa las transferencias después de confirmar el pago
    paymentService.createTransfersAfterPayment(paymentInfoRequest.getProducts());
    return new ResponseEntity<>("Transfers created successfully", HttpStatus.OK);
  }

}
