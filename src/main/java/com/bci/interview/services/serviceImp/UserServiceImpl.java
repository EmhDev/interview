package com.bci.interview.services.serviceImp;

import com.bci.interview.Entity.UserEntity;
import com.bci.interview.dto.PhoneDTO;
import com.bci.interview.dto.UserRequestDTO;
import com.bci.interview.dto.UserResponseDTO;
import com.bci.interview.exceptions.InvalidEmailFormatException;
import com.bci.interview.exceptions.InvalidPasswordFormatException;
import com.bci.interview.exceptions.UserAlreadyExistsException;
import com.bci.interview.repository.UserRepository;
import com.bci.interview.services.service.JwtService;
import com.bci.interview.services.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    // Expresion regular para validar el formato del correo (aaaaaaa@dominio.cl)
    // Se ha generalizado un poco para ser mas robusta, pero se adhiere a la estructura basica.
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

//expresion regular
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=(?:.*\\d){2})[A-Za-z\\d@$!%*?&]{6,}$";


    /**
     * Registra un nuevo usuario en el sistema, realizando validaciones y generando un token.
     *
     * @param request El objeto {@link UserRequestDTO} con los datos del nuevo usuario
     * @return Un objeto {@link UserResponseDTO} con los detalles del usuario registrado
     * @throws UserAlreadyExistsException     Si EMAIL ya esta registrado
     * @throws InvalidEmailFormatException    Si el EMAIL es invalido
     * @throws InvalidPasswordFormatException Si el formato de la contraseña es invalido
     */
    @Override
    public UserResponseDTO registerUser(UserRequestDTO request) {
        // 1. Validar formato de correo
        if (!isValidEmail(request.getEmail())) {
            throw new InvalidEmailFormatException("El correo no tiene un formato válido (ej. aaaaaaa@dominio.cl)");
        }

        // 2. Validar formato de contraseña
        if (!isValidPassword(request.getPassword())) {
            throw new InvalidPasswordFormatException("La contraseña debe contener al menos una mayúscula, letras minúsculas y dos números.");
        }

        // 3. Verificar si el correo ya está registrado
        Optional<UserEntity> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("El correo ya registrado");
        }

        // 4. Mapear DTO a Entidad de usuario
        UserEntity newUser = mapToUserEntity(request);

        // 5. Generar token JWT
        String token = jwtService.generateToken(newUser);
        newUser.setToken(token); // Asignar el token al usuario

        // 6. Establecer fechas y estado inicial
        LocalDateTime now = LocalDateTime.now();
        newUser.setId(UUID.randomUUID()); // Generar UUID para el nuevo usuario
        newUser.setCreated(now);
        newUser.setModified(now);
        newUser.setLastLogin(now);
        newUser.setActive(true);

        // 7. Persistir el usuario en la base de datos
        UserEntity savedUser = userRepository.save(newUser);

        // 8. Mapear la Entidad guardada a DTO de respuesta y retornarla
        return mapToUserResponse(savedUser);
    }

    /**
     * Valida el formato del correo electrónico usando una expresion regular.
     *
     * @param email El correo electronico a validar.
     * @return true si el formato es valido, false en caso contrario.
     */
    private boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    /**
     * formato de la contraseña usando una expresion regular.
     *
     * @param password La contraseña a validar.
     * @return true si el formato es valido, false en caso contrario.
     */
    private boolean isValidPassword(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    /**
     * Mapea un objeto {@link UserRequestDTO} a una entidad {@link UserEntity}.
     *
     * @param request El objeto de solicitud.
     * @return La entidad {@link UserEntity} mapeada.
     */
    private UserEntity mapToUserEntity(UserRequestDTO request) {
        UserEntity userEntity = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();


        List<PhoneDTO> phones = request.getPhones().stream()
                .map(p -> PhoneDTO.builder()
                        .number(p.getNumber())
                        .citycode(p.getCitycode())
                        .countrycode(p.getCountrycode())
                        .user(userEntity) // Establecer la relacion bidireccional
                        .build())
                .collect(Collectors.toList());
        userEntity.setPhones(phones);
        return userEntity;
    }

    /**
     * Mapea una entidad {@link UserEntity} a un objeto {@link UserResponseDTO}
     *
     * @param userEntity La entidad de usuario
     * @return El objeto {@link UserResponseDTO} mapeado
     */
    private UserResponseDTO mapToUserResponse(UserEntity userEntity) {
        return UserResponseDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .created(userEntity.getCreated())
                .modified(userEntity.getModified())
                .lastLogin(userEntity.getLastLogin())
                .token(userEntity.getToken())
                .isActive(userEntity.isActive())
                .build();
    }
}