package mx.edu.utez.back.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 🚩 CAMPO AÑADIDO PARA LA ASIGNACIÓN PRINCIPAL (soluciona 'setStore')
    // Esto modela que un repartidor tiene una "Base" o Tienda principal
    @ManyToOne(fetch = FetchType.LAZY) // Usamos LAZY para no cargarla siempre
    @JoinColumn(name = "main_store_id")
    private Store store;


    @ManyToMany
    @JoinTable(name = "user_store",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "store_id"))
    private Set<Store> assignedStores = new HashSet<>();

    // Nota: Si usas @Data de Lombok, los getters y setters (getStore/setStore)
    // se generan automáticamente. ¡El error desaparecerá al guardar!
}