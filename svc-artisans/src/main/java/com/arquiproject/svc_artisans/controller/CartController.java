package com.arquiproject.svc_artisans.controller;

import com.arquiproject.svc_artisans.model.DTOs.CheckoutRequest;
import com.arquiproject.svc_artisans.model.DTOs.ProductInfo;
import com.arquiproject.svc_artisans.service.CartService;
import com.arquiproject.svc_artisans.service.OrderService;
import com.arquiproject.svc_artisans.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

  private final CartService cartService;
  private final OrderService orderService;

  public CartController(CartService cartService, OrderService orderService) {
    this.cartService = cartService;
    this.orderService = orderService;
  }

  @PostMapping("/add")
  public ResponseEntity<String> addProductToCart(@RequestParam Long userId,
                                                 @RequestParam Long productId,
                                                 @RequestParam int quantity) {
    try {
      cartService.addProductToCart(userId, productId, quantity);
      return ResponseEntity.ok("Product added to cart");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping("/remove")
  public ResponseEntity<String> removeProductFromCart(@RequestParam Long userId,
                                                      @RequestParam Long productId,
                                                      @RequestParam int quantity) {
    try {
      cartService.removeProductFromCart(userId, productId, quantity);
      return ResponseEntity.ok("Product deleted from cart");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/{userId}")
  public ResponseEntity<Order> getCartByUser(@PathVariable Long userId) {
    Order cart = cartService.getOrCreateCartByUser(userId);
    return ResponseEntity.ok(cart);
  }

  @PostMapping("/checkout")
  public ResponseEntity<Order> checkout(@RequestBody CheckoutRequest request) {
    Long userId = request.getUserId();
    double totalAmount = request.getTotalAmount();
    String deliveryAddress = request.getDeliveryAddress();
    String deliveryCity = request.getDeliveryCity();
    String deliveryPostalCode = request.getDeliveryPostalCode();

    Order order = orderService.checkout(userId, totalAmount, deliveryAddress,deliveryCity,deliveryPostalCode);
    return new ResponseEntity<>(order, HttpStatus.OK);

  }

  @GetMapping("/info/{productId}")
  public ResponseEntity<ProductInfo> getProductDetails(@PathVariable Long productId) {
    ProductInfo productInfo = cartService.getProductDetails(productId);
    return ResponseEntity.ok(productInfo);
  }

}
