package mx.edu.utez.back.controller;

import mx.edu.utez.back.model.TemporaryAssignment;
import mx.edu.utez.back.service.TemporaryAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/temporary-assignments")
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
}
