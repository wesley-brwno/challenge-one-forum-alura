package com.br.alura.forum.constrains;

public enum StatusTopico {
	
	NAO_RESPONDIDO("sem reposta"),
	NAO_SOLUCIONADO("sem solução"),
	SOLUCIONADO("solucionado"),
	FECHADO("fechado");

	String status;

	StatusTopico(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
