package com.br.alura.forum.controller;

import com.br.alura.forum.DTO.resposta.*;
import com.br.alura.forum.modelo.Resposta;
import com.br.alura.forum.modelo.Topico;
import com.br.alura.forum.modelo.Usuario;
import com.br.alura.forum.repository.RespostaRepository;
import com.br.alura.forum.repository.TopicoRepository;
import com.br.alura.forum.repository.UsuarioRespository;
import com.br.alura.forum.service.reposta.ConverteObjectsParaRespostaUsuarioData;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    @Transactional
    public ResponseEntity<RespostaDataOutput> cadastarResposta(@Valid @RequestBody RespostaDataInput dataInput, UriComponentsBuilder uriBuilder) {
        if (usuarioRespository.existsById(dataInput.autorId()) && topicoRepository.existsById(dataInput.topicoId())) {
            Usuario usuario = usuarioRespository.findById(dataInput.autorId()).get();
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

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deletarResposta(@RequestBody DeletarResposta dataInput) {
        String usuario = respostaRepository.findUsuarioNomeById(dataInput.id());
        if (usuario.equals(dataInput.autor())) {
            respostaRepository.deleteRespostaById(dataInput.id());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
