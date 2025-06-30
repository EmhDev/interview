package com.bci.interview.services.serviceImp;

import com.bci.interview.services.service.JwtService;
import lombok.Value;
import org.springframework.stereotype.Service;

@Service
public class  JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration.ms}")
    private long EXPIRATION_TIME_MS;

    /**
     * Genera un JWT para el usuario especificado.
     * Los claims del token incluirán el ID del usuario, email y nombre.
     *
     * @param user El {@link UserEntity} para el cual se generará el token.
     * @return El token JWT generado como String.
     */
    @Override
    public String generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId().toString());
        claims.put("email", user.getEmail());
        claims.put("name", user.getName());
        return createToken(claims, user.getId().toString()); // El ID del usuario como sujeto del token
    }

    /**
     * Crea un token JWT a partir de los claims y el sujeto proporcionados.
     * Establece la fecha de emisión y la fecha de expiración del token.
     *
     * @param claims Un mapa de claims personalizados a incluir en el token.
     * @param subject El sujeto del token (típicamente el identificador del usuario).
     * @return El token JWT como String.
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Establece los claims
                .setSubject(subject) // Establece el sujeto
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS)) // Fecha de expiración
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // Firma el token con la clave secreta y algoritmo HS256
                .compact(); // Construye el token
    }

    /**
     * Obtiene la clave de firma decodificada de la clave secreta base64.
     *
     * @return La clave {@link Key} utilizada para firmar el token.
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
