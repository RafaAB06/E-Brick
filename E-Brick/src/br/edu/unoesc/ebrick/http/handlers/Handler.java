package br.edu.unoesc.ebrick.http.handlers;

import com.sun.net.httpserver.HttpHandler;

public abstract class Handler implements HttpHandler{
	private String requestURI;
	
	public Handler(String requestURI) {
		this.requestURI = requestURI;
	}
	
	public String getRequestURI() {
		return requestURI;
	}
}
