package br.edu.unoesc.ebrick.http;

import java.net.HttpURLConnection;

public class Resposta {
	public static final Resposta PADRAO = new Resposta(HttpURLConnection.HTTP_OK, "Sucesso");
	public static final Resposta SESSAO_INVALIDA = new Resposta(HttpURLConnection.HTTP_UNAUTHORIZED, "Sessão Inválida");
	
	private int codigo;
	private String mensagem;
	
	public Resposta(int codigo, String mensagem) {
		this.codigo = codigo;
		this.mensagem = mensagem;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public String getMensagem() {
		return mensagem;
	}
}
