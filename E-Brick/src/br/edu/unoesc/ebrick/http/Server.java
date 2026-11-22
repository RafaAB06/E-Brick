package br.edu.unoesc.ebrick.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

import br.edu.unoesc.ebrick.http.handlers.Handler;

public final class Server {
	private static Server instancia = new Server();
	private static HttpServer server;
	private Server() {}

	public static Server getInstancia() {
		return instancia;
	}

	public void start(int port) throws IOException {
		server = HttpServer.create(new InetSocketAddress(port), 0);
		server.setExecutor(Executors.newFixedThreadPool(25));
		server.start();

		System.out.println("Servidor iniciado na porta " + port + "!");
	}

	public void shutdown() throws NullPointerException{
		if(server == null) {
			excecaoMalEducada();
		}else server.stop(0);
	}

	public void addListener(Handler handler) throws NullPointerException{
		if(server == null) {
			excecaoMalEducada();
		}server.createContext(handler.getRequestURI(), handler);
	}
	
	private void excecaoMalEducada() {
		throw new NullPointerException("Server não foi inicializado seu animal burro desgraçado!");
	}
}
