package com.br.alura.forum.exceptionHandler;

public record MensagemCostumizada(
        String timestamp,
        String status,
        String error,
        String message
) {
}
