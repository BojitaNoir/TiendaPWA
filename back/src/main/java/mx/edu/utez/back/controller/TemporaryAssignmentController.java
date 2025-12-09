package mx.edu.utez.back.controller;

import mx.edu.utez.back.model.TemporaryAssignment;
import mx.edu.utez.back.service.TemporaryAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/temporary-assignments")
@CrossOrigin(origins = "*")
public class TemporaryAssignmentController {
    private final TemporaryAssignmentService service;

    public TemporaryAssignmentController(TemporaryAssignmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TemporaryAssignment> assign(@RequestParam String storeId,
            @RequestParam String repartidorId,
            @RequestParam String date) {
        return ResponseEntity.ok(service.assign(storeId, repartidorId, date));
    }

    /**
     * Obtener asignaciones temporales para un repartidor en una fecha específica
     * GET /api/temporary-assignments/repartidor/{repartidorId}/date/{date}
     */
    @GetMapping("/repartidor/{repartidorId}/date/{date}")
    public ResponseEntity<List<TemporaryAssignment>> getByRepartidorAndDate(
            @PathVariable String repartidorId,
            @PathVariable String date) {
        List<TemporaryAssignment> assignments = service.findForRepartidorOnDate(repartidorId, date);
        return ResponseEntity.ok(assignments);
    }
}
