package com.arquiproject.svc_artisans.service;

import com.arquiproject.svc_artisans.client.CatalogClientRest;
import com.arquiproject.svc_artisans.model.Order;
import com.arquiproject.svc_artisans.model.OrderProduct;
import com.arquiproject.svc_artisans.model.Product;
import com.arquiproject.svc_artisans.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class OrderService implements IOrderService{

    final private OrderRepository orderRepository;

//    private CatalogClientRest clientRest;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order findById(int orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

}
