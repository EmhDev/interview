package com.bci.interview.Entity;
// src/main/java/com/example/usercreation/entity/Phone.java


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "phones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String number;
    private String citycode;
    private String countrycode;

    @ManyToOne(fetch = FetchType.LAZY) // Relacion Muchos a Uno con User
    @JoinColumn(name = "user_id", nullable = false) // Columna de union a la tabla de usuarios
    private UserEntity user; // Referencia al usuario al que pertenece este telefono
}


