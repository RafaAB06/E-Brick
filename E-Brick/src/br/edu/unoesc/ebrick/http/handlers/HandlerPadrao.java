package br.edu.unoesc.ebrick.http.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import br.edu.unoesc.ebrick.auth.AuthUtils;
import br.edu.unoesc.ebrick.http.HttpUtils;

public class HandlerPadrao extends Handler{
	public HandlerPadrao(String requestURI) {
		super(requestURI);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if(HttpUtils.IsGet(exchange)) {
			String destino;
			
			if(AuthUtils.autenticado(exchange)) {
				destino = HttpUtils.PAGINA_SISTEMA;
			}else {
				destino = HttpUtils.PAGINA_LOGIN;
			}
			
			HttpUtils.mudarLocation(exchange, destino);
		}
	}

}
