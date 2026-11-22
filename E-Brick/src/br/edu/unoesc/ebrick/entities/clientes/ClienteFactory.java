package br.edu.unoesc.ebrick.entities.clientes;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import br.edu.unoesc.ebrick.validator.Validator;

public class ClienteFactory {
	private ClienteFactory() {}
	
	public static Cliente fromResultSet(ResultSet result) throws SQLException {
		long id = result.getLong("id");
	    String nome = result.getString("nome");
	    String telefone = result.getString("telefone");
	    String email = result.getString("email");
	    String endereco = result.getString("endereco");
	    String cidade = result.getString("cidade");
	    String estado = result.getString("estado");
	    boolean ativo = result.getBoolean("ativo");

	    String cpf = result.getString("cpf");
	    String cnpj = result.getString("cnpj");
	    
	    Cliente cliente = null;
	    if (cpf != null) {
	        cliente = new PessoaFisica(id, nome, telefone, email, endereco, cidade, estado, ativo, cpf);
	    } else if (cnpj != null) {
	    	cliente = new PessoaJuridica(id, nome, telefone, email, endereco, cidade, estado, ativo, cnpj);
	    }
	    return cliente;
	}
	
	public static Cliente fromJsonObject(JSONObject jsonObject) {
		long id = jsonObject.optLong("id");
	    String nome = jsonObject.optString("nome");
	    String telefone = jsonObject.optString("telefone");
	    String email = jsonObject.optString("email");
	    String endereco = jsonObject.optString("endereco");
	    String cidade = jsonObject.optString("cidade");
	    String estado = jsonObject.optString("estado");
	    boolean ativo = jsonObject.optBoolean("ativo");
	    
	    if(!Validator.validarTelefone(telefone)) {
	    	throw new IllegalArgumentException("Telefone inválido");
	    }

	    String cpf = jsonObject.optString("cpf");
	    String cnpj = jsonObject.optString("cnpj");
	    
	    Cliente cliente = null;
	    if (!cpf.isEmpty()) {
	    	if(Validator.validarCPF(cpf)) {
	    		cliente = new PessoaFisica(id, nome, telefone, email, endereco, cidade, estado, ativo, cpf);
	    	}else throw new IllegalArgumentException("CPF inválido");
	    }else if (!cnpj.isEmpty()) {
	    	if(Validator.validarCNPJ(cnpj)) {
	    		cliente = new PessoaJuridica(id, nome, telefone, email, endereco, cidade, estado, ativo, cnpj);
	    	}else throw new IllegalArgumentException("CNPJ inválido");
	    }
	    
	    return cliente;
	}
}
