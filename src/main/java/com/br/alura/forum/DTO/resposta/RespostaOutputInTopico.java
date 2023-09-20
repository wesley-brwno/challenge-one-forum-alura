package com.br.alura.forum.DTO.resposta;

import com.br.alura.forum.DTO.usuario.UsuarioDataOutputInResponses;
import com.br.alura.forum.modelo.Resposta;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDateTime;

public record RespostaOutputInTopico(
        Long id,
        String mensagem,
        @JsonAlias("data_criacao")
        LocalDateTime dataCriacao,
        UsuarioDataOutputInResponses autor,
        boolean solucao
) {
    public RespostaOutputInTopico(Resposta resposta) {
        this(resposta.getId(), resposta.getMensagem(), resposta.getDataCriacao(), new UsuarioDataOutputInResponses(resposta.getAutor()), resposta.getSolucao());
    }
}
