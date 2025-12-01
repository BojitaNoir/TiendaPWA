package mx.edu.utez.back.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String code; // QR or barcode value

    private Double latitude;
    private Double longitude;
}
