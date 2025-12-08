package mx.edu.utez.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visit {
    private String id;
    private String checkInTime; // ISO String
    private Double latitude;
    private Double longitude;
    private boolean hadOrder;
    private boolean temporaryAssignment;
    private String repartidorId;
    private String storeId;

    public void setCheckInTimeNow() {
        this.checkInTime = LocalDateTime.now().toString();
    }
}
