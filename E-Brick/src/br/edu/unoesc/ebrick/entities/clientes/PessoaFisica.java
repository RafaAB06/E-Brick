package br.edu.unoesc.ebrick.entities.clientes;

import org.json.JSONObject;

public class PessoaFisica extends Cliente{
	private String cpf;
	
	public PessoaFisica(long id, String nome, String telefone, String email, String endereco, String cidade, String estado, boolean ativo, String cpf) {
		super(id, nome, telefone, email, endereco, cidade, estado, ativo);
		
		this.cpf = cpf;
	}
	
	public String getCPF() {
		return cpf;
	}
	
	@Override
	public JSONObject toJsonObject() {
        JSONObject json = super.toJsonObject();
        json.put("cpf", cpf);
        
        return json;
    }
}
