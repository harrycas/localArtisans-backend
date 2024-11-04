package com.arquiproject.svc_artisans.service;

import com.arquiproject.svc_artisans.client.CatalogClientRest;
import com.arquiproject.svc_artisans.model.DTOs.ProductInfo;
import com.arquiproject.svc_artisans.model.Order;
import com.arquiproject.svc_artisans.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;

@Service
public class OrderService implements IOrderService{

    final private OrderRepository orderRepository;
    final private CatalogClientRest clientRest;

    public OrderService(OrderRepository orderRepository, CatalogClientRest clientRest) {
        this.orderRepository = orderRepository;
        this.clientRest = clientRest;
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public Order checkout(Long userId) {

        // Retrieve the Shopping Cart
        Order cart = orderRepository.findByUserIdAndIsCart(userId, true)
            .orElseThrow(() -> new IllegalArgumentException("Cart does not exist for this user"));

        // Verify if the cart has items
        if (cart.getOrderProducts().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        // Calculate total
        double subtotal = cart.getOrderProducts().stream()
            .mapToDouble(op -> op.getQuantity() * getProductPrice(op.getProductId()))
            .sum();

        double discount = cart.getDiscount();
        double total = subtotal;
        if (discount > 0) {
            total = subtotal - (subtotal * (discount / 100));
        }

        cart.setTotal(total);
        cart.setCart(false);  // Change state to Final Order
        cart.setOrderDate(LocalDateTime.now());

        return orderRepository.save(cart);
    }

    private double getProductPrice(Long productId) {
        // Call to Microservice Catalog
        ProductInfo productInfo = clientRest.getProductById(productId);

        // Verify product exists or is Active
        if (productInfo == null || !productInfo.isActive()) {
            throw new IllegalArgumentException("Product not found or inactive");
        }

        return productInfo.getPrice();
    }

}
