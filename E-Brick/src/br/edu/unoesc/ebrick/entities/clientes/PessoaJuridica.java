package br.edu.unoesc.ebrick.entities.clientes;

import org.json.JSONObject;

public class PessoaJuridica extends Cliente {
    private String cnpj;

    public PessoaJuridica(long id, String nome, String telefone, String email, String endereco, String cidade, String estado, boolean ativo, String cnpj) {
        super(id, nome, telefone, email, endereco, cidade, estado, ativo);
        this.cnpj = cnpj;
    }

    public String getCNPJ() {
        return cnpj;
    }
    
    @Override
	public JSONObject toJsonObject() {
        JSONObject json = super.toJsonObject();
        json.put("cnpj", cnpj);
        
        return json;
    }
}