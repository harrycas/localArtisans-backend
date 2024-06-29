package com.arquiproject.svc_artisans.service;

import com.arquiproject.svc_artisans.model.Order;
import com.arquiproject.svc_artisans.model.Product;

import java.util.List;
import java.util.Optional;

public interface IOrderService {

    Order createOrder(Order order);
    Order findById(int orderId);

}
