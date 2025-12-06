package mx.edu.utez.back.service;

import mx.edu.utez.back.model.Store;
import mx.edu.utez.back.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar

import java.util.List;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional
    public Store create(Store s) {
        return storeRepository.save(s);
    }

    @Transactional
    public Store update(Store s) {
        return storeRepository.save(s);
    }

    @Transactional(readOnly = true)
    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Store findByCode(String code) {
        return storeRepository.findByCode(code).orElse(null);
    }

    // 🆕 NUEVO: Método para eliminar
    @Transactional
    public void delete(Long id) {
        storeRepository.deleteById(id);
    }
}