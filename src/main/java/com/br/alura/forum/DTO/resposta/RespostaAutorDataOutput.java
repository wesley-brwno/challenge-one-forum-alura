package com.br.alura.forum.DTO.resposta;

import java.time.LocalDateTime;

public record RespostaAutorDataOutput(
        Long topicoId,
        String autor,
        String mensagem,
        LocalDateTime dataCriacao,
        Boolean solucao
) {
    public RespostaAutorDataOutput(Long topicoId, String autor, String mensagem, LocalDateTime dataCriacao, Boolean solucao) {
        this.topicoId = topicoId;
        this.autor = autor;
        this.mensagem = mensagem;
        this.dataCriacao = dataCriacao;
        this.solucao = solucao;
    }
}
