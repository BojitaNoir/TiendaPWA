package mx.edu.utez.back.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime checkInTime;

    // Coordenadas GPS del check-in
    private Double latitude;
    private Double longitude;

    // ✅ ESTOS CAMPOS FALTABAN
    private boolean hadOrder; // ¿Tenía pedido asignado?
    private boolean temporaryAssignment; // ¿Fue una asignación temporal?

    @ManyToOne
    @JoinColumn(name = "repartidor_id")
    private User repartidor;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @PrePersist
    protected void onCreate() {
        checkInTime = LocalDateTime.now();
    }
}