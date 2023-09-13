package com.br.alura.forum.DTO.resposta;

import java.time.LocalDateTime;

public record RespostaUsuarioData(
        Long id,
        String nome,
        String mensagem,
        LocalDateTime dataCriacao,
        Boolean solucao
) {
}
