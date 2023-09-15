package com.br.alura.forum.controller;

import com.br.alura.forum.DTO.usuario.UsuarioDataInput;
import com.br.alura.forum.DTO.usuario.UsuarioDataOutput;
import com.br.alura.forum.DTO.usuario.UsuarioLogin;
import com.br.alura.forum.modelo.Usuario;
import com.br.alura.forum.repository.UsuarioRespository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRespository usuarioRespository;
    @PostMapping("/registrar")
    public ResponseEntity<?> regisrar(@Valid @RequestBody UsuarioDataInput data) {
        String encode = new BCryptPasswordEncoder().encode(data.senha());
        Usuario usuario = new Usuario(data.nome(), data.email(), encode);
        usuarioRespository.save(usuario);
        return ResponseEntity.ok().body(new UsuarioDataOutput(usuario.getId(), usuario.getNome(), usuario.getEmail(), data.senha().replaceAll(".", "*")));
    }

    @PostMapping("/entrar")
    public ResponseEntity<Void> logar(@Valid @RequestBody UsuarioLogin data) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        Authentication authenticate = authenticationManager.authenticate(token);
        return ResponseEntity.ok().build();
    }
}
