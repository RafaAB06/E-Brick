package br.edu.unoesc.ebrick.http.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import org.json.JSONArray;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import br.edu.unoesc.ebrick.auth.AuthUtils;
import br.edu.unoesc.ebrick.auth.entities.Permissao;
import br.edu.unoesc.ebrick.auth.entities.Usuario;
import br.edu.unoesc.ebrick.http.HttpUtils;
import br.edu.unoesc.ebrick.http.Server;

public class SistemaHandler extends HandlerAutenticado{	
	public SistemaHandler(String requestPath) {
		super(requestPath);
		Server.addListener(abasHandler);
		
		//List<Permissao> permissoes = Permissoes.getInstancia().getTodasPermissoes();
		//for(Permissao permissao : permissoes) {
			//String url = String.format("/sistema/%s", permissao.getId());
			//Server.addListener(url, new HandlerAba(permissao));
		//}
	}
	
	@Override
	protected void handleAutenticado(HttpExchange exchange) throws IOException {
		super.handleAutenticado(exchange);
		
		if(HttpUtils.IsGet(exchange)) {
			HttpUtils.send(exchange, HttpURLConnection.HTTP_OK, "web/sistema/index.html", HttpUtils.TEXT_HTML);
		}
	}
	
	private Handler abasHandler = new Handler("/sistema/abas") {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			boolean get = HttpUtils.IsGet(exchange);
			if(!get) return;
			
			Usuario user = AuthUtils.getUsuarioAutenticado(exchange);
			if(user == null) return;

			List<Permissao> permissoes = user.getPermissoes();			
			JSONArray array = new JSONArray();
			
			for (Permissao permissao : permissoes) {
			    array.put(permissao.toJsonObject());
			}
			
			byte[] bytes = array.toString().getBytes();
			HttpUtils.send(exchange, HttpURLConnection.HTTP_OK, bytes, HttpUtils.APPLICATION_JSON);
		}
	};
}
