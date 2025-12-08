package mx.edu.utez.back.service;

import mx.edu.utez.back.model.Store;
import mx.edu.utez.back.model.User;
import mx.edu.utez.back.model.Visit;
import mx.edu.utez.back.repository.StoreRepository;
import mx.edu.utez.back.repository.UserRepository;
import mx.edu.utez.back.repository.VisitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VisitService {
    private final VisitRepository visitRepo;
    private final StoreRepository storeRepo;
    private final UserRepository userRepo;
    private final NotificationService notificationService;

    public VisitService(VisitRepository visitRepo, StoreRepository storeRepo, UserRepository userRepo,
            NotificationService notificationService) {
        this.visitRepo = visitRepo;
        this.storeRepo = storeRepo;
        this.userRepo = userRepo;
        this.notificationService = notificationService;
    }

    public List<Visit> findAll() {
        try {
            return visitRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error finding visits", e);
        }
    }

    public List<Visit> findByRepartidor(String repartidorId) {
        try {
            // Manual filtering
            List<Visit> all = visitRepo.findAll();
            return all.stream()
                    .filter(v -> repartidorId.equals(v.getRepartidorId()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding visits for repartidor", e);
        }
    }

    public Visit registerScan(String storeCode, String repartidorId, Double lat, Double lng, boolean hadOrder,
            boolean temporary) {
        try {
            // 1. Validar Tienda
            // We need to find by code. Scan all stores efficiently?
            // For now, scan all.
            Store store = storeRepo.findAll().stream()
                    .filter(s -> storeCode.equals(s.getCode()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Código de tienda inválido"));

            // 2. Validar Repartidor
            User repartidor = userRepo.findById(repartidorId);
            if (repartidor == null) {
                throw new RuntimeException("Repartidor no encontrado");
            }

            // 3. Crear Visita
            Visit visit = new Visit();
            visit.setId(UUID.randomUUID().toString());
            visit.setStoreId(store.getId());
            visit.setRepartidorId(repartidor.getId());
            visit.setCheckInTimeNow();
            visit.setLatitude(lat);
            visit.setLongitude(lng);
            visit.setHadOrder(hadOrder);
            visit.setTemporaryAssignment(temporary);

            visitRepo.save(visit, visit.getId());

            // 🔔 4. ENVIAR NOTIFICACIÓN AL ADMINISTRADOR
            notificationService.registrarEvento(
                    "Visita Registrada",
                    "El repartidor " + repartidor.getName() + " ha llegado a " + store.getName(),
                    "info");

            return visit;
        } catch (Exception e) {
            throw new RuntimeException("Error registering scan", e);
        }
    }
}