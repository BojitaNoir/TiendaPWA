package mx.edu.utez.back.service;

import mx.edu.utez.back.model.Store;
import mx.edu.utez.back.model.User;
import mx.edu.utez.back.repository.StoreRepository;
import mx.edu.utez.back.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public UserService(UserRepository userRepository, StoreRepository storeRepository) {
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User assignStore(Long userId, Long storeId) {
        User u = userRepository.findById(userId).orElseThrow();
        Store s = storeRepository.findById(storeId).orElseThrow();
        u.getAssignedStores().add(s);
        return userRepository.save(u);
    }
}
