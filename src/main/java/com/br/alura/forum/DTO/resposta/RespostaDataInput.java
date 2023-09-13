package com.br.alura.forum.DTO.resposta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RespostaDataInput(
        @NotBlank
        String mensagem,
        @NotNull
        @JsonAlias("topico_id")
        Long topicoId,
        @NotNull
        @JsonAlias("autor_id")
        Long autorId
) {
}
