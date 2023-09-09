package com.br.alura.forum.validations;

public class ValidationsException extends RuntimeException{
    public ValidationsException(String messagem) {
        super(messagem);
    }
}
