package br.edu.unoesc.ebrick.http.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import br.edu.unoesc.ebrick.auth.AuthUtils;
import br.edu.unoesc.ebrick.http.HttpUtils;

public class HandlerAutenticado extends HandlerBasico {
	protected String fallback;
	
	public HandlerAutenticado(String requestPath) {
		super(requestPath);	
		
		fallback = HttpUtils.PAGINA_LOGIN;
	}
	
	public HandlerAutenticado(String requestPath, String fallback) {
		super(requestPath);
		
		this.fallback = fallback;
	}

	@Override
    public void handle(HttpExchange exchange) throws IOException {
        if (AuthUtils.autenticado(exchange)) {
            handleAutenticado(exchange);
        } else {
            handleNaoAutenticado(exchange);
        }    
    }

    protected void handleAutenticado(HttpExchange exchange) throws IOException{
    	super.handle(exchange);    	
    }

    private void handleNaoAutenticado(HttpExchange exchange) throws IOException{
    	HttpUtils.mudarLocation(exchange, fallback);
    }
}
