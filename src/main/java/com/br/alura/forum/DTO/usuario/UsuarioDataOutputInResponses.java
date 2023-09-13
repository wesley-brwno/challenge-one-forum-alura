package com.br.alura.forum.DTO.usuario;

import com.br.alura.forum.modelo.Usuario;

public record UsuarioDataOutputInResponses(
        Long id,
        String nome
) {
    public UsuarioDataOutputInResponses(Usuario autor) {
        this(autor.getId(), autor.getNome());
    }
}
