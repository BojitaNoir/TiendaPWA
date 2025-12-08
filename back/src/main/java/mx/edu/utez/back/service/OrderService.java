package mx.edu.utez.back.service;

import mx.edu.utez.back.model.*;
import mx.edu.utez.back.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final StoreRepository storeRepo;
    private final UserRepository userRepo;
    private final NotificationService notificationService;

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
        try {
            List<Order> orders = orderRepo.findAll();

            // Populate related objects for each order
            for (Order order : orders) {
                // Populate Store
                if (order.getStoreId() != null) {
                    try {
                        Store store = storeRepo.findById(order.getStoreId());
                        order.setStore(store);
                    } catch (Exception e) {
                        // Store not found, leave null
                    }
                }

                // Populate Repartidor
                if (order.getRepartidorId() != null) {
                    try {
                        User repartidor = userRepo.findById(order.getRepartidorId());
                        order.setRepartidor(repartidor);
                    } catch (Exception e) {
                        // User not found, leave null
                    }
                }

                // Populate Products in OrderItems
                if (order.getItems() != null) {
                    for (OrderItem item : order.getItems()) {
                        if (item.getProductId() != null) {
                            try {
                                Product product = productRepo.findById(item.getProductId());
                                item.setProduct(product);
                            } catch (Exception e) {
                                // Product not found, leave null
                            }
                        }
                    }
                }
            }

            return orders;
        } catch (Exception e) {
            throw new RuntimeException("Error finding orders", e);
        }
    }

    public Order create(Order orderData) {
        try {
            Order newOrder = new Order();
            newOrder.generateId();
            newOrder.setClientName(orderData.getClientName());
            newOrder.setCreatedAt(LocalDateTime.now().toString());
            newOrder.setStatus("PENDIENTE");

            // 1. Asignar Tienda (Validar que exista)
            if (orderData.getStoreId() != null) {
                Store store = storeRepo.findById(orderData.getStoreId());
                if (store == null) {
                    throw new RuntimeException("Tienda no encontrada");
                }
                newOrder.setStoreId(store.getId());
            }

            // 2. Procesar Productos
            double totalCalculado = 0.0;
            List<OrderItem> itemsFinales = new ArrayList<>();

            if (orderData.getItems() != null) {
                for (OrderItem itemDTO : orderData.getItems()) {
                    // Validar producto
                    if (itemDTO.getProductId() == null)
                        continue;

                    Product p = productRepo.findById(itemDTO.getProductId());
                    if (p == null) {
                        throw new RuntimeException("Producto no encontrado: " + itemDTO.getProductId());
                    }

                    if (p.getStock() < itemDTO.getQuantity()) {
                        throw new RuntimeException("Stock insuficiente para: " + p.getName());
                    }

                    // Actualizar stock
                    p.setStock(p.getStock() - itemDTO.getQuantity());
                    productRepo.save(p, p.getId());

                    // Crear Item
                    OrderItem item = new OrderItem();
                    item.setId(UUID.randomUUID().toString()); // Generar ID para el item
                    item.setProductId(p.getId());
                    item.setQuantity(itemDTO.getQuantity());
                    item.setPrice(p.getPrice());

                    itemsFinales.add(item);
                    totalCalculado += p.getPrice() * itemDTO.getQuantity();
                }
            }

            newOrder.setItems(itemsFinales);
            newOrder.setTotalPrice(totalCalculado);

            // Clear nested objects before saving to Firestore (we only want IDs)
            newOrder.setStore(null);
            newOrder.setRepartidor(null);
            for (OrderItem item : newOrder.getItems()) {
                item.setProduct(null);
            }

            // Guardar orden
            orderRepo.save(newOrder, newOrder.getId());

            // 🔔 3. Notificar creación del pedido
            notificationService.registrarEvento(
                    "Nuevo Pedido",
                    "Se recibió el pedido #" + newOrder.getId() + " de " + newOrder.getClientName() + " ($"
                            + newOrder.getTotalPrice() + ")",
                    "info");

            return newOrder;
        } catch (Exception e) {
            throw new RuntimeException("Error creating order", e);
        }
    }

    public Order update(String id, Order orderDetails) {
        try {
            Order order = orderRepo.findById(id);
            if (order == null)
                throw new RuntimeException("Pedido no encontrado");

            // Actualizar estado
            if (orderDetails.getStatus() != null) {
                String oldStatus = order.getStatus();
                order.setStatus(orderDetails.getStatus());

                // 🔔 4. Notificar si se completó la entrega
                if (!"COMPLETADO".equals(oldStatus) && "COMPLETADO".equals(orderDetails.getStatus())) {
                    notificationService.registrarEvento(
                            "Entrega Exitosa",
                            "El pedido #" + order.getId() + " ha sido entregado al cliente.",
                            "success");
                }
            }

            // Asignar repartidor
            if (orderDetails.getRepartidorId() != null) {
                User rep = userRepo.findById(orderDetails.getRepartidorId());
                if (rep != null) {
                    order.setRepartidorId(rep.getId());
                }
            }

            orderRepo.save(order, id);
            return order;
        } catch (Exception e) {
            throw new RuntimeException("Error updating order", e);
        }
    }

    public void delete(String id) {
        try {
            orderRepo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting order", e);
        }
    }
}