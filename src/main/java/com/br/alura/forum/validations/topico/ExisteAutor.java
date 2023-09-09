package com.br.alura.forum.validations.topico;

import com.br.alura.forum.DTO.topico.CadastrarTopicoDados;
import com.br.alura.forum.repository.UsuarioRespository;
import com.br.alura.forum.validations.ValidationsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExisteAutor implements ValidarDadosTopico{
    @Autowired
    private UsuarioRespository respository;
    @Override
    public void validar(CadastrarTopicoDados dados) {
        if (!respository.existsById(dados.autorId())) {
            throw new ValidationsException("Usuário não existe");
        }
    }
}
