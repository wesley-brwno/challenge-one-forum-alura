package com.br.alura.forum.controller;

import com.br.alura.forum.DTO.curso.CursoDataInput;
import com.br.alura.forum.DTO.curso.CursoDataOutput;
import com.br.alura.forum.modelo.Curso;
import com.br.alura.forum.repository.CursoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @SecurityRequirement(name = "bearer-key")
    @PostMapping
    @Transactional
    public ResponseEntity<CursoDataOutput> cadastrar(@Valid @RequestBody CursoDataInput dataInput, UriComponentsBuilder uriBuilder) {
        Curso curso = new Curso(dataInput);
        cursoRepository.save(curso);
        URI uri = uriBuilder.path("/curso/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(uri).body(new CursoDataOutput(curso));
    }

    @GetMapping("{id}")
    public ResponseEntity<CursoDataOutput> listarPorId(@PathVariable Long id) {
        if (cursoRepository.existsById(id)) {
            Curso curso = cursoRepository.findById(id).get();
            return ResponseEntity.ok(new CursoDataOutput(curso));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Page<CursoDataOutput>> listarTodos(@PageableDefault(size = 10, sort = {"nome"})Pageable pageable) {
        Page<CursoDataOutput> cursos = cursoRepository.findAll(pageable).map(CursoDataOutput::new);
        return ResponseEntity.ok(cursos);
    }

    @SecurityRequirement(name = "bearer-key")
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<Void> atualizarCurso(@Valid @RequestBody CursoDataInput dataInput, @PathVariable Long id) {
        if (cursoRepository.existsById(id)) {
            Curso curso = new Curso(id, dataInput.nome(), dataInput.categoria());
            cursoRepository.save(curso);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> deletarCuro(@PathVariable Long id) {
        cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
