package br.edu.unoesc.ebrick.http.handlers;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

import com.sun.net.httpserver.HttpExchange;

import br.edu.unoesc.ebrick.http.HttpUtils;
import br.edu.unoesc.ebrick.utils.FileUtils;

public class HandlerBasico extends Handler {	
	public HandlerBasico(String requestURI) {
		super(requestURI);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException{
	    if(HttpUtils.IsGet(exchange)) {
	        if(!servePagina(exchange)) {
	        	handleGetSemPagina(exchange);
	        }
	    }
	}

	private boolean servePagina(HttpExchange exchange) {		
		String uri = exchange.getRequestURI().getPath();
		String path = uriParaPathAbsoluto(uri);
		File file = new File(path);
		
		if(file.exists() && file.isFile()) {
			try {
				String mimeType = Files.probeContentType(Path.of(path));
				HttpUtils.send(exchange, HttpURLConnection.HTTP_OK, FileUtils.getBytes(file), mimeType);
				
				return true;
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	private String uriParaPathAbsoluto(String uri) {
		return String.format("web%s", uri);
	}
	
	protected void handleGetSemPagina(HttpExchange exchange) throws IOException{
		HttpUtils.mudarLocation(exchange, String.format("%s/index.html", getRequestURI()));
	}
}
