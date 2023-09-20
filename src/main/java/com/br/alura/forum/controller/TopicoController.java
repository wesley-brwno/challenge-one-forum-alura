package com.br.alura.forum.controller;

import com.br.alura.forum.DTO.autenticacao.ErrorResponse;
import com.br.alura.forum.DTO.topico.CadastrarTopicoDados;
import com.br.alura.forum.DTO.topico.TopicoComRespostas;
import com.br.alura.forum.DTO.topico.TopicoDetalhes;
import com.br.alura.forum.constrains.StatusTopico;
import com.br.alura.forum.modelo.Curso;
import com.br.alura.forum.modelo.Topico;
import com.br.alura.forum.modelo.Usuario;
import com.br.alura.forum.repository.CursoRepository;
import com.br.alura.forum.repository.TopicoRepository;
import com.br.alura.forum.repository.UsuarioRespository;
import com.br.alura.forum.service.topico.PopularTopicoComRespostas;
import com.br.alura.forum.service.topico.ValidarTopico;
import com.br.alura.forum.service.usuario.UsuarioPermissao;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private ValidarTopico validarTopico;
    @Autowired
    private UsuarioPermissao usuarioPermissao;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private UsuarioRespository usuarioRespository;
    @Autowired
    private PopularTopicoComRespostas popularTopicoComRespostas;

    @SecurityRequirement(name = "bearer-key")
    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDetalhes> cadastrar(@Valid @RequestBody CadastrarTopicoDados dados, UriComponentsBuilder uriBuilder) {
        validarTopico.aplicarValidacoes(dados);
        Usuario usuario = usuarioRespository.findById(dados.autorId()).get();
        Curso curso = cursoRepository.findById(dados.cursoId()).get();
        Topico topico = new Topico(dados, usuario, curso);
        topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDetalhes(topico));
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

    @SecurityRequirement(name = "bearer-key")
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<?> atualizarTopico(@Valid @RequestBody CadastrarTopicoDados dados, @PathVariable Long id, Authentication authentication) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isPresent()) {
            if (usuarioPermissao.isCriadorDoTopico(authentication, topico.get().getAutor().getEmail())) {
                validarTopico.aplicarValidacoes(dados);
                Usuario usuario = usuarioRespository.findById(dados.autorId()).get();
                Curso curso = cursoRepository.findById(dados.cursoId()).get();
                topico.get().setTitulo(dados.titulo());
                topico.get().setMensagem(dados.mensagem());
                topico.get().setAutor(usuario);
                topico.get().setCurso(curso);
                topicoRepository.save(topico.get());
                return ResponseEntity.ok().body(new TopicoDetalhes(topico.get()));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("O topico de id "+id+" não pertence a esse usuário!"));
        }
        return ResponseEntity.notFound().build();
    }

    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping("{id}")
    @Transactional
    @Modifying
    public ResponseEntity<?> deletarTopico(@PathVariable Long id, Authentication authentication) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            if (usuarioPermissao.isAdmin(authentication) || usuarioPermissao.isCriadorDoTopico(authentication, topico.get().getAutor().getEmail())) {
                topicoRepository.delete(topico.get());
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Usuário não é o criador desse tópico!"));
        }
        return ResponseEntity.notFound().build();
    }

    @SecurityRequirement(name = "bearer-key")
    @PutMapping("/{topico-fechado}/{id}")
    @Transactional
    public ResponseEntity<?> atualizarTopicoStatusFechado(@RequestParam("topico-fechado") Boolean fechado, @RequestParam("id") Long id, Authentication authentication) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            if (usuarioPermissao.isAdmin(authentication) || usuarioPermissao.isCriadorDoTopico(authentication, topico.get().getAutor().getEmail())) {
                topico.get().setStatus(StatusTopico.FECHADO);
                topicoRepository.save(topico.get());
                return ResponseEntity.ok().body(new TopicoDetalhes(topico.get()));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Você não tem permissão para mudar o status deste tópico para fechado. Apenas quem criou o tópico ou um administrador pode fazer isso. Se você precisar fechar este tópico, entre em contato com o administrador."));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/status")
    public ResponseEntity<Page<TopicoDetalhes>> listarPorStatus (@RequestParam StatusTopico status, Pageable pageable) {
        System.err.println(status);
        Page<TopicoDetalhes> topicos = topicoRepository.findByStatus(status, pageable).map(TopicoDetalhes::new);
        return ResponseEntity.ok().body(topicos);
    }

    @GetMapping("/{topico_id}/respostas")
    public ResponseEntity<TopicoComRespostas> listarTopicoComRespostas(@PathVariable("topico_id") Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if(topico.isPresent()) {
            return ResponseEntity.ok().body(popularTopicoComRespostas.obterDados(topico.get()));
        }
        return ResponseEntity.notFound().build();
    }
}
