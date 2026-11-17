package br.edu.unoesc.ebrick.http.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import br.edu.unoesc.ebrick.auth.Sessoes;
import br.edu.unoesc.ebrick.auth.entities.Permissao;
import br.edu.unoesc.ebrick.auth.entities.Usuario;
import br.edu.unoesc.ebrick.http.Cookies;
import br.edu.unoesc.ebrick.http.HttpUtils;

public abstract class HandlerAba extends HandlerAutenticado{
	protected Permissao permissao;
	
	public HandlerAba(Permissao permissao) {
		this.permissao = permissao;
	}

	@Override
	protected void handleAutenticado(HttpExchange exchange) throws IOException {
		String token = Cookies.getCookieSessao(exchange);
		Usuario user = Sessoes.getInstancia().getUsuarioPorToken(token);
		if(user.temPermissao(permissao)) {
			
			
			handleAutorizado(exchange);
		}else {
			HttpUtils.mudarLocation(exchange, "/sistema");
		}
	}

	protected abstract void handleAutorizado(HttpExchange exchange);
}
