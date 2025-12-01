package mx.edu.utez.back.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "temporary_assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Store store;

    @ManyToOne
    private User repartidor;

    private LocalDate date; // date of temporary visit
}
