package com.br.alura.forum.controller;

import com.br.alura.forum.DTO.topico.CadastrarTopicoDados;
import com.br.alura.forum.DTO.topico.TopicoDetalhes;
import com.br.alura.forum.repository.CursoRepository;
import com.br.alura.forum.repository.TopicoRepository;
import com.br.alura.forum.service.topico.CadastrarTopico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private CadastrarTopico cadastrarTopico;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<TopicoDetalhes> cadastrar(@Valid @RequestBody CadastrarTopicoDados dados, UriComponentsBuilder uriBuilder) {
        TopicoDetalhes topicoDetalhes = cadastrarTopico.criarNovoTopio(dados);
        URI uri = uriBuilder.path("/topico/{id}").buildAndExpand(topicoDetalhes.id()).toUri();
        return ResponseEntity.created(uri).body(topicoDetalhes);
    }

    @GetMapping
    public ResponseEntity<Page<TopicoDetalhes>> listar(@PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable pageable) {
        Page<TopicoDetalhes> topicos = topicoRepository.findAllByIdNotNull(pageable).map(TopicoDetalhes::new);
        return ResponseEntity.ok().body(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDetalhes> listarPorId(@PathVariable Long id) {
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(new TopicoDetalhes(topicoRepository.getReferenceById(id)));
    }

    @GetMapping("/cursos/{nome}")
    public ResponseEntity<Page<TopicoDetalhes>> listarPorCursoNome(@PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable pageable, @PathVariable String nome) {
        List<Long> cursoNomeIds = cursoRepository.findByNome(nome);
        List<TopicoDetalhes> topicoLista = new ArrayList<>();

        for (Long nomeId : cursoNomeIds) {
            Page<TopicoDetalhes> topico = topicoRepository.findAllByCurso_IdOrderByDataCriacao(pageable, nomeId).map(TopicoDetalhes::new);
            topicoLista.addAll(topico.getContent());
        }

        Page<TopicoDetalhes> topicos = new PageImpl<>(topicoLista, pageable, topicoLista.size());
        return ResponseEntity.ok().body(topicos);
    }

    @GetMapping("/ano/{ano}")
    public ResponseEntity<Page<TopicoDetalhes>> listarPorAno(@PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable pageable, @PathVariable Long ano) {
        Page<TopicoDetalhes> topicos = topicoRepository.findByAno(pageable, ano).map(TopicoDetalhes::new);
        return ResponseEntity.ok().body(topicos);
    }

    @PutMapping("{id}")
    public ResponseEntity<TopicoDetalhes> atualizarTopico(@Valid @RequestBody CadastrarTopicoDados dados, @PathVariable Long id) {
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(cadastrarTopico.atualizarTopico(dados, id));
    }
}
