package br.edu.unoesc.ebrick.auth;

import com.sun.net.httpserver.HttpExchange;

import br.edu.unoesc.ebrick.auth.entities.Permissao;
import br.edu.unoesc.ebrick.auth.entities.Usuario;
import br.edu.unoesc.ebrick.http.Cookies;

public final class AuthUtils {
	private AuthUtils() {}
	
	public static Usuario getUsuarioAutenticado(HttpExchange exchange) {
	    String token = Cookies.getCookieSessao(exchange);

	    if (!Sessoes.getInstancia().sessaoValida(token)) {
	        return null;
	    }

	    return Sessoes.getInstancia().getUsuarioPorToken(token);
	}

	public static boolean autenticado(HttpExchange exchange) {
	    return getUsuarioAutenticado(exchange) != null;
	}

	public static boolean autorizado(HttpExchange exchange, Permissao permissao) {
	    Usuario user = getUsuarioAutenticado(exchange);

	    return user != null && user.temPermissao(permissao);
	}
}
