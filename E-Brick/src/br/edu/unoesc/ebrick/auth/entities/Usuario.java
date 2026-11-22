package br.edu.unoesc.ebrick.auth.entities;

import java.util.List;

public class Usuario{
	private long id;
	private String login;
	private String senha;
	private List<Permissao> permissoes;
	
	public Usuario(long id, String login, String senha, List<Permissao> permissoes) {
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.permissoes = permissoes;
	}
	
	public boolean temPermissao(Permissao permissao) {
		for(Permissao p : permissoes) {
			if(permissao.equals(p)) return true;
		}
		
		return false;
	}
	
	public String getLogin() {
		return login;
	}
	
	public boolean senhaValida(String senha) {
		return this.senha.equals(senha);
	}
	
	public List<Permissao> getPermissoes(){
		return permissoes;
	}
	
	@Override
	public String toString() {
		return String.format("Usuário: %d, nome: %s, senha: %s, permissões: %s", id, login, senha, permissoes);
	}
}