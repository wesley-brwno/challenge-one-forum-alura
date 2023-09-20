package com.br.alura.forum.DTO.topico;

import com.br.alura.forum.DTO.curso.CursoDataOutput;
import com.br.alura.forum.DTO.resposta.RespostaOutputInTopico;
import com.br.alura.forum.constrains.StatusTopico;
import com.br.alura.forum.modelo.Topico;

import java.util.List;

public record TopicoComRespostas(
        Long id,
        String titulo,
        String mensagem,
        CursoDataOutput curso,
        StatusTopico status,
        List<RespostaOutputInTopico> respostas) {

    public TopicoComRespostas(Topico topico, List<RespostaOutputInTopico> respostas) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                new CursoDataOutput(topico.getCurso()),
                topico.getStatus(),
                respostas
        );
    }
}
