package com.br.alura.forum.DTO.curso;

import jakarta.validation.constraints.NotBlank;

public record CursoDataInput(
        @NotBlank
        String nome,
        @NotBlank
        String categoria
) {
}
