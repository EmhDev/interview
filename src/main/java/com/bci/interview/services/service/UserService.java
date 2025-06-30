package com.bci.interview.services.service;

import com.bci.interview.dto.UserRequestDTO;
import com.bci.interview.dto.UserResponseDTO;

public interface UserService {
    /**
     * Registra un nuevo usuario en el sistema.
     * @param request El objeto {@link UserRequestDTO} que contiene los datos para el registro del usuario.
     * @return Un objeto {@link UserResponseDTO} con los datos del usuario registrado.
     */
    UserResponseDTO registerUser(UserRequestDTO request);
}
