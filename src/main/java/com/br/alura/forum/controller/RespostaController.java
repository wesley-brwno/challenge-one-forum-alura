package com.br.alura.forum.controller;

import com.br.alura.forum.DTO.autenticacao.ErrorResponse;
import com.br.alura.forum.DTO.resposta.RespostaDataInput;
import com.br.alura.forum.DTO.resposta.RespostaDataOutput;
import com.br.alura.forum.DTO.resposta.RespostaUpdateData;
import com.br.alura.forum.DTO.resposta.RespostaUsuarioData;
import com.br.alura.forum.modelo.Resposta;
import com.br.alura.forum.modelo.Topico;
import com.br.alura.forum.modelo.Usuario;
import com.br.alura.forum.repository.RespostaRepository;
import com.br.alura.forum.repository.TopicoRepository;
import com.br.alura.forum.repository.UsuarioRespository;
import com.br.alura.forum.service.reposta.ConverteObjectsParaRespostaUsuarioData;
import com.br.alura.forum.service.usuario.UsuarioPermissao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/respostas")
public class RespostaController {
    @Autowired
    private RespostaRepository respostaRepository;
    @Autowired
    private UsuarioRespository usuarioRespository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private ConverteObjectsParaRespostaUsuarioData converteObjcts;
    @Autowired
    private UsuarioPermissao usuarioPermissao;

    @PostMapping
    @Transactional
    public ResponseEntity<RespostaDataOutput> cadastarResposta(@Valid @RequestBody RespostaDataInput dataInput, UriComponentsBuilder uriBuilder, Authentication authentication) {
        if (topicoRepository.existsById(dataInput.topicoId())) {
            Usuario usuario = (Usuario) usuarioRespository.findByEmail(authentication.getName());
            Topico topico = topicoRepository.findById(dataInput.topicoId()).get();
            Resposta resposta = new Resposta(dataInput, usuario, topico);
            respostaRepository.save(resposta);
            URI uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
            return ResponseEntity.created(uri).body(new RespostaDataOutput(resposta));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<RespostaUsuarioData>> respostaPorTopico(@RequestParam(name = "topico_id") Long id) {
        if (topicoRepository.existsById(id)) {
            List<Object[]> objectRespotas = respostaRepository.findAllByTopicoId(id);
            List<RespostaUsuarioData> respostaUsuarioData = converteObjcts.convertObjectToRespostaUsuarioData(objectRespotas);
            return ResponseEntity.ok().body(respostaUsuarioData);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RespostaDataOutput> atualizarResposta(@PathVariable Long id, @Valid @RequestBody RespostaUpdateData dataInput) {
        Optional<Resposta> respotaOptional = respostaRepository.findById(id);
        if (respotaOptional.isPresent()) {
            Resposta resposta = respotaOptional.get();
            resposta.setMensagem(dataInput.mensagem());
            respostaRepository.save(resposta);
            return ResponseEntity.ok(new RespostaDataOutput(resposta));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Modifying
    public ResponseEntity<?> deletarResposta(@PathVariable Long id, Authentication authentication) {
        Optional<Resposta> respotaOptional = respostaRepository.findById(id);
        if (respotaOptional.isPresent()) {
            if (usuarioPermissao.isCriadorDoTopico(authentication, respotaOptional.get().getAutor().getEmail()) || usuarioPermissao.isAdmin(authentication)) {
                respostaRepository.deleteRespostaById(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Você não tem permissão para deletar esse recurso"));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<?> isRespostaCorreta(@PathVariable Long id, @RequestParam("solucao") Boolean solucao, Authentication authentication){
        Optional<Resposta> respotaOptional = respostaRepository.findById(id);
        if (respotaOptional.isPresent()) {
            if (usuarioPermissao.isCriadorDoTopico(authentication,respotaOptional.get().getAutor().getEmail())) {
                respotaOptional.get().setSolucao(solucao);
                respostaRepository.save(respotaOptional.get());
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Apenas o cirador do tópico pode alterar status da resposta!"));
        }
        return ResponseEntity.notFound().build();
    }
}
