package br.edu.unoesc.ebrick.auth;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.unoesc.ebrick.auth.entities.Permissao;
import br.edu.unoesc.ebrick.sql.SqlConnection;

public class Permissoes {
	private static Permissoes instancia = new Permissoes();
	public static Permissoes getInstancia() {
        return instancia;
    }
	private Permissoes() {}
	
	public List<Permissao> getTodasPermissoes(){
		List<Permissao> permissoes = new ArrayList<>();
		String sql = "SELECT * FROM permissao;";
		
		try {
			PreparedStatement statement = SqlConnection.getConnetion().prepareStatement(sql);
			ResultSet results = statement.executeQuery();
			
			while(results.next()) {
				String id = results.getString(1);
				String nome = results.getString(2);
				
				permissoes.add(new Permissao(id, nome));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return permissoes;
	}
}
