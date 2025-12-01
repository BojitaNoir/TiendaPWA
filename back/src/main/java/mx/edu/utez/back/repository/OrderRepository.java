package mx.edu.utez.back.repository;

import mx.edu.utez.back.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByRepartidorId(Long repartidorId);
}
