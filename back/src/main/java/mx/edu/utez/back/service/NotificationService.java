package mx.edu.utez.back.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import mx.edu.utez.back.model.Notification;
import mx.edu.utez.back.repository.NotificationRepository;
import mx.edu.utez.back.repository.UserRepository;
import mx.edu.utez.back.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository repo;
    private final UserRepository userRepo; // Necesitamos buscar usuarios para obtener tokens

    public NotificationService(NotificationRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    public void registrarEvento(String title, String message, String type) {
        try {
            // 1. Guardar en BD (Historial)
            Notification n = new Notification();
            n.setId(java.util.UUID.randomUUID().toString());
            n.setTitle(title);
            n.setMessage(message);
            n.setType(type);
            n.setDateNow();
            repo.save(n, n.getId());

            // 2. Enviar Notificación Push (FCM) a TODOS los Admin
            List<User> users = userRepo.findAll();
            for (User u : users) {
                // Filtramos por rol ADMIN o si queremos notificar a todos
                if (u.getFcmToken() != null && !u.getFcmToken().isEmpty()) {
                    sendPush(u.getFcmToken(), title, message);
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // Log error but don't crash flow?
        }
    }

    private void sendPush(String token, String title, String body) {
        try {
            Message msg = Message.builder()
                    .setToken(token)
                    .putData("title", title)
                    .putData("body", body)
                    .setNotification(com.google.firebase.messaging.Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();
            FirebaseMessaging.getInstance().send(msg);
            System.out.println("Push notification sent to: " + token);
        } catch (Exception e) {
            System.err.println("Error sending push: " + e.getMessage());
        }
    }

    public List<Notification> findAll() {
        try {
            return repo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Return empty list on error
        }
    }
}
