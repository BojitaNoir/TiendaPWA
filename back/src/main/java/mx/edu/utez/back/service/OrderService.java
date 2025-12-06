package mx.edu.utez.back.service;

import mx.edu.utez.back.model.*;
import mx.edu.utez.back.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final StoreRepository storeRepo;
    private final UserRepository userRepo;

    // 🔔 1. Declaramos el servicio de notificaciones
    private final NotificationService notificationService;

    // 🔔 2. Lo agregamos al Constructor (Inyección de Dependencias)
    public OrderService(OrderRepository orderRepo,
                        ProductRepository productRepo,
                        StoreRepository storeRepo,
                        UserRepository userRepo,
                        NotificationService notificationService) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.storeRepo = storeRepo;
        this.userRepo = userRepo;
        this.notificationService = notificationService;
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    @Transactional
    public Order create(Order orderData) {
        Order newOrder = new Order();
        newOrder.setClientName(orderData.getClientName());
        newOrder.setCreatedAt(LocalDateTime.now());
        newOrder.setStatus("PENDIENTE");

        // 1. Asignar Tienda
        if (orderData.getStore() != null && orderData.getStore().getId() != null) {
            Store store = storeRepo.findById(orderData.getStore().getId())
                    .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));
            newOrder.setStore(store);
        }

        // 2. Procesar Productos
        double totalCalculado = 0.0;
        List<OrderItem> itemsFinales = new ArrayList<>();

        if (orderData.getItems() != null) {
            for (OrderItem itemDTO : orderData.getItems()) {
                Product p = productRepo.findById(itemDTO.getProduct().getId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                if (p.getStock() < itemDTO.getQuantity()) {
                    throw new RuntimeException("Stock insuficiente para: " + p.getName());
                }

                p.setStock(p.getStock() - itemDTO.getQuantity());
                productRepo.save(p);

                OrderItem item = new OrderItem();
                item.setProduct(p);
                item.setQuantity(itemDTO.getQuantity());
                item.setPrice(p.getPrice());
                item.setOrder(newOrder);

                itemsFinales.add(item);
                totalCalculado += p.getPrice() * itemDTO.getQuantity();
            }
        }

        newOrder.setItems(itemsFinales);
        newOrder.setTotalPrice(totalCalculado);

        Order savedOrder = orderRepo.save(newOrder);

        // 🔔 3. Notificar creación del pedido
        notificationService.registrarEvento(
                "Nuevo Pedido",
                "Se recibió el pedido #" + savedOrder.getId() + " de " + savedOrder.getClientName() + " ($" + savedOrder.getTotalPrice() + ")",
                "info"
        );

        return savedOrder;
    }

    @Transactional
    public Order update(Long id, Order orderDetails) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // Actualizar estado
        if (orderDetails.getStatus() != null) {
            String oldStatus = order.getStatus();
            order.setStatus(orderDetails.getStatus());

            // 🔔 4. Notificar si se completó la entrega
            if (!"COMPLETADO".equals(oldStatus) && "COMPLETADO".equals(orderDetails.getStatus())) {
                notificationService.registrarEvento(
                        "Entrega Exitosa",
                        "El pedido #" + order.getId() + " ha sido entregado al cliente.",
                        "success"
                );
            }
        }

        // Asignar repartidor
        if (orderDetails.getRepartidor() != null && orderDetails.getRepartidor().getId() != null) {
            User rep = userRepo.findById(orderDetails.getRepartidor().getId()).orElse(null);
            order.setRepartidor(rep);
        }

        return orderRepo.save(order);
    }

    public void delete(Long id) {
        orderRepo.deleteById(id);
    }
}