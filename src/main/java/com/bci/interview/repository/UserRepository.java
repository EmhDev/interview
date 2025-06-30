package com.bci.interview.repository;

import com.bci.interview.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico del usuario a buscar.
     * @return Un {@link Optional} que contiene la {@link UserEntity} si se encuentra, o vacío si no.
     */
    Optional<UserEntity> findByEmail(String email);
}
