package com.arquiproject.svc_artisans.service;

import com.arquiproject.svc_artisans.client.CatalogClientRest;
import com.arquiproject.svc_artisans.model.DTOs.ProductInfo;
import com.arquiproject.svc_artisans.model.Order;
import com.arquiproject.svc_artisans.model.enums.OrderStatus;
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

    public Order checkout(Long userId, double totalAmount, String deliveryAddress, String deliveryCity, String deliveryPostalCode) {

        // Retrieve the Shopping Cart
        Order cart = orderRepository.findByUserIdAndIsCart(userId, true)
            .orElseThrow(() -> new IllegalArgumentException("Cart does not exist for this user"));

        // Verify if the cart has items
        if (cart.getOrderProducts().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        // Amount from the Cart
        cart.setTotal(totalAmount);

        // Set Delivery Info
        cart.setDeliveryAddress(deliveryAddress);
        cart.setDeliveryCity(deliveryCity);
        cart.setDeliveryPostalCode(deliveryPostalCode);
        cart.setOrderDate(LocalDateTime.now());
        cart.setCart(false);  // Set order as final
        cart.setStatus(OrderStatus.PREPARING);

        return orderRepository.save(cart);
    }

    // 2nd SPRINT
    /*public Order updateOrderStatusToOnTheWay(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Cambiar el estado y establecer la fecha de recogida
        order.setStatus(OrderStatus.ON_THE_WAY);
        order.setShipDate(LocalDateTime.now());

        // Estimar la fecha de entrega, por ejemplo, 2 días después de la recogida
        order.setDeliveryDate(order.getShipDate().plus(2, ChronoUnit.DAYS));

        return orderRepository.save(order);
    }*/


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
