package mx.edu.utez.back.repository;

import mx.edu.utez.back.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository extends AbstractFirestoreRepository<Order> {
    public OrderRepository() {
        super("orders", Order.class);
    }
}
