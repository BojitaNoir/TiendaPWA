package mx.edu.utez.back.service;

import mx.edu.utez.back.model.Store;
import mx.edu.utez.back.model.User;
import mx.edu.utez.back.repository.StoreRepository;
import mx.edu.utez.back.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder; // 🟢 Importación necesaria

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder; // 🟢 Añadir el campo para el encoder

    // 🟢 Constructor Modificado: Ahora inyecta PasswordEncoder
    public UserService(UserRepository userRepository, StoreRepository storeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.passwordEncoder = passwordEncoder; // Asignación del encoder
    }

    @Transactional
    public User create(User u) {
        // 🟢 CORRECCIÓN: Hashear la contraseña antes de guardarla
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return userRepository.save(u);
    }

    @Transactional(readOnly = true) // Solo lectura
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    // Implementación del método assignStore (sin cambios, ya que estaba bien)
    @Transactional
    public User assignStore(Long userId, Long storeId) {
        // 1. Encontrar el usuario o lanzar excepción si no existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario (Repartidor) no encontrado con ID: " + userId));

        // 2. Encontrar la tienda o lanzar excepción si no existe
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada con ID: " + storeId));

        // 3. Asignar la tienda al usuario (Repartidor)
        user.setStore(store);

        // 4. Guardar los cambios
        return userRepository.save(user);
    }

    // 🔑 Método de Autenticación
    @Transactional(readOnly = true)
    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);

        // 🟢 CORRECCIÓN: Usar passwordEncoder.matches() para comparar la contraseña plana
        // con el hash almacenado.
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}