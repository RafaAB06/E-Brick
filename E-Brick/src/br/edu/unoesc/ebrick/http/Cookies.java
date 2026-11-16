package br.edu.unoesc.ebrick.http;

import java.util.List;

import com.sun.net.httpserver.HttpExchange;

public final class Cookies {
	public static void addCookieSessao(HttpExchange exchange, String cookieValue) {
		String cookie = String.format("%s=%s", "sessionid", cookieValue);
		exchange.getResponseHeaders().add("Set-Cookie", cookie);
	}
	
	public static String getCookieSessao(HttpExchange exchange) {
		String token = null;	
		List<String> cookies = exchange.getRequestHeaders().get("Cookie");
		
		if((cookies != null)) {
			for(String cookie : cookies) {
				String[] strs = cookie.split("=");
				if(strs.length == 2 && strs[0].equals("sessionid")) {
					token = strs[1];
					break;
				}
			}
		}
	
		return token;
	}
}
