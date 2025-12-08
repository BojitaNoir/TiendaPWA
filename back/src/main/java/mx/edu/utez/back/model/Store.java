package mx.edu.utez.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    private String id;
    private String name;
    private String code; // QR code value (storeCode)
    private String city;
    private String address;
    private String phone;
    private Double latitude;
    private Double longitude;
    private String photoUrl;
}