package com.br.alura.forum.DTO.resposta;

import jakarta.validation.constraints.NotBlank;

public record RespostaUpdateData(
        @NotBlank
        String mensagem
) {
}
