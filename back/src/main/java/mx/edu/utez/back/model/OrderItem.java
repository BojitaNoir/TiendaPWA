package mx.edu.utez.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private String id;
    private Integer quantity;
    private Double price;

    // ID for Firestore storage
    private String productId;

    // Full object for JSON responses (Firestore will ignore if null)
    private Product product;
}
