package br.edu.unoesc.ebrick.http.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import br.edu.unoesc.ebrick.auth.AuthUtils;
import br.edu.unoesc.ebrick.auth.entities.Permissao;
import br.edu.unoesc.ebrick.http.HttpUtils;

public class HandlerAutorizado extends HandlerAutenticado{
	protected Permissao permissao;
	
	public HandlerAutorizado(String requestPath, String fallback, Permissao permissao) {
		super(requestPath, fallback);
		
		this.permissao = permissao;
	}
	
	public HandlerAutorizado(String requestPath, Permissao permissao) {
		this(requestPath, HttpUtils.PAGINA_INICIO, permissao);
	}

	@Override
	protected void handleAutenticado(HttpExchange exchange) throws IOException{
		if(AuthUtils.autorizado(exchange, permissao)) {
			handleAutorizado(exchange);
		}else {
			HttpUtils.mudarLocation(exchange, fallback);
		}
    }

	protected void handleAutorizado(HttpExchange exchange) throws IOException {
		super.handleAutenticado(exchange);
	}
}
