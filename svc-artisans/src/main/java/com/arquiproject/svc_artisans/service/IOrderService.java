package com.arquiproject.svc_artisans.service;

import com.arquiproject.svc_artisans.model.Order;

public interface IOrderService {
    Order createOrder(Order order);
    Order findById(Long orderId);
}
