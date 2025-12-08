package mx.edu.utez.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String id;
    private String clientName;
    private Double totalPrice;
    private String status; // PENDIENTE, EN_PROCESO, COMPLETADO
    private String createdAt;

    // IDs for Firestore storage
    private String storeId;
    private String repartidorId;

    // Full objects for JSON responses (Firestore will ignore these if null)
    private Store store;
    private User repartidor;

    private List<OrderItem> items = new ArrayList<>();

    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
