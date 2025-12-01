package mx.edu.utez.back.controller;

import mx.edu.utez.back.model.Order;
import mx.edu.utez.back.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order o) {
        return ResponseEntity.ok(orderService.create(o));
    }

    @GetMapping("/by-repartidor/{id}")
    public ResponseEntity<List<Order>> byRepartidor(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findByRepartidor(id));
    }
}
