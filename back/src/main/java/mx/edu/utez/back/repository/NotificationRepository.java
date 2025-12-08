package mx.edu.utez.back.repository;

import mx.edu.utez.back.model.Notification;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepository extends AbstractFirestoreRepository<Notification> {
    public NotificationRepository() {
        super("notifications", Notification.class);
    }
}