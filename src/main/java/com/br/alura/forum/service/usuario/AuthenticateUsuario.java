package com.br.alura.forum.service.usuario;

import com.br.alura.forum.repository.UsuarioRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUsuario implements UserDetailsService {
    @Autowired
    private UsuarioRespository respository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return respository.findByEmail(username);
    }
}
