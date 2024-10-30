package com.arquiproject.svc_artisans.controller;

import com.arquiproject.svc_artisans.model.DTOs.PaymentInfoRequest;
import com.arquiproject.svc_artisans.service.PaymentService;
import com.arquiproject.svc_artisans.utils.ExtractJWT;
import com.stripe.model.PaymentIntent;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin("*")
public class PaymentController {
  private PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping("/payment-intent")
  public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest)
      throws StripeException {
    PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfoRequest);
    String paymentStr = paymentIntent.toJson();
    return new ResponseEntity<>(paymentStr, HttpStatus.OK);
  }

  @PutMapping("/payment-complete")
  public ResponseEntity<String> stripePaymentComplete(@RequestHeader(value="Authorization") String token)
      throws Exception {
    String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    if (userEmail == null) {
      throw new Exception("User email is missing");
    }
    return paymentService.stripePayment(userEmail);
  }
}
