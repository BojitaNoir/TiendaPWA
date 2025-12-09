package mx.edu.utez.back.service;

import mx.edu.utez.back.model.User;
import mx.edu.utez.back.model.Store;
import mx.edu.utez.back.repository.UserRepository;
import mx.edu.utez.back.repository.StoreRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, StoreRepository storeRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User user) {
        try {
            if (user.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            String id = userRepository.save(user, user.getId());
            user.setId(id);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Error creating user", e);
        }
    }

    public List<User> findAll() {
        try {
            List<User> users = userRepository.findAll();

            // Populate store for each user
            for (User user : users) {
                if (user.getMainStoreId() != null) {
                    try {
                        Store store = storeRepository.findById(user.getMainStoreId());
                        user.setStore(store);
                    } catch (Exception e) {
                        // Store not found, leave null
                    }
                }
            }

            return users;
        } catch (Exception e) {
            throw new RuntimeException("Error finding users", e);
        }
    }

    public void delete(String id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }

    public Optional<User> findById(String id) {
        try {
            User user = userRepository.findById(id);
            if (user == null) {
                return Optional.empty();
            }
            
            // Populate store if user has mainStoreId
            if (user.getMainStoreId() != null) {
                try {
                    Store store = storeRepository.findById(user.getMainStoreId());
                    user.setStore(store);
                } catch (Exception e) {
                    // Store not found, leave null
                }
            }
            
            return Optional.of(user);
        } catch (Exception e) {
            throw new RuntimeException("Error finding user", e);
        }
    }

    // Método para asignar tienda (cambiado a usar IDs)
    public User assignStore(String userId, String storeId) {
        try {
            User user = userRepository.findById(userId);
            if (user == null) {
                throw new RuntimeException("User not found");
            }
            // Verificar que la tienda existe (opcional, pero buena práctica)
            if (storeRepository.findById(storeId) == null) {
                throw new RuntimeException("Store not found");
            }

            user.setMainStoreId(storeId);
            return userRepository.save(user, userId) != null ? user : null;
        } catch (Exception e) {
            throw new RuntimeException("Error assigning store to user", e);
        }
    }

    public User authenticate(String email, String password) {
        try {
            List<User> users = userRepository.findAll(); // Optimization needed later
            return users.stream()
                    .filter(u -> u.getEmail().equals(email) && passwordEncoder.matches(password, u.getPassword()))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error during authentication", e);
        }
    }

    public void saveFcmToken(String userId, String token) {
        try {
            User user = userRepository.findById(userId);
            if (user != null) {
                user.setFcmToken(token);
                userRepository.save(user, userId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving FCM token", e);
        }
    }
}