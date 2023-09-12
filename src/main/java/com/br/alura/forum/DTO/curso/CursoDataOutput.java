package com.br.alura.forum.DTO.curso;

import com.br.alura.forum.modelo.Curso;

public record CursoDataOutput(
        Long id,
        String nome,
        String categoria
) {
    public CursoDataOutput(Curso curso) {
        this(curso.getId(), curso.getNome(), curso.getCategoria());
    }
}
