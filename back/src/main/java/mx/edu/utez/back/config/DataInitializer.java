package mx.edu.utez.back.config;

import mx.edu.utez.back.model.*;
import mx.edu.utez.back.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepo, StoreRepository storeRepo, ProductRepository productRepo) {
        return args -> {
            if (userRepo.count() == 0) {
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@example.com");
                admin.setPassword("admin");
                admin.setRole(Role.ADMIN);
                userRepo.save(admin);

                User repartidor = new User();
                repartidor.setName("Repartidor 1");
                repartidor.setEmail("rep1@example.com");
                repartidor.setPassword("rep");
                repartidor.setRole(Role.REPARTIDOR);
                userRepo.save(repartidor);

                Store s1 = new Store();
                s1.setName("Tienda A");
                s1.setCode("TIENDA-A-QR");
                s1.setLatitude(19.432608);
                s1.setLongitude(-99.133209);
                storeRepo.save(s1);

                Store s2 = new Store();
                s2.setName("Tienda B");
                s2.setCode("TIENDA-B-QR");
                s2.setLatitude(19.427);
                s2.setLongitude(-99.167);
                storeRepo.save(s2);

                Product p1 = new Product();
                p1.setName("Refresco 600ml");
                p1.setSku("REF600");
                p1.setPrice(12.5);
                productRepo.save(p1);

                Product p2 = new Product();
                p2.setName("Pan bolillo");
                p2.setSku("PAN-BOL");
                p2.setPrice(3.0);
                productRepo.save(p2);
            }
        };
    }
}
