package br.edu.unoesc.ebrick.http.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import br.edu.unoesc.ebrick.auth.entities.Permissao;
import br.edu.unoesc.ebrick.entities.clientes.Cliente;
import br.edu.unoesc.ebrick.entities.clientes.ClienteFactory;
import br.edu.unoesc.ebrick.entities.clientes.ClienteRepository;
import br.edu.unoesc.ebrick.http.HttpUtils;
import br.edu.unoesc.ebrick.http.Resposta;
import br.edu.unoesc.ebrick.sql.SqlConnection;

public class ClientesHandler extends HandlerAutorizado{
	private final String BUSCAR_CLIENTES = "buscar_clientes";
	private final String ATUALIZAR_CLIENTE = "atualizar_cliente";
	private final String ADICIONAR_CLIENTE = "adicionar_cliente";
	private final String EXCLUIR_CLIENTE = "excluir_cliente";
	
	public ClientesHandler(String requestPath, Permissao permissao) {
		super(requestPath, permissao);
	}

	@Override
	protected void handleAutorizado(HttpExchange exchange) throws IOException{
		super.handleAutorizado(exchange);				
		if(HttpUtils.IsPost(exchange)) {
			String json = new String(exchange.getRequestBody().readAllBytes());
			
			try {
				JSONObject jsonObject = new JSONObject(json);
				String request = exchange.getRequestURI().toString();
				
				if(request.endsWith(BUSCAR_CLIENTES)) {
					buscarClientes(exchange, jsonObject);
				}else if(request.endsWith(ATUALIZAR_CLIENTE)) {
					atualizarCliente(exchange, jsonObject);
				}else if(request.endsWith(ADICIONAR_CLIENTE)) {
					adicionarCliente(exchange, jsonObject);
				}else if(request.endsWith(EXCLUIR_CLIENTE)) {
					excluirCliente(exchange, jsonObject);
				}
			}catch(Exception e){
				e.printStackTrace();
				
				Resposta resposta = new Resposta(HttpURLConnection.HTTP_BAD_REQUEST, "Erro ao validar dados");
				HttpUtils.send(exchange, resposta, HttpUtils.TEXT_PLAIN);
			}			
		}
	}

	private void buscarClientes(HttpExchange exchange, JSONObject jsonObject) throws SQLException, IOException {
		String pesquisa = jsonObject.getString("pesquisa");
		String ordem = jsonObject.getString("ordem");
		int inicio = jsonObject.getInt("inicio");
		int qtd = jsonObject.getInt("qtd");
		
		List<Cliente> clientes = ClienteRepository.getInstancia().getClientes(pesquisa, ordem, inicio, qtd);
		JSONArray array = new JSONArray();
		for(Cliente cliente : clientes) {
			array.put(cliente.toJsonObject());
		}
		byte[] bytes = array.toString().getBytes();
		HttpUtils.send(exchange, HttpURLConnection.HTTP_OK, bytes, HttpUtils.APPLICATION_JSON);
	}
	
	private void atualizarCliente(HttpExchange exchange, JSONObject jsonObject) throws SQLException, IOException{
		Resposta resposta = Resposta.PADRAO;
		try {
			Cliente cliente = ClienteFactory.fromJsonObject(jsonObject);
			ClienteRepository.getInstancia().update(cliente);
		}catch(IllegalArgumentException e) {
			resposta = new Resposta(HttpURLConnection.HTTP_BAD_REQUEST, e.getMessage());
		}
		
		HttpUtils.send(exchange, resposta, HttpUtils.TEXT_PLAIN);
	}
	
	private void adicionarCliente(HttpExchange exchange, JSONObject jsonObject) throws SQLException, IOException {
		Resposta resposta = Resposta.PADRAO;
		
		try {
			Cliente cliente = ClienteFactory.fromJsonObject(jsonObject);
			ClienteRepository.getInstancia().insert(cliente);
		}catch(IllegalArgumentException e) {
			resposta = new Resposta(HttpURLConnection.HTTP_BAD_REQUEST, e.getMessage());
		}
		HttpUtils.send(exchange, resposta, HttpUtils.TEXT_PLAIN);
	}
	
	private void excluirCliente(HttpExchange exchange, JSONObject jsonObject) throws SQLException, IOException {
		long id = jsonObject.optLong("id");
		
		String sql = "CALL excluir_cliente(?);";
		PreparedStatement statement = SqlConnection.getConnetion().prepareStatement(sql);
		statement.setLong(1, id);
		statement.execute();
		
		HttpUtils.send(exchange, Resposta.PADRAO, HttpUtils.TEXT_PLAIN);
	}
}
