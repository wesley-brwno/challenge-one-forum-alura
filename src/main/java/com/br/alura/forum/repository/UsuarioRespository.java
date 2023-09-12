package com.br.alura.forum.repository;

import com.br.alura.forum.modelo.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRespository extends JpaRepository<Usuario, Long> {
    Page<Usuario> findAllByIdNotNullOrderByNome(Pageable pageable);
}
