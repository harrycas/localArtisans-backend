package com.arquiproject.svc_artisans.controller;

import com.arquiproject.svc_artisans.model.Order;
import com.arquiproject.svc_artisans.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
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

    @GetMapping("/assigned/{deliveryPersonId}/{orderId}")
    public ResponseEntity<Order> findOrderByDeliveryPersonIdAndOrderId(
            @PathVariable Long deliveryPersonId,
            @PathVariable Long orderId) {
        Order order = orderService.findOrderByDeliveryPersonAndId(deliveryPersonId, orderId);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/assigned/{deliveryPersonId}")
    public ResponseEntity<List<Order>> getAssignedOrders(@PathVariable Long deliveryPersonId) {
        List<Order> assignedOrders = orderService.getOrdersByDeliveryPerson(deliveryPersonId);
        return new ResponseEntity<>(assignedOrders, HttpStatus.OK);
    }

}
