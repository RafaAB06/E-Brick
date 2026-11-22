package br.edu.unoesc.ebrick.entities.clientes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.unoesc.ebrick.sql.SqlConnection;

public final class ClienteRepository {
	private static ClienteRepository instancia = new ClienteRepository();
	private ClienteRepository() {}
	public static ClienteRepository getInstancia() {
		return instancia;
	}
	
	public List<Cliente> getClientes(String pesquisa, String ordem, int inicio, int qtd) throws SQLException{
		// id, nome, telefone, email, endereco, cidade, estado, ativo, cpf, cnpj
		List<Cliente> clientes = new ArrayList<>();
		
		String sql = "SELECT * FROM buscar_clientes(?, ?, ?, ?)";
		PreparedStatement statement = SqlConnection.getConnetion().prepareStatement(sql);
		statement.setString(1, pesquisa);
		statement.setString(2, ordem);
		statement.setInt(3, inicio);
		statement.setInt(4, qtd);
		
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			clientes.add(ClienteFactory.fromResultSet(result));
		}
		return clientes;
	}
	
	public void update(Cliente cliente) throws SQLException{
		//id nome telefone email endereco cidade estado ativo cpf cnpj
		String sql = "CALL atualizar_cliente(ROW(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)::cliente_completo);";
		PreparedStatement statement = SqlConnection.getConnetion().prepareStatement(sql);
		
		statement.setLong(1, cliente.getId());
		statement.setString(2, cliente.getNome());
		statement.setString(3, cliente.getTelefone());
		statement.setString(4, cliente.getEmail());
		statement.setString(5, cliente.getEndereco());
		statement.setString(6, cliente.getCidade());
		statement.setString(7, cliente.getEstado());
		statement.setBoolean(8, cliente.isAtivo());
		
		String cpf = null;
		String cnpj = null;

		if (cliente instanceof PessoaFisica pessoaFisica) {
		    cpf = pessoaFisica.getCPF();
		} else if (cliente instanceof PessoaJuridica pessoaJuridica) {
		    cnpj = pessoaJuridica.getCNPJ();
		}
		
		statement.setString(9, cpf);
		statement.setString(10, cnpj);

		statement.execute();
		statement.close();		
	}
	
	public void insert(Cliente cliente) throws SQLException {
		//id nome telefone email endereco cidade estado ativo cpf cnpj
		String sql = "CALL adicionar_cliente(?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement statement = SqlConnection.getConnetion().prepareStatement(sql);
		
		statement.setString(1, cliente.getNome());
		statement.setString(2, cliente.getTelefone());
		statement.setString(3, cliente.getEmail());
		statement.setString(4, cliente.getEndereco());
		statement.setString(5, cliente.getCidade());
		statement.setString(6, cliente.getEstado());
		
		String cpf = null;
		String cnpj = null;

		if (cliente instanceof PessoaFisica pessoaFisica) {
		    cpf = pessoaFisica.getCPF();
		} else if (cliente instanceof PessoaJuridica pessoaJuridica) {
		    cnpj = pessoaJuridica.getCNPJ();
		}
		
		statement.setString(7, cpf);
		statement.setString(8, cnpj);

		statement.execute();
		statement.close();	
	}
}
