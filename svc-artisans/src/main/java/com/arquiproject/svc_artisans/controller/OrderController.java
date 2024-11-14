package com.arquiproject.svc_artisans.controller;

import com.arquiproject.svc_artisans.model.DTOs.SaleInfo;
import com.arquiproject.svc_artisans.model.Order;
import com.arquiproject.svc_artisans.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService=orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order>createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        if(createdOrder != null){
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/one/{orderId}")
    public ResponseEntity<Order> findById(@PathVariable Long orderId){
        Order order = orderService.findById(orderId);
        if(order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/salesBy/{artisanId}")
    public ResponseEntity<List<SaleInfo>> getSalesByArtisan(@PathVariable Long artisanId) {
        List<SaleInfo> sales = orderService.getSalesByArtisan(artisanId);
        if (sales != null && !sales.isEmpty()) {
            return new ResponseEntity<>(sales, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
