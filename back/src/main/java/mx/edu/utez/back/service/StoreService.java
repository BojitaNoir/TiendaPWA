package mx.edu.utez.back.service;

import mx.edu.utez.back.model.Store;
import mx.edu.utez.back.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store create(Store s) {
        return storeRepository.save(s);
    }

    public Store update(Store s) {
        return storeRepository.save(s);
    }

    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    public Store findByCode(String code) {
        return storeRepository.findByCode(code).orElse(null);
    }
}
