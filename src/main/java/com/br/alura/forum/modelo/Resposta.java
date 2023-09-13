package com.br.alura.forum.modelo;

import com.br.alura.forum.DTO.resposta.RespostaDataInput;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
@NoArgsConstructor
public class Resposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String mensagem;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "topico_id")
	private Topico topico;
	@JsonAlias("data_criacao")
	private LocalDateTime dataCriacao = LocalDateTime.now();
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario autor;
	private Boolean solucao = false;

	public Resposta(RespostaDataInput dataInput, Usuario usuario, Topico topico) {
		this.mensagem = dataInput.mensagem();
		this.autor = usuario;
		this.topico = topico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resposta other = (Resposta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Topico getTopico() {
		return topico;
	}

	public void setTopico(Topico topico) {
		this.topico = topico;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public Boolean getSolucao() {
		return solucao;
	}

	public void setSolucao(Boolean solucao) {
		this.solucao = solucao;
	}

}
