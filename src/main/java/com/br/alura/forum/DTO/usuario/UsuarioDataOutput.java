package com.br.alura.forum.DTO.usuario;

import com.br.alura.forum.modelo.Usuario;

public record UsuarioDataOutput(
        Long id,
        String nome,
        String email
) {
    public UsuarioDataOutput(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
