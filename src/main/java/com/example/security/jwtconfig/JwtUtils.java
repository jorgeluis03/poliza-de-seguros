package com.example.security.jwtconfig;
import com.example.security.userservice.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils { // 1: CLASE PARA CREAR, VALIDAR Y OBTENER INFORMACION DE TOKENS JWT

    //Logger para registrar mensajes y errores relacionados con jwt
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret; //Clave secreta para firmar los tokens

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs; // Tiempo de expiracion de los tokens

    //metodo para generar un token basada en la autenticacion del usuario
    public String generateJwtToken(Authentication authentication) {
        //Obtiene los detalles de usuario autenticado y lo pone del tipo objeto UserDetailsImpl
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        //Contruye el token usando en username, la fecha actual y la fecha de expiracion
        return Jwts.builder()
                .subject((userPrincipal.getUsername())) //El "subject" del token será el nombre de usuario
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey()) //Firma el token usando la llave secreta
                .compact();

    }

    //Metodo privado para obtener la clave de la firma desde el valor codificado en Base64
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        // Crear una clave HMAC con el algoritmo SHA
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Metodo para obtener el username (Subject) a partir de un token jwt
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser() // Crear un parser para analizar el token
                .verifyWith(getSigningKey()) // Verificar el token con la clave secreta
                .build()
                .parseSignedClaims(token) // Parsear y obtener los claims firmados del token
                .getPayload() // Obtener la carga útil (payload)
                .getSubject(); // Devolver el "subject" del token, que es el nombre de usuario
    }

    //Metodo para validar si un token JWT es correcto
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser() // Crear un parser para analizar el token
                    .verifyWith(getSigningKey()) // Verificar el token con la clave secreta
                    .build()
                    .parseSignedClaims(authToken); //Parsear y obtener los claims firmados del token
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
