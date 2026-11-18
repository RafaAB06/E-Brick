package br.edu.unoesc.ebrick.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;

import br.edu.unoesc.ebrick.auth.AuthUtils;
import br.edu.unoesc.ebrick.utils.FileUtils;

public final class HttpUtils {
    private HttpUtils() {}
    
    public static final String TEXT_HTML = "text/html; charset=UTF-8";
    public static final String TEXT_CSS = "text/css; charset=UTF-8";
    public static final String TEXT_PLAIN = "text/plain; charset=UTF-8";
    public static final String TEXT_JAVASCRIPT = "text/javascript; charset=UTF-8";
    public static final String APPLICATION_JSON = "application/json; charset=UTF-8";
    public static final String IMAGE_PNG = "image/png; charset=UTF-8";
    
    public static final int SEM_CORPO = -1;
    
    public static final String PAGINA_LOGIN = "/login/index.html";
    public static final String PAGINA_SISTEMA = "/sistema/index.html";
    
    public static boolean IsGet(HttpExchange exchange) {
    	return exchange.getRequestMethod().equalsIgnoreCase("GET");
    }
    
    public static boolean IsPost(HttpExchange exchange) {
    	return exchange.getRequestMethod().equalsIgnoreCase("POST");
    }
    
    public static String getBody(HttpExchange exchange) throws IOException {
    	return new String(exchange.getRequestBody().readAllBytes());
    }
    
    public static void send(HttpExchange exchange, Resposta resposta, String type) throws IOException {
    	byte[] bytes = resposta.getMensagem().getBytes();
    	int statusCode = resposta.getCodigo();
    	
    	send(exchange, statusCode, bytes, type);
    }

    public static void send(HttpExchange exchange, int statusCode, byte[] bytes, String type) throws IOException {
    	exchange.getResponseHeaders().set("Content-Type", type);
        exchange.sendResponseHeaders(statusCode, bytes.length);

        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(bytes);
        outputStream.close();
    }
    
    public static void send(HttpExchange exchange, int statusCode, String filePath, String type) throws IOException {
    	byte[] bytes = FileUtils.getBytes(filePath);
    	
        send(exchange, statusCode, bytes, type);
    }
    
    public static void mudarLocation(HttpExchange exchange, String location) throws IOException{
    	exchange.getResponseHeaders().set("Location", location);
    	exchange.sendResponseHeaders(HttpURLConnection.HTTP_SEE_OTHER, SEM_CORPO);    	
    }
}