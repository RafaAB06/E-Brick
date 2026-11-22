package br.edu.unoesc.ebrick.auth.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class Permissao {
	private String id;
	private String nome;
	
	public Permissao(String id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", id);
			jsonObject.put("nome", nome);
		}catch(JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	@Override
	public boolean equals(Object other) {
		return ((Permissao)other).getId().equals(id);
	}
	
	@Override
	public String toString() {
	    return id;
	}
}
