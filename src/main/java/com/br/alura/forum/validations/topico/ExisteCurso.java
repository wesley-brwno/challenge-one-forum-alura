package com.br.alura.forum.validations.topico;

import com.br.alura.forum.DTO.topico.CadastrarTopicoDados;
import com.br.alura.forum.repository.CursoRepository;
import com.br.alura.forum.validations.ValidationsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExisteCurso implements ValidarDadosTopico{
    @Autowired
    private CursoRepository repository;
    @Override
    public void validar(CadastrarTopicoDados dados) {
        if (!repository.existsById(dados.cursoId())) {
            throw new ValidationsException("Curso não existe");
        }
    }
}
