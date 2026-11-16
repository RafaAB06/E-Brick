package br.edu.unoesc.ebrick.http.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import br.edu.unoesc.ebrick.auth.Sessoes;
import br.edu.unoesc.ebrick.http.Cookies;
import br.edu.unoesc.ebrick.http.HttpUtils;

public class HandlerPadrao implements HttpHandler{
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if(HttpUtils.IsGet(exchange)) {
			String token = Cookies.getCookieSessao(exchange);
			String destino = null;
			
			if(Sessoes.getInstancia().sessaoValida(token)) {
				destino = "/sistema";
			}else {
				destino = "/login";
			}
			
			HttpUtils.mudarLocation(exchange, destino);
		}
	}

}
