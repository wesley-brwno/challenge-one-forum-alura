package com.br.alura.forum.repository;

import com.br.alura.forum.modelo.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    @Query("SELECT c.id FROM Curso c WHERE c.nome LIKE %:nome% ORDER BY c.nome")
    List<Long> findByNome(String nome);
}
