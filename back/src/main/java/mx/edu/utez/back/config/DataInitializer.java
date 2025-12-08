package mx.edu.utez.back.config;

import mx.edu.utez.back.model.*;
import mx.edu.utez.back.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.ArrayList;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            try {
                List<User> existingUsers = userRepo.findAll();
                if (existingUsers.isEmpty()) {
                    System.out.println("--- INICIANDO CARGA DE USUARIOS BASE (FIRESTORE) ---");

                    // 1. ADMIN
                    User admin = new User();
                    admin.setName("Admin Principal");
                    admin.setEmail("admin@example.com");
                    admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña segura
                    admin.setRole(Role.ADMIN);
                    userRepo.save(admin, null); // ID autogenerado

                    // 2. REPARTIDOR 1
                    User repartidor = new User();
                    repartidor.setName("Repartidor Centro");
                    repartidor.setEmail("rep1@example.com");
                    repartidor.setPassword(passwordEncoder.encode("rep123")); // Contraseña segura
                    repartidor.setRole(Role.REPARTIDOR);
                    userRepo.save(repartidor, null);

                    // 3. REPARTIDOR 2
                    User repartidor2 = new User();
                    repartidor2.setName("Repartidor Polanco");
                    repartidor2.setEmail("rep2@example.com");
                    repartidor2.setPassword(passwordEncoder.encode("rep123")); // Contraseña segura
                    repartidor2.setRole(Role.REPARTIDOR);
                    userRepo.save(repartidor2, null);

                    System.out.println("--- USUARIOS CREADOS EXITOSAMENTE ---");
                    System.out.println("Admin: admin@example.com / admin123");
                    System.out.println("Repartidor: rep1@example.com / rep123");
                } else {
                    System.out.println("--- Usuarios ya existen en BD, omitiendo carga inicial ---");
                }
            } catch (Exception e) {
                System.err.println("Error initializing data: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
