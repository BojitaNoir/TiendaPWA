package mx.edu.utez.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
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
    private String createdAt; // Firebase uses Strings or Timestamps better, keeping String for ISO or use
                              // long

    private String storeId;
    private String repartidorId;

    private List<OrderItem> items = new ArrayList<>();

    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
