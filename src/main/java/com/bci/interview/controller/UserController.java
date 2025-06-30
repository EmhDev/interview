package com.bci.interview.controller;

import com.bci.interview.dto.UserRequestDTO;
import com.bci.interview.dto.UserResponseDTO;
import com.bci.interview.services.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

/**
 * Endpoint para registrar un nuevo usuario.
 * Acepta una solicitud JSON con los datos del usuario y retorna un UserResponse.
 * @param request Los datos del usuario para el registro.
 * @return ResponseEntity con el UserResponse y el status HTTP 201 CREATED.
 */
@PostMapping("/register")
public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO request) {

    // Llama al servicio para registrar al usuario
    UserResponseDTO userResponse = userService.registerUser(request);
    // Retorna la respuesta con el status 201 Created
    return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
}



