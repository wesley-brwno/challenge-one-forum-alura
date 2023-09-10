package com.br.alura.forum.modelo;

import com.br.alura.forum.DTO.topico.CadastrarTopicoDados;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao = LocalDateTime.now();
	private StatusTopico status = StatusTopico.NAO_RESPONDIDO;
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario autor;
	@ManyToOne
	@JoinColumn(name = "curso_id")
	private Curso curso;
	@OneToMany(mappedBy = "topico")
	private List<Resposta> respostas = new ArrayList<>();

	public Topico(CadastrarTopicoDados dados, Usuario usuario, Curso curso) {
		this.titulo = dados.titulo();
		this.mensagem = dados.mensagem();
		this.autor = usuario;
		this.curso = curso;
	}

	public Topico(Long id, CadastrarTopicoDados dados, Usuario usuario, Curso curso) {
		this(dados, usuario, curso);
		this.id = id;
	}
}

