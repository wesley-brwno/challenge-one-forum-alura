package com.br.alura.forum.DTO.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioLogin(
        @Email
        String email,
        @NotBlank
        String senha
) {
}
