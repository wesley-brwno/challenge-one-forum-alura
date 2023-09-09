package com.br.alura.forum.controller;

import com.br.alura.forum.DTO.topico.CadastrarTopicoDados;
import com.br.alura.forum.DTO.topico.TopicoDetalhes;
import com.br.alura.forum.service.topico.CadastrarTopico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private CadastrarTopico cadastrarTopico;

    @PostMapping
    public ResponseEntity<TopicoDetalhes> cadastrar(@Valid @RequestBody CadastrarTopicoDados dados, UriComponentsBuilder uriBuilder) {
        TopicoDetalhes topicoDetalhes = cadastrarTopico.criarNovoTopio(dados);
        URI uri = uriBuilder.path("/topico/{id}").buildAndExpand(topicoDetalhes.id()).toUri();
        return ResponseEntity.created(uri).body(topicoDetalhes);
    }
}
