package mx.edu.utez.back.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "visits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Store store;

    @ManyToOne
    private User repartidor;

    private Instant timestamp = Instant.now();

    private Double latitude;
    private Double longitude;

    private Boolean hadOrder = false;

    private Boolean temporary = false;
}
