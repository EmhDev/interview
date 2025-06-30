package com.bci.interview.services.service;

import com.bci.interview.Entity.UserEntity;

public interface JwtService {
    /**
     * Genera un JWT para el usuario proporcionado.
     * @param user El {@link UserEntity} para el cual se generará el token.
     * @return Una cadena que representa el JWT generado.
     */
    String generateToken(UserEntity user);
}
