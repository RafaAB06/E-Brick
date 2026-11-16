package br.edu.unoesc.ebrick.entities;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.edu.unoesc.ebrick.sql.SqlConnection;

public final class ClienteRepository {
	private ClienteRepository() {}
	
	public static void insert(Cliente cliente) throws SQLException {
		String sql = "INSERT INTO cliente (cpf, nome) VALUES (?, ?)";
		
		PreparedStatement statement = SqlConnection.getConnetion().prepareStatement(sql);
		statement.setString(1, cliente.getCpf().trim());
		statement.setString(2, cliente.getNome().trim());
		statement.execute();
	}
}
