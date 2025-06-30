package com.bci.interview.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo es invalido") // Validacion
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotEmpty(message = "La lista de telefonos no puede estar vacia")
    @Valid // Habilita la validacion de los objetos PhoneDTO dentro de la lista
    private List<PhoneDTO> phones;
}
