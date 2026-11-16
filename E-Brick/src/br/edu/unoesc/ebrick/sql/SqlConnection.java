package br.edu.unoesc.ebrick.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class SqlConnection {
	private static Connection connection;
	
	public static void connect(String url, String user, String password) throws SQLException{
		connection = DriverManager.getConnection(url, user, password);
	}
	
	public static Connection getConnetion() {
		return connection;
	}
}
