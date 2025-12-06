package mx.edu.utez.back.config;

import mx.edu.utez.back.model.*;
import mx.edu.utez.back.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            // Solo se ejecuta si no hay usuarios en la base de datos
            if (userRepo.count() == 0) {
                System.out.println("--- INICIANDO CARGA DE USUARIOS BASE ---");

                // 1. ADMIN
                User admin = new User();
                admin.setName("Admin Principal");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña segura
                admin.setRole(Role.ADMIN);
                userRepo.save(admin);

                // 2. REPARTIDOR 1
                User repartidor = new User();
                repartidor.setName("Repartidor Centro");
                repartidor.setEmail("rep1@example.com");
                repartidor.setPassword(passwordEncoder.encode("rep123")); // Contraseña segura
                repartidor.setRole(Role.REPARTIDOR);
                userRepo.save(repartidor);

                // 3. REPARTIDOR 2
                User repartidor2 = new User();
                repartidor2.setName("Repartidor Polanco");
                repartidor2.setEmail("rep2@example.com");
                repartidor2.setPassword(passwordEncoder.encode("rep123")); // Contraseña segura
                repartidor2.setRole(Role.REPARTIDOR);
                userRepo.save(repartidor2);

                System.out.println("--- USUARIOS CREADOS EXITOSAMENTE ---");
                System.out.println("Admin: admin@example.com / admin123");
                System.out.println("Repartidor: rep1@example.com / rep123");
            } else {
                System.out.println("--- Usuarios ya existen en BD, omitiendo carga inicial ---");
            }
        };
    }
}