package com.br.alura.forum.service.topico;

import com.br.alura.forum.DTO.topico.CadastrarTopicoDados;
import com.br.alura.forum.validations.topico.ValidarDadosTopico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidarTopico {

    @Autowired
    private List<ValidarDadosTopico> validacoes;
    public void aplicarValidacoes(CadastrarTopicoDados dados) {
        validacoes.forEach(v -> v.validar(dados));
    }
}
