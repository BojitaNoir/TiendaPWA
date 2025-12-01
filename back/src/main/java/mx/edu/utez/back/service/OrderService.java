package mx.edu.utez.back.service;

import mx.edu.utez.back.model.Order;
import mx.edu.utez.back.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order create(Order o) {
        return orderRepository.save(o);
    }

    public List<Order> findByRepartidor(Long repartidorId) {
        return orderRepository.findByRepartidorId(repartidorId);
    }
}
