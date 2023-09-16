package com.br.alura.forum.service.usuario;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UsuarioPermissao {
    public boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean isCriadorDoTopico(Authentication authentication, String email) {
        return authentication.getName().equals(email);
    }
}
