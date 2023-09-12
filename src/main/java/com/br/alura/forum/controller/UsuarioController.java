package com.br.alura.forum.controller;

import com.br.alura.forum.DTO.usuario.UsuarioDataInput;
import com.br.alura.forum.DTO.usuario.UsuarioDataOutput;
import com.br.alura.forum.modelo.Usuario;
import com.br.alura.forum.repository.UsuarioRespository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRespository usuarioRespository;
    @PostMapping
    @Transactional
    public ResponseEntity<UsuarioDataOutput> cadastrarUsuario(@Valid @RequestBody UsuarioDataInput dataInput, UriComponentsBuilder uriBuilder) {
        Usuario usuario = new Usuario(dataInput);
        usuarioRespository.save(usuario);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioDataOutput(usuario));
    }

    @GetMapping("{id}")
    public ResponseEntity<UsuarioDataOutput> listarPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioRespository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return ResponseEntity.ok().body(new UsuarioDataOutput(usuario));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/todos")
    public ResponseEntity<Page<UsuarioDataOutput>> listarTodos(@PageableDefault(size = 10) Pageable pageable) {
        Page<UsuarioDataOutput> usuarios = usuarioRespository.findAllByIdNotNullOrderByNome(pageable).map(UsuarioDataOutput::new);
        return ResponseEntity.ok().body(usuarios);
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<UsuarioDataOutput> atualizarUsuario(@Valid @RequestBody UsuarioDataInput dataInput, @PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioRespository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = new Usuario(id, dataInput);
            usuarioRespository.save(usuario);
            return ResponseEntity.ok().body(new UsuarioDataOutput(usuario));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioRespository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
