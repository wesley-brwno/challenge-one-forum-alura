package com.br.alura.forum.controller;

import com.br.alura.forum.DTO.autenticacao.ErrorResponse;
import com.br.alura.forum.DTO.usuario.UsuarioDataInput;
import com.br.alura.forum.DTO.usuario.UsuarioDataOutput;
import com.br.alura.forum.modelo.Usuario;
import com.br.alura.forum.repository.UsuarioRespository;
import com.br.alura.forum.service.usuario.UsuarioPermissao;
import com.br.alura.forum.service.usuario.ValidarUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRespository usuarioRespository;
    @Autowired
    private UsuarioPermissao usuarioPermissao;

    @GetMapping("{id}")
    public ResponseEntity<UsuarioDataOutput> listarPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioRespository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return ResponseEntity.ok().body(new UsuarioDataOutput(usuario));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/todos")
    public ResponseEntity<Page<UsuarioDataOutput>> listarTodos(@PageableDefault(size = 10) Pageable pageable) {
        Page<UsuarioDataOutput> usuarios = usuarioRespository.findAllByIdNotNullOrderByNome(pageable).map(UsuarioDataOutput::new);
        return ResponseEntity.ok().body(usuarios);
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<?> atualizarUsuario(@Valid @RequestBody UsuarioDataInput dataInput, @PathVariable Long id, Authentication authentication) {
        Optional<Usuario> usuarioOptional = usuarioRespository.findById(id);
        if (usuarioOptional.isPresent()) {
            if (usuarioPermissao.isAdmin(authentication) || usuarioPermissao.isCriadorDoTopico(authentication, usuarioOptional.get().getEmail())) {
                Usuario usuario = new Usuario(id, dataInput);
                usuarioRespository.save(usuario);
                return ResponseEntity.ok().body(new UsuarioDataOutput(usuario));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Apenas o dono ou um adimistrador pode atualizar essa conta!"));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id, Authentication authentication) {
        if (usuarioRespository.existsById(id)) {
            if (usuarioPermissao.isAdmin(authentication) || usuarioPermissao.isCriadorDoTopico(authentication, usuarioRespository.findById(id).get().getEmail())) {
                usuarioRespository.deleteById(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Apenas o dono ou um adimistrador pode deletar essa conta!"));
        }
        return ResponseEntity.notFound().build();
    }
}
