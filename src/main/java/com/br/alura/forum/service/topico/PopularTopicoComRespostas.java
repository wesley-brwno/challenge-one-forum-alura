package com.br.alura.forum.service.topico;

import com.br.alura.forum.DTO.resposta.RespostaOutputInTopico;
import com.br.alura.forum.DTO.topico.TopicoComRespostas;
import com.br.alura.forum.modelo.Resposta;
import com.br.alura.forum.modelo.Topico;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PopularTopicoComRespostas {

    public TopicoComRespostas obterDados(Topico topico) {
        List<RespostaOutputInTopico> respostas = new ArrayList<>();

        for (Resposta resposta : topico.getRespostas()) {
            respostas.add(new RespostaOutputInTopico(resposta));
        }

        return new TopicoComRespostas(topico, respostas);
    }
}
