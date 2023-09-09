package com.br.alura.forum.validations.topico;

import com.br.alura.forum.DTO.topico.CadastrarTopicoDados;
import com.br.alura.forum.repository.TopicoRepository;
import com.br.alura.forum.validations.ValidationsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicoEstaDuplicado implements ValidarDadosTopico{
    @Autowired
    private TopicoRepository repository;
    @Override
    public void validar(CadastrarTopicoDados dados) {
        if (repository.countByTitulo(dados.titulo()) > 0) {
            if (repository.countByMensagem(dados.mensagem()) > 0) {
                throw new ValidationsException("Tópico já existe");
            }
        }
    }
}
