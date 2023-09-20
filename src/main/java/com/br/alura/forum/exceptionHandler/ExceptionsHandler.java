package com.br.alura.forum.exceptionHandler;

import com.br.alura.forum.DTO.autenticacao.ErrorResponse;
import com.br.alura.forum.validations.ValidationsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestControllerAdvice
@Slf4j
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErrorValidation>> handleMenthodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(fieldErrors.stream().map(DadosErrorValidation::new).toList());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentilsException(BadCredentialsException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Senha ou email inválidos!"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccesDeniedException(AccessDeniedException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Acesso negado!"));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExcepion(Exception exception) {
        log.error(exception.getMessage());
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error " + exception.getLocalizedMessage()));
    }

    private record DadosErrorValidation(String campo, String mensagem) {
        public DadosErrorValidation(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}


