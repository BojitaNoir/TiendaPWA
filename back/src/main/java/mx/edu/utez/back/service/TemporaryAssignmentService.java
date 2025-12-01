package mx.edu.utez.back.service;

import mx.edu.utez.back.model.TemporaryAssignment;
import mx.edu.utez.back.model.Store;
import mx.edu.utez.back.model.User;
import mx.edu.utez.back.repository.TemporaryAssignmentRepository;
import mx.edu.utez.back.repository.StoreRepository;
import mx.edu.utez.back.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TemporaryAssignmentService {
    private final TemporaryAssignmentRepository repo;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public TemporaryAssignmentService(TemporaryAssignmentRepository repo, StoreRepository storeRepository, UserRepository userRepository) {
        this.repo = repo;
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
    }

    public TemporaryAssignment assign(Long storeId, Long repartidorId, LocalDate date) {
        Store s = storeRepository.findById(storeId).orElseThrow();
        User u = userRepository.findById(repartidorId).orElseThrow();
        TemporaryAssignment ta = new TemporaryAssignment();
        ta.setStore(s);
        ta.setRepartidor(u);
        ta.setDate(date);
        return repo.save(ta);
    }

    public List<TemporaryAssignment> findForRepartidorOnDate(Long repartidorId, LocalDate date) {
        return repo.findByRepartidorIdAndDate(repartidorId, date);
    }
}
