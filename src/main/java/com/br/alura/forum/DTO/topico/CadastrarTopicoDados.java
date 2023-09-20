package com.br.alura.forum.DTO.topico;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastrarTopicoDados(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotNull
        @JsonAlias("curso_id")
        Long cursoId
) {
}