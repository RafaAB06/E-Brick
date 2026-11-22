package br.edu.unoesc.ebrick.entities.clientes;

import org.json.JSONObject;

public abstract class Cliente {
	private long id;
	private String nome;
	private String telefone;
	private String email;
	private String endereco;
	private String cidade;
	private String estado;
	private boolean ativo;
	
	public Cliente(long id, String nome, String telefone, String email, String endereco, String cidade, String estado, boolean ativo) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
		this.cidade = cidade;
		this.estado = estado;
		this.ativo = ativo;
	}
	
	public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.put("id", getId());
        json.put("nome", getNome());
        json.put("telefone", getTelefone());
        json.put("email", getEmail());
        json.put("endereco", getEndereco());
        json.put("cidade", getCidade());
        json.put("estado", getEstado());
        json.put("ativo", isAtivo());
        
        return json;
    }

	public long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
}

