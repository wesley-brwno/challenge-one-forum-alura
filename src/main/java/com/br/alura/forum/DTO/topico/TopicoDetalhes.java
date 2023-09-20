package com.br.alura.forum.DTO.topico;

import com.br.alura.forum.constrains.StatusTopico;
import com.br.alura.forum.modelo.Topico;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record TopicoDetalhes(
        Long id,
        String titulo,
        String messagem,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
        LocalDateTime data,
        StatusTopico statuts,
        String autor,
        String curso
) {
        public TopicoDetalhes (Topico topico){
                this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getStatus(), topico.getAutor().getNome(), topico.getCurso().getNome());
        }
}
