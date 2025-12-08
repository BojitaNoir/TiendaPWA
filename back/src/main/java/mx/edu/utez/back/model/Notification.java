package mx.edu.utez.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private String id;
    private String title; // Ej: "Nuevo Pedido"
    private String message; // Ej: "Se ha registrado el pedido #50"
    private String type; // "info", "success", "warning", "danger"
    private String date; // ISO String

    public void setDateNow() {
        this.date = LocalDateTime.now().toString();
    }
}
