// mx.edu.utez.back.service.NotificationService.java
package mx.edu.utez.back.service;

import mx.edu.utez.back.model.Notification;
import mx.edu.utez.back.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public void registrarEvento(String title, String message, String type) {
        Notification n = new Notification();
        n.setTitle(title);
        n.setMessage(message);
        n.setType(type);
        repo.save(n);
    }

    public List<Notification> findAll() {
        return repo.findAll(); // O repo.findAllByOrderByDateDesc() si lo creaste
    }
}