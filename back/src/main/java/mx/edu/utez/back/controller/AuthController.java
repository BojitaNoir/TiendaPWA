package mx.edu.utez.back.controller;

import mx.edu.utez.back.dto.AuthRequest;
import mx.edu.utez.back.dto.AuthResponse;
import mx.edu.utez.back.model.Role;
import mx.edu.utez.back.model.User;
import mx.edu.utez.back.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            User user = userService.authenticate(request.getEmail(), request.getPassword());
            if (user != null) {
                AuthResponse response = new AuthResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole().toString(),
                    "Login exitoso"
                );
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(401).body(new AuthResponse(null, null, null, null, "Email o contraseña incorrectos"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new AuthResponse(null, null, null, null, e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        try {
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setRole(Role.valueOf(request.getRole() != null ? request.getRole() : "REPARTIDOR"));
            
            User createdUser = userService.create(user);
            AuthResponse response = new AuthResponse(
                createdUser.getId(),
                createdUser.getName(),
                createdUser.getEmail(),
                createdUser.getRole().toString(),
                "Usuario registrado exitosamente"
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new AuthResponse(null, null, null, null, "Error: " + e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(new AuthResponse(null, null, null, null, "Logout exitoso"));
    }
}
