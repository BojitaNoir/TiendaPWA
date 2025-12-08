package mx.edu.utez.back.controller;

import mx.edu.utez.back.model.Store;
import mx.edu.utez.back.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<Store> create(@RequestBody Store s) {
        return ResponseEntity.ok(storeService.create(s));
    }

    @PutMapping
    public ResponseEntity<Store> update(@RequestBody Store s) {
        return ResponseEntity.ok(storeService.update(s));
    }

    @GetMapping
    public ResponseEntity<List<Store>> list() {
        return ResponseEntity.ok(storeService.findAll());
    }

    @GetMapping("/by-code/{code}")
    public ResponseEntity<Store> byCode(@PathVariable String code) {
        return ResponseEntity.ok(storeService.findByCode(code));
    }

    // 🆕 NUEVO: Método para eliminar una tienda
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        storeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}