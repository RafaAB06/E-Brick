package br.edu.unoesc.ebrick.entities;

import org.json.JSONObject;

public class Cliente {
	private String cpf;
	private String nome;
	
	public Cliente(String json) {
		JSONObject jsonObject = new JSONObject(json);
		
		cpf = jsonObject.getString("cpf");
		nome = jsonObject.getString("nome");
	}
	
	public Cliente(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}
}
