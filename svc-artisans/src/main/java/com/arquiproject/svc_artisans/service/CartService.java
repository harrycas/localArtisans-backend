package com.arquiproject.svc_artisans.service;

import com.arquiproject.svc_artisans.model.Order;
import com.arquiproject.svc_artisans.model.User;
import com.arquiproject.svc_artisans.model.OrderProduct;
import com.arquiproject.svc_artisans.repository.OrderProductRepository;
import com.arquiproject.svc_artisans.repository.OrderRepository;
import com.arquiproject.svc_artisans.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

  final private OrderRepository orderRepository;
  final private OrderProductRepository orderProductRepository;
  final private UserRepository userRepository;

  public CartService(OrderRepository orderRepository, OrderProductRepository orderProductRepository, UserRepository userRepository) {
    this.orderRepository = orderRepository;
    this.orderProductRepository = orderProductRepository;
    this.userRepository = userRepository;
  }

  public Order getOrCreateCartByUser(Long userId) {
    return orderRepository.findByUserIdAndIsCart(userId, true)
        .orElseGet(() -> createCartForUser(userId));
  }

  private Order createCartForUser(Long userId) {
    Order cart = new Order();
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User Not Found"));
    cart.setUser(user);
    cart.setCart(true);
    return orderRepository.save(cart);
  }

  public void addProductToCart(Long userId, Long productId, int quantity) {

    /*if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive");
    }*/

    // Retrieve or create the Shopping Cart
    Order cart = getOrCreateCartByUser(userId);

    // Verify if the product already exists in the Cart
    Optional<OrderProduct> existingOrderProduct = orderProductRepository
        .findByOrderIdAndProductId(cart.getId(), productId);

    OrderProduct orderProduct;
    if (existingOrderProduct.isPresent()) {
      orderProduct = existingOrderProduct.get();
      orderProduct.setQuantity(orderProduct.getQuantity() + quantity);
    } else {
      orderProduct = new OrderProduct();
      orderProduct.setProductId(productId);
      orderProduct.setQuantity(quantity);
      orderProduct.setOrder(cart);

    }
    orderProductRepository.save(orderProduct);
  }

  public void removeProductFromCart(Long userId, Long productId, int quantity) {

    // Retrieve the Shopping Cart
    Order cart = getOrCreateCartByUser(userId);

    // Verify if the product already exists in the Cart
    Optional<OrderProduct> existingOrderProduct = orderProductRepository
        .findByOrderIdAndProductId(cart.getId(), productId);

    if (existingOrderProduct.isPresent()) {
      OrderProduct orderProduct = existingOrderProduct.get();

      // Calculate new Quantity
      int newQuantity = orderProduct.getQuantity() - quantity;

      if (newQuantity > 0) {
        // Update the quantity
        orderProduct.setQuantity(newQuantity);
        orderProductRepository.save(orderProduct);
      } else {
        // If it is below 0, then eliminate the OrderProduct
        orderProductRepository.delete(orderProduct);
      }
    } else {
      throw new IllegalArgumentException("Product does not exist in the User's cart");
    }
  }


}
