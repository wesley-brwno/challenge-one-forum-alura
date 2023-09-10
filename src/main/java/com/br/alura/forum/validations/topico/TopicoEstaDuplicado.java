package com.br.alura.forum.validations.topico;

import com.br.alura.forum.DTO.topico.CadastrarTopicoDados;
import com.br.alura.forum.modelo.Topico;
import com.br.alura.forum.repository.TopicoRepository;
import com.br.alura.forum.validations.ValidationsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TopicoEstaDuplicado implements ValidarDadosTopico{
    @Autowired
    private TopicoRepository repository;
    @Override
    public void validar(CadastrarTopicoDados dados) {
        if (repository.countByTitulo(dados.titulo()) > 0 && repository.countByMensagem(dados.mensagem()) > 0) {
            if (!isAutorOrCursoEdited(dados)) {
                throw new ValidationsException("Tópico já existe");
            }
        }
    }

    public boolean isAutorOrCursoEdited(CadastrarTopicoDados dados) {
        Topico topico = repository.findByTituloAndMensagem(dados.titulo(), dados.mensagem());
        return !Objects.equals(topico.getAutor().getId(), dados.autorId()) || !Objects.equals(topico.getCurso().getId(), dados.cursoId());
    }
}
