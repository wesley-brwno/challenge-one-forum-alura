package com.br.alura.forum.DTO.topico;

import com.br.alura.forum.modelo.StatusTopico;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record TopicoDetalhes(
        Long id,
        String titulo,
        String messagem,
        @JsonFormat(pattern = "dd - MMMM - yyyy HH:mm")
        LocalDateTime data,
        StatusTopico statuts,
        String autor,
        String curso
) {
}
