package mx.edu.utez.back.service;

import mx.edu.utez.back.model.*;
import mx.edu.utez.back.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitService {
    private final VisitRepository visitRepo;
    private final StoreRepository storeRepo;
    private final UserRepository userRepo;
    private final NotificationService notificationService; // 🔔 1. Inyectamos notificaciones

    public VisitService(VisitRepository visitRepo, StoreRepository storeRepo, UserRepository userRepo, NotificationService notificationService) {
        this.visitRepo = visitRepo;
        this.storeRepo = storeRepo;
        this.userRepo = userRepo;
        this.notificationService = notificationService;
    }

    public List<Visit> findAll() { return visitRepo.findAll(); }

    public List<Visit> findByRepartidor(Long repartidorId) {
        return visitRepo.findByRepartidorId(repartidorId);
    }

    @Transactional
    public Visit registerScan(String storeCode, Long repartidorId, Double lat, Double lng, boolean hadOrder, boolean temporary) {
        // 1. Validar Tienda
        Store store = storeRepo.findByCode(storeCode)
                .orElseThrow(() -> new RuntimeException("Código de tienda inválido"));

        // 2. Validar Repartidor
        User repartidor = userRepo.findById(repartidorId)
                .orElseThrow(() -> new RuntimeException("Repartidor no encontrado"));

        // 3. Crear Visita
        Visit visit = new Visit();
        visit.setStore(store);
        visit.setRepartidor(repartidor);
        visit.setCheckInTime(LocalDateTime.now());
        visit.setLatitude(lat);
        visit.setLongitude(lng);
        visit.setHadOrder(hadOrder);
        visit.setTemporaryAssignment(temporary);

        Visit savedVisit = visitRepo.save(visit);

        // 🔔 4. ENVIAR NOTIFICACIÓN AL ADMINISTRADOR
        notificationService.registrarEvento(
                "Visita Registrada",
                "El repartidor " + repartidor.getName() + " ha llegado a " + store.getName(),
                "info"
        );

        return savedVisit;
    }
}