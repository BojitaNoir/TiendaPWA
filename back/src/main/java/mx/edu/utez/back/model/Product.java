package mx.edu.utez.back.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String sku;
    private Double price;
    private Integer stock = 0;
    private String description;
    private Boolean available = true;
    
    @Column(columnDefinition = "LONGTEXT")
    private String photoBase64;
    
    @Column(columnDefinition = "LONGTEXT")
    private String qrCode;
}
