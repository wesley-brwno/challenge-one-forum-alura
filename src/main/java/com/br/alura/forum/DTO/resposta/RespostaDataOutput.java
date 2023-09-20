package com.br.alura.forum.DTO.resposta;

import com.br.alura.forum.DTO.topico.TopicoDetalhes;
import com.br.alura.forum.DTO.usuario.UsuarioDataOutputInResponses;
import com.br.alura.forum.modelo.Resposta;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embedded;

import java.time.LocalDateTime;

public record RespostaDataOutput(
        Long id,
        String mensagem,
        @Embedded
        TopicoDetalhes topico,
        @JsonProperty("data_criacao")
        LocalDateTime dataCriacao,
        UsuarioDataOutputInResponses autor,
        boolean solucao
) {
    public RespostaDataOutput(Resposta resposta) {
        this(resposta.getId(), resposta.getMensagem(), new TopicoDetalhes(resposta.getTopico()), resposta.getDataCriacao(), new UsuarioDataOutputInResponses(resposta.getAutor()), resposta.getSolucao());
    }
}
