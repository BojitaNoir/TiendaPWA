package mx.edu.utez.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryAssignment {
    private String id;
    private String storeId;
    private String repartidorId;
    private String date; // ISO String for LocalDate
}
