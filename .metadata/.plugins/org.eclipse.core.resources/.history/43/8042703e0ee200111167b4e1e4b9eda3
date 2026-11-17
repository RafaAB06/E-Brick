package br.edu.unoesc.ebrick.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public final class Server {
	private static HttpServer server;

	public static void start(int port) throws IOException {
		server = HttpServer.create(new InetSocketAddress(port), 0);
		server.setExecutor(Executors.newFixedThreadPool(50));
		server.start();

		System.out.println("Servidor iniciado na porta " + port + "!");
	}

	public static void shutdown() {
		server.stop(0);
	}

	public static void addListener(String path, HttpHandler handler) {
		server.createContext(path, handler);
	}
}
