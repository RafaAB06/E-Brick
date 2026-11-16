package br.edu.unoesc.ebrick.auth.entities;

import br.edu.unoesc.ebrick.auth.Sessoes;

public class Sessao {
	private Usuario usuario;
	private String token;
	private long expiraEm;
	
	public Sessao(Usuario usuario, String token, long timer) {
		this.usuario = usuario;
		this.token = token;
		
		reset();
	}
	
	public void reset() {
		long criadaEm = System.currentTimeMillis();
		expiraEm = criadaEm + Sessoes.TEMPO_SESSAO;
	}
	
	public boolean expirou() {
		return System.currentTimeMillis() >= expiraEm;
	}

	public String getToken() {
		return token;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
}
