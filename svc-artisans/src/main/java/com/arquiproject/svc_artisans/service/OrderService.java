package com.arquiproject.svc_artisans.service;

import com.arquiproject.svc_artisans.client.CatalogClientRest;
import com.arquiproject.svc_artisans.model.DTOs.ProductInfo;
import com.arquiproject.svc_artisans.model.DTOs.SaleInfo;
import com.arquiproject.svc_artisans.model.Order;
import com.arquiproject.svc_artisans.model.OrderProduct;
import com.arquiproject.svc_artisans.model.enums.OrderStatus;
import com.arquiproject.svc_artisans.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public List<SaleInfo> getSalesByArtisan(Long artisanId) {
        List<Order> completedOrders = orderRepository.findByIsCartFalse();
        List<SaleInfo> sales = new ArrayList<>();

        for (Order order : completedOrders) {
            for (OrderProduct orderProduct : order.getOrderProducts()) {
                try {
                    ProductInfo productInfo = clientRest.getProductById(orderProduct.getProductId());

                    if (productInfo != null && productInfo.getUserId().equals(artisanId)) {
                        SaleInfo sale = new SaleInfo();
                        sale.setOrderId(order.getId());
                        sale.setProductName(productInfo.getName());
                        sale.setProductPrice(productInfo.getPrice());
                        sale.setQuantity(orderProduct.getQuantity());
                        sale.setOrderDate(order.getOrderDate());
                        sale.setCategoryId(productInfo.getCategoryId());
                        sale.setStatus(order.getStatus());

                        sales.add(sale);
                    }
                } catch (Exception e) {
                    System.err.println("Can not get info of product " + orderProduct.getProductId() + ": " + e.getMessage());
                    // Register error in a log or ignore it
                }
            }
        }

        return sales;
    }

}
