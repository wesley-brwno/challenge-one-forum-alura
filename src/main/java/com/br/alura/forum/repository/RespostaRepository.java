package com.br.alura.forum.repository;

import com.br.alura.forum.modelo.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    @Query("""
            SELECT r.id, u.nome, r.mensagem, r.dataCriacao, r.solucao
            FROM Resposta r INNER JOIN Usuario u
            WHERE r.topico.id = :id AND u.id = r.autor.id
            ORDER BY r.dataCriacao
            """)
    List<Object[]> findAllByTopicoId(Long id);

    @Query("SELECT u.nome FROM Resposta r INNER JOIN Usuario u WHERE r.id = :id AND u.id = r.autor.id")
    String findUsuarioNomeById(Long id);

    @Query("DELETE FROM Resposta r WHERE r.id = :id")
    @Modifying
    void deleteRespostaById(Long id);
}
