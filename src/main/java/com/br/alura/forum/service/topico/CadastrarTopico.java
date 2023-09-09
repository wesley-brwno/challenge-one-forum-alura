package com.br.alura.forum.service.topico;

import com.br.alura.forum.DTO.topico.CadastrarTopicoDados;
import com.br.alura.forum.DTO.topico.TopicoDetalhes;
import com.br.alura.forum.modelo.Curso;
import com.br.alura.forum.modelo.Topico;
import com.br.alura.forum.modelo.Usuario;
import com.br.alura.forum.repository.CursoRepository;
import com.br.alura.forum.repository.TopicoRepository;
import com.br.alura.forum.repository.UsuarioRespository;
import com.br.alura.forum.validations.topico.ValidarDadosTopico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastrarTopico {

    @Autowired
    private UsuarioRespository usuarioRespository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private List<ValidarDadosTopico> validacoes;

    @Transactional
    public TopicoDetalhes criarNovoTopio(CadastrarTopicoDados dados) {

        validacoes.forEach(v -> v.validar(dados));

        Usuario usuario = usuarioRespository.findById(dados.autorId()).get();
        Curso curso = cursoRepository.findById(dados.cursoId()).get();

        Topico topico = new Topico(dados, usuario, curso);

        topicoRepository.save(topico);

        return new TopicoDetalhes(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getStatus(), topico.getAutor().getNome(), topico.getCurso().getNome());
    }
}
