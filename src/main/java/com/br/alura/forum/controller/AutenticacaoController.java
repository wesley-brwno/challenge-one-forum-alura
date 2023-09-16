package com.br.alura.forum.controller;

import com.br.alura.forum.DTO.autenticacao.ErrorResponse;
import com.br.alura.forum.DTO.autenticacao.TokenDTO;
import com.br.alura.forum.DTO.usuario.UsuarioDataInput;
import com.br.alura.forum.DTO.usuario.UsuarioDataOutput;
import com.br.alura.forum.DTO.usuario.UsuarioLogin;
import com.br.alura.forum.infra.secutiry.TokenService;
import com.br.alura.forum.modelo.Usuario;
import com.br.alura.forum.repository.UsuarioRespository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRespository usuarioRespository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<?> regisrar(@Valid @RequestBody UsuarioDataInput data, UriComponentsBuilder uriComponentsBuilder) {
        if (usuarioRespository.findByEmail(data.email()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Email já está em uso!"));
        }
        String encode = new BCryptPasswordEncoder().encode(data.senha());
        Usuario usuario = new Usuario(data.nome(), data.email(), encode);
        usuarioRespository.save(usuario);
        URI uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioDataOutput(usuario));
    }

    @PostMapping("/entrar")
    public ResponseEntity<TokenDTO> logar(@Valid @RequestBody UsuarioLogin data) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        Authentication authenticate = authenticationManager.authenticate(token);
        if (authenticate.isAuthenticated()) {
            String tokenJWT = tokenService.generateTokenJWT((Usuario) authenticate.getPrincipal());
            return ResponseEntity.ok().body(new TokenDTO(tokenJWT));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("WWW-Authenticate", "Bearer").build();
    }
}
