package br.edu.unoesc.ebrick.http.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;

import br.edu.unoesc.ebrick.http.HttpUtils;

public class FileHandler extends Handler {
	private String file;
	private String type;
	
	public FileHandler(String uri, String file, String type) {
		super(uri);
		
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
