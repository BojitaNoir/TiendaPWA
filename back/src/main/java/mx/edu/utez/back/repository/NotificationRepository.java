package mx.edu.utez.back.repository;

import mx.edu.utez.back.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Para obtener las más recientes primero
    List<Notification> findAllByOrderByDateDesc();
}