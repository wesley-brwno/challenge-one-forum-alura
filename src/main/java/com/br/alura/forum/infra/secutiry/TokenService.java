package com.br.alura.forum.infra.secutiry;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.br.alura.forum.modelo.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.securty.secret}")
    private String secret;
    public String generateTokenJWT(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Forum Alura")
                    .withSubject(usuario.getEmail())
                    .withClaim("nome", usuario.getNome())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerar token JWT", exception);
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusMinutes(3).toInstant(ZoneOffset.of("-03:00"));
    }


}
