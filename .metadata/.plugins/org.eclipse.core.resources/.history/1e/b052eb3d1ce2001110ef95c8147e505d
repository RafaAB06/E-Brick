package br.edu.unoesc.ebrick.http.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import br.edu.unoesc.ebrick.http.HttpUtils;

public class FileHandler implements HttpHandler {
	private String file;
	private String type;
	
	public FileHandler(String file, String type) {
		this.file = file;
		this.type = type;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if(HttpUtils.IsGet(exchange)) {
			HttpUtils.send(exchange, HttpURLConnection.HTTP_OK, file, type);
		}
	}

}
