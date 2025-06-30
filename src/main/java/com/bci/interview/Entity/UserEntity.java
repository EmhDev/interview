package com.bci.interview.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {@Id
@GeneratedValue(strategy = GenerationType.UUID) // Generacion automatica de UUID para el ID
private UUID id;

    private String name;

    @Column(unique = true, nullable = false) // El correo debe ser unico y no nulo
    private String email;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default // Asegura que la lista se inicialice por defecto
    private List<PhoneEntity> phones = new ArrayList<>();

    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive; // Campo para indicar si el usuario esta activo

    /**
     * Método de utilidad para añadir un teléfono y asegurar la bidireccionalidad de la relación.
     * @param phone El objeto Phone a añadir.
     */
    public void addPhone(PhoneEntity phone) {
        phones.add(phone);
        phone.setUser(phone.getUser());
    }


    public void removePhone(PhoneEntity phone) {
        phones.remove(phone);
        phone.setUser(null);
    }
}


