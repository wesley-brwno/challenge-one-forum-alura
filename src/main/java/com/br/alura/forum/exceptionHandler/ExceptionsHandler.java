package com.br.alura.forum.exceptionHandler;

import com.br.alura.forum.validations.ValidationsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(ValidationsException.class)
    public ResponseEntity<MensagemCostumizada> validationsException(ValidationsException validationsException){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String timestamp = ZonedDateTime.now().format(formatter);
        String status = String.valueOf(HttpStatus.BAD_REQUEST.value());
        String error = "Bad Request";
        String message = validationsException.getMessage();

        return ResponseEntity.badRequest().body(new MensagemCostumizada(timestamp, status, error, message));
    }
}


