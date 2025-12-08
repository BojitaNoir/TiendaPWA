package mx.edu.utez.back.service;

import mx.edu.utez.back.model.Store;
import mx.edu.utez.back.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> findAll() {
        try {
            return storeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error finding stores", e);
        }
    }

    public Optional<Store> findById(String id) {
        try {
            return Optional.ofNullable(storeRepository.findById(id));
        } catch (Exception e) {
            throw new RuntimeException("Error finding store", e);
        }
    }

    public Store findByCode(String code) {
        try {
            // Manual scan (inefficient but works for small datasets)
            return findAll().stream()
                    .filter(s -> code.equals(s.getCode()))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error finding store by code", e);
        }
    }

    public Store create(Store s) {
        if (s.getId() == null) {
            s.setId(java.util.UUID.randomUUID().toString());
        }
        return save(s);
    }

    public Store update(Store s) {
        return save(s);
    }

    public Store save(Store store) {
        try {
            String id = storeRepository.save(store, store.getId());
            store.setId(id);
            return store;
        } catch (Exception e) {
            throw new RuntimeException("Error saving store", e);
        }
    }

    public void delete(String id) {
        try {
            storeRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting store", e);
        }
    }
}