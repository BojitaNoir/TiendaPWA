package mx.edu.utez.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private String id;
    private Integer quantity;
    private Double price;
    private String productId;

    // Transient field for API responses (not stored in Firestore)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private transient Product product;
}
