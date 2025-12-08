package mx.edu.utez.back.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String id;
    private String name;
    private String sku;
    private Double price;
    private Integer stock = 0;
    private String description;
    private Boolean available = true;
    private String photoBase64;
    private String qrCode;
}
