package br.edu.unoesc.ebrick.http.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import br.edu.unoesc.ebrick.auth.Sessoes;
import br.edu.unoesc.ebrick.auth.Usuarios;
import br.edu.unoesc.ebrick.auth.entities.Usuario;
import br.edu.unoesc.ebrick.http.Cookies;
import br.edu.unoesc.ebrick.http.HttpUtils;
import br.edu.unoesc.ebrick.http.Resposta;
import br.edu.unoesc.ebrick.http.Server;

public class LoginHandler extends HandlerBasico {	
	public LoginHandler(String requestURI) {
		super(requestURI);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {	
		if(HttpUtils.IsPost(exchange)) {
			processarLogin(exchange);
		}else super.handle(exchange);
	}

	private void processarLogin(HttpExchange exchange) throws IOException {
		Resposta resposta = Resposta.PADRAO;
				
		try {
			String json = HttpUtils.getBody(exchange);//odeio try catch, muito feio
			JSONObject jsonObject = new JSONObject(json);
			String login = jsonObject.getString("login");
						
			Usuario user = Usuarios.getInstancia().getUsuarioPeloLogin(login);
			if(user == null) {
				resposta = new Resposta(HttpURLConnection.HTTP_NOT_FOUND, "Usuário não encontrado");
			}else {
				String senha = jsonObject.getString("senha");
				
				if(!user.senhaValida(senha)) {
					resposta = new Resposta(HttpURLConnection.HTTP_UNAUTHORIZED, "Senha incorreta"); 
				}else {
					validarSessao(exchange, user);
				}
			}
		}catch(Exception e) {
			resposta = new Resposta(HttpURLConnection.HTTP_INTERNAL_ERROR, "Erro desconhecido");
			e.printStackTrace();
		}
		
		HttpUtils.send(exchange, resposta, HttpUtils.TEXT_PLAIN);
	}

	private void validarSessao(HttpExchange exchange, Usuario usuario) throws IOException {
		String idSessao = Cookies.getCookieSessao(exchange);
		
		if(!Sessoes.getInstancia().sessaoValida(idSessao)){
			String token = Sessoes.getInstancia().criarSessao(usuario);
			
			Cookies.addCookieSessao(exchange, token);
		}
	}

}
