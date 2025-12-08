package mx.edu.utez.back.model;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private Role role;

    // Relación principal con la tienda (ID)
    private String mainStoreId;

    // Tiendas asignadas (IDs)
    private List<String> assignedStoreIds = new ArrayList<>();

    // Token FCM para notificaciones push
    private String fcmToken;
}
