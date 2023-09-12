package com.br.alura.forum.DTO.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioDataInput(
        @NotBlank
        @Size(min = 3, max = 150)
        String nome,
        @Email
        String email,
        @NotBlank
        @Size(min = 6)
        String senha
) {
}
