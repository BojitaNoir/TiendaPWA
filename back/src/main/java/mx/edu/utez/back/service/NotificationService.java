package mx.edu.utez.back.service;

import mx.edu.utez.back.model.Notification;
import mx.edu.utez.back.model.Role;
import mx.edu.utez.back.repository.NotificationRepository;
import mx.edu.utez.back.repository.UserRepository;
import mx.edu.utez.back.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio de notificaciones mejorado
 * Maneja notificaciones push FCM y almacenamiento en BD
 * 
 * @author TiendaPWA Team
 */
@Service
public class NotificationService {
    private final NotificationRepository repo;
    private final UserRepository userRepo;
    private final FirebaseMessagingService fcmService;

    public NotificationService(NotificationRepository repo, UserRepository userRepo,
            FirebaseMessagingService fcmService) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.fcmService = fcmService;
    }

    /**
     * Registrar evento genérico (deprecado - usar métodos específicos)
     */
    @Deprecated
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

            // 2. Enviar push a administradores
            notificarAdministradores(title, message, null);

        } catch (Exception e) {
            System.err.println("Error registrando evento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ═══════════════════════════════════════════════════
    // 🆕 MÉTODOS ESPECÍFICOS PARA TIENDA PWA
    // ═══════════════════════════════════════════════════

    /**
     * CASO 1: Notificar al ADMIN cuando se crea un nuevo pedido
     */
    public void notificarNuevoPedido(String orderId, String storeName, Double totalPrice) {
        try {
            String titulo = "🛒 Nuevo Pedido Recibido";
            String mensaje = String.format("Pedido de %s por $%.2f", storeName, totalPrice);

            // Guardar en historial
            guardarNotificacion(titulo, mensaje, "nuevo_pedido");

            // Datos adicionales para la notificación
            Map<String, String> data = new HashMap<>();
            data.put("tipo", "nuevo_pedido");
            data.put("orderId", orderId);
            data.put("storeName", storeName);
            data.put("totalPrice", totalPrice.toString());

            // Enviar push a todos los administradores
            notificarAdministradores(titulo, mensaje, data);

            System.out.println("✅ Notificación de nuevo pedido enviada a administradores");
        } catch (Exception e) {
            System.err.println("❌ Error notificando nuevo pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * CASO 2: Notificar al REPARTIDOR cuando el admin le asigna una visita temporal
     */
    public void notificarAsignacionTemporal(String repartidorId, String storeName, String fecha) {
        try {
            // Obtener repartidor
            User repartidor = userRepo.findById(repartidorId);
            if (repartidor == null) {
                System.out.println("⚠️ Repartidor no encontrado: " + repartidorId);
                return;
            }

            String titulo = "📅 Nueva Visita Temporal Asignada";
            String mensaje = String.format("Se te asignó visitar \"%s\" el %s", storeName, fecha);

            // Guardar en historial (opcional - podría ser solo BD del repartidor)
            guardarNotificacion(titulo, mensaje, "asignacion_temporal");

            // Datos adicionales
            Map<String, String> data = new HashMap<>();
            data.put("tipo", "asignacion_temporal");
            data.put("storeName", storeName);
            data.put("fecha", fecha);
            data.put("repartidorId", repartidorId);

            // Enviar push al repartidor
            if (repartidor.getFcmToken() != null && !repartidor.getFcmToken().isEmpty()) {
                fcmService.enviarNotificacion(repartidor.getFcmToken(), titulo, mensaje, data);
                System.out.println("✅ Notificación de asignación temporal enviada a: " + repartidor.getName());
            } else {
                System.out.println("⚠️ Repartidor " + repartidor.getName() + " no tiene token FCM registrado");
            }

        } catch (Exception e) {
            System.err.println("❌ Error notificando asignación temporal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * CASO 3: Notificar cuando un pedido cambia de estado
     */
    public void notificarCambioEstadoPedido(String orderId, String nuevoEstado, String storeName) {
        try {
            String titulo = "📦 Actualización de Pedido";
            String mensaje = String.format("El pedido de %s cambió a: %s", storeName, nuevoEstado);

            // Guardar en historial
            guardarNotificacion(titulo, mensaje, "cambio_estado");

            // Datos adicionales
            Map<String, String> data = new HashMap<>();
            data.put("tipo", "cambio_estado");
            data.put("orderId", orderId);
            data.put("nuevoEstado", nuevoEstado);
            data.put("storeName", storeName);

            // Enviar a administradores
            notificarAdministradores(titulo, mensaje, data);

        } catch (Exception e) {
            System.err.println("❌ Error notificando cambio de estado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * CASO 4: Notificar a un repartidor específico
     */
    public void notificarRepartidor(String repartidorId, String titulo, String mensaje, Map<String, String> data) {
        try {
            User repartidor = userRepo.findById(repartidorId);
            if (repartidor == null) {
                System.out.println("⚠️ Repartidor no encontrado: " + repartidorId);
                return;
            }

            if (repartidor.getFcmToken() != null && !repartidor.getFcmToken().isEmpty()) {
                fcmService.enviarNotificacion(repartidor.getFcmToken(), titulo, mensaje, data);
                System.out.println("✅ Notificación enviada a repartidor: " + repartidor.getName());
            } else {
                System.out.println("⚠️ Repartidor no tiene token FCM");
            }
        } catch (Exception e) {
            System.err.println("❌ Error notificando repartidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ═══════════════════════════════════════════════════
    // 🔧 MÉTODOS AUXILIARES
    // ═══════════════════════════════════════════════════

    /**
     * Enviar notificación push a todos los administradores
     */
    private void notificarAdministradores(String titulo, String mensaje, Map<String, String> data) {
        try {
            List<User> users = userRepo.findAll();

            // Filtrar solo administradores con token FCM
            List<String> adminTokens = users.stream()
                    .filter(u -> u.getRole() == Role.ADMIN)
                    .filter(u -> u.getFcmToken() != null && !u.getFcmToken().isEmpty())
                    .map(User::getFcmToken)
                    .collect(Collectors.toList());

            if (!adminTokens.isEmpty()) {
                fcmService.enviarNotificacionMultiple(adminTokens, titulo, mensaje, data);
                System.out.println("📤 Notificación enviada a " + adminTokens.size() + " administrador(es)");
            } else {
                System.out.println("⚠️ No hay administradores con tokens FCM disponibles");
            }
        } catch (Exception e) {
            System.err.println("❌ Error enviando a administradores: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Guardar notificación en el historial de Firestore
     */
    private void guardarNotificacion(String title, String message, String type) {
        try {
            Notification n = new Notification();
            n.setId(java.util.UUID.randomUUID().toString());
            n.setTitle(title);
            n.setMessage(message);
            n.setType(type);
            n.setDateNow();
            repo.save(n, n.getId());
            System.out.println("💾 Notificación guardada en historial");
        } catch (Exception e) {
            System.err.println("❌ Error guardando notificación: " + e.getMessage());
        }
    }

    /**
     * Obtener historial de notificaciones
     */
    public List<Notification> findAll() {
        try {
            return repo.findAll();
        } catch (Exception e) {
            System.err.println("❌ Error obteniendo notificaciones: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
}
