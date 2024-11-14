package com.arquiproject.svc_artisans.service;

import com.arquiproject.svc_artisans.model.DTOs.PaymentInfoRequest;
import com.arquiproject.svc_artisans.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.Transfer;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountLinkCreateParams;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class PaymentService {

  private final UserRepository userRepository;

  public PaymentService(@Value("${stripe.key.secret}") String secretKey, UserRepository userRepository) {
    Stripe.apiKey = secretKey;  // Initialize Stripe API with 'secret key'
    this.userRepository = userRepository;
  }

  /*public PaymentIntent createPaymentIntent(int totalAmount, String currency, List<PaymentInfoRequest.ProductPaymentInfo> products) throws StripeException {
    List<Map<String, Object>> transferDataList = new ArrayList<>();

    // Procesar cada producto y preparar la información de transferencia
    for (PaymentInfoRequest.ProductPaymentInfo product : products) {
      String vendorAccountId = userRepository.findStripeAccountIdById(product.getVendorId())
          .orElseThrow(() -> new IllegalArgumentException("Vendor account ID not found for vendorId: " + product.getVendorId()));

      // Calcular la cantidad después de aplicar la comisión del 15%
      int amountAfterFee = (int) Math.round(product.getAmount() * 0.85); // 15% de comisión

      // Agregar log para verificar el monto de cada transferencia
      System.out.println("Transfer Amount for vendor account " + vendorAccountId + ": " + amountAfterFee);

      // Verificar si el monto es menor al mínimo permitido y lanzar una excepción si es así
      if (amountAfterFee < 50) { // 50 centavos (ajústalo según el mínimo para tu moneda)
        System.err.println("Error: Amount is too low for transfer. Amount: " + amountAfterFee);
        throw new IllegalArgumentException("El monto de la transferencia es demasiado bajo para el vendedor con ID: " + product.getVendorId());
      }

      Map<String, Object> transferData = new HashMap<>();
      transferData.put("destination", vendorAccountId);
      transferData.put("amount", amountAfterFee); // Monto específico para este vendedor después de la comisión

      transferDataList.add(transferData);
    }

    // Preparar los parámetros del PaymentIntent
    Map<String, Object> params = new HashMap<>();
    params.put("amount", totalAmount);
    params.put("currency", currency);
    params.put("payment_method_types", List.of("card"));
    params.put("transfer_data", transferDataList);

    // Crear el PaymentIntent
    return PaymentIntent.create(params);
  }*/

  public PaymentIntent createPaymentIntent(int totalAmount, String currency) throws StripeException {
    Map<String, Object> params = new HashMap<>();
    params.put("amount", totalAmount);
    params.put("currency", currency);
    params.put("payment_method_types", List.of("card"));
    return PaymentIntent.create(params);
  }

  // Método para crear Transferencias después de confirmar el pago
  public void createTransfersAfterPayment(List<PaymentInfoRequest.ProductPaymentInfo> products) throws StripeException {
    for (PaymentInfoRequest.ProductPaymentInfo product : products) {
      String vendorAccountId = userRepository.findStripeAccountIdById(product.getVendorId())
          .orElseThrow(() -> new IllegalArgumentException("Vendor account ID not found for vendorId: " + product.getVendorId()));

      int amountAfterFee = (int) Math.round(product.getAmount() * 0.85); // 15% de comisión

      Map<String, Object> transferParams = new HashMap<>();
      transferParams.put("amount", amountAfterFee);
      transferParams.put("currency", "usd");
      transferParams.put("destination", vendorAccountId);

      Transfer.create(transferParams);
    }
  }

  // Create Connected Account
  public Account createConnectedAccount(String email, Long vendorId) throws Exception {
    AccountCreateParams params = AccountCreateParams.builder()
        .setType(AccountCreateParams.Type.EXPRESS) // Tipo de cuenta para vendedores
        .setCountry("US") // Cambia a tu país si es necesario
        .setEmail(email)
        .build();

    Account connectedAccount = Account.create(params);
    String accountId = connectedAccount.getId();

    // Actualizar el stripeAccountId en la base de datos
    System.out.println("Updating Stripe account for vendorId: " + vendorId + " with accountId: " + accountId);
    userRepository.updateStripeAccountId(vendorId, accountId);

    return connectedAccount;
  }

  // Create Joining Link
  public String createAccountLink(String accountId) throws Exception {
    AccountLinkCreateParams accountLinkParams = AccountLinkCreateParams.builder()
        .setAccount(accountId)
        .setRefreshUrl("https://053e-179-6-166-86.ngrok-free.app") // URL para reintentar si hay errores
        .setReturnUrl("https://053e-179-6-166-86.ngrok-free.app") // URL para redirigir después de completar el registro
        .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
        .build();

    AccountLink accountLink = AccountLink.create(accountLinkParams);
    return accountLink.getUrl(); // Este es el enlace que envías al frontend
  }

}
