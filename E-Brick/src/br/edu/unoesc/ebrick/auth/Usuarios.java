package br.edu.unoesc.ebrick.auth;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.unoesc.ebrick.auth.entities.Permissao;
import br.edu.unoesc.ebrick.auth.entities.Usuario;
import br.edu.unoesc.ebrick.sql.SqlConnection;

public final class Usuarios {
	private static Usuarios instancia = new Usuarios();
	public static Usuarios getInstancia() {
        return instancia;
    }
	private Usuarios() {}

	private List<Usuario> usuarios = new ArrayList<Usuario>();

	public void inicializar() throws SQLException {
		String sql = "SELECT * FROM usuario;";
		PreparedStatement statement = SqlConnection.getConnetion().prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		
		while(result.next()){
			long id = result.getLong(1);
			String nome = result.getString(2);
			String senha = result.getString(3);
			List<Permissao> permissoes = selectPermissoes(nome);
			
			usuarios.add(new Usuario(id, nome, senha, permissoes));
		}
	}
	
	private List<Permissao> selectPermissoes(String nomeUsuario) throws SQLException {
		List<Permissao> permissoes = new ArrayList<Permissao>();
		
		String sql = "SELECT * FROM obter_permissoes(?);";
		PreparedStatement statement = SqlConnection.getConnetion().prepareStatement(sql);
		statement.setString(1, nomeUsuario);
		ResultSet result = statement.executeQuery();
		
		while(result.next()){
			String id = result.getString(1);
			String nome = result.getString(2);
			
			permissoes.add(new Permissao(id, nome));
		}
		
		return permissoes;
	}
	
	public Usuario getUsuarioPeloLogin(String login) {
		Usuario usuario = null;
		
		for(Usuario u : usuarios) {
			if(u.getLogin().equals(login)) {
				usuario = u;
				break;
			}
		}
		
		return usuario;
	}
}
