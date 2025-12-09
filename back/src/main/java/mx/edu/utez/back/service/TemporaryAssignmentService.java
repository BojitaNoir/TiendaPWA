package mx.edu.utez.back.service;

import mx.edu.utez.back.model.Store;
import mx.edu.utez.back.model.TemporaryAssignment;
import mx.edu.utez.back.repository.StoreRepository;
import mx.edu.utez.back.repository.TemporaryAssignmentRepository;
import mx.edu.utez.back.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TemporaryAssignmentService {
    private final TemporaryAssignmentRepository repo;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public TemporaryAssignmentService(TemporaryAssignmentRepository repo,
            StoreRepository storeRepository,
            UserRepository userRepository,
            NotificationService notificationService) {
        this.repo = repo;
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public TemporaryAssignment assign(String storeId, String repartidorId, String date) {
        try {
            Store store = storeRepository.findById(storeId);
            if (store == null)
                throw new RuntimeException("Store not found");
            if (userRepository.findById(repartidorId) == null)
                throw new RuntimeException("User not found");

            TemporaryAssignment ta = new TemporaryAssignment();
            ta.setId(UUID.randomUUID().toString());
            ta.setStoreId(storeId);
            ta.setRepartidorId(repartidorId);
            ta.setDate(date);

            repo.save(ta, ta.getId());

            // 🔔 Enviar notificación push al repartidor
            notificationService.notificarAsignacionTemporal(repartidorId, store.getName(), date);

            return ta;
        } catch (Exception e) {
            throw new RuntimeException("Error assigning temporary store", e);
        }
    }

    public List<TemporaryAssignment> findForRepartidorOnDate(String repartidorId, String date) {
        try {
            // Manual filter
            List<TemporaryAssignment> assignments = repo.findAll().stream()
                    .filter(ta -> repartidorId.equals(ta.getRepartidorId()) && date.equals(ta.getDate()))
                    .collect(Collectors.toList());

            // Populate Store object for each assignment
            for (TemporaryAssignment ta : assignments) {
                if (ta.getStoreId() != null) {
                    try {
                        ta.setStore(storeRepository.findById(ta.getStoreId()));
                    } catch (Exception e) {
                        // Store not found, leave null
                    }
                }
            }

            return assignments;
        } catch (Exception e) {
            throw new RuntimeException("Error finding assignments", e);
        }
    }
}
