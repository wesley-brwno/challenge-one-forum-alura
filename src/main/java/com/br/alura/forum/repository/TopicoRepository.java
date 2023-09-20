package com.br.alura.forum.repository;

import com.br.alura.forum.constrains.StatusTopico;
import com.br.alura.forum.modelo.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findAllByIdNotNull(Pageable pageable);
    Page<Topico> findAllByCurso_IdOrderByDataCriacao(Pageable pageable, Long id);
    @Query("SELECT t FROM Topico t WHERE YEAR(t.dataCriacao) = :ano ORDER BY t.titulo ASC")
    Page<Topico> findByAno(Pageable pageable, Long ano);
    long countByTitulo(String titulo);
    long countByMensagem(String mensagem);
    Topico findByTituloAndMensagem(String titulo, String mensagem);

    Page<Topico> findByStatus(StatusTopico status, Pageable pageable);
}
