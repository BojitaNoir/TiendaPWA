package mx.edu.utez.back.controller;

import mx.edu.utez.back.model.User;
import mx.edu.utez.back.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User u) {
        return ResponseEntity.ok(userService.create(u));
    }

    @GetMapping
    public ResponseEntity<List<User>> list() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/{userId}/assign-store/{storeId}")
    public ResponseEntity<User> assignStore(@PathVariable Long userId, @PathVariable Long storeId) {
        return ResponseEntity.ok(userService.assignStore(userId, storeId));
    }

    // 🆕 NUEVO: Método para eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}