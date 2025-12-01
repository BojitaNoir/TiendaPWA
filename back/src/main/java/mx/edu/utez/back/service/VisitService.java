package mx.edu.utez.back.service;

import mx.edu.utez.back.model.Store;
import mx.edu.utez.back.model.User;
import mx.edu.utez.back.model.Visit;
import mx.edu.utez.back.repository.StoreRepository;
import mx.edu.utez.back.repository.UserRepository;
import mx.edu.utez.back.repository.VisitRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class VisitService {
    private final VisitRepository visitRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public VisitService(VisitRepository visitRepository, StoreRepository storeRepository, UserRepository userRepository) {
        this.visitRepository = visitRepository;
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
    }

    public Visit registerScan(String storeCode, Long repartidorId, Double lat, Double lng, boolean hadOrder, boolean temporary) {
        Store store = storeRepository.findByCode(storeCode).orElseThrow();
        User user = userRepository.findById(repartidorId).orElseThrow();
        Visit v = new Visit();
        v.setStore(store);
        v.setRepartidor(user);
        v.setTimestamp(Instant.now());
        v.setLatitude(lat);
        v.setLongitude(lng);
        v.setHadOrder(hadOrder);
        v.setTemporary(temporary);
        return visitRepository.save(v);
    }

    public List<Visit> findByRepartidor(Long repartidorId) {
        return visitRepository.findByRepartidorId(repartidorId);
    }
}
