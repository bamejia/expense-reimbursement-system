package proj.p1.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	private static Connection connection;
	
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); //registering Oracle Driver
		}
		catch(ClassNotFoundException ex) {
			ex.printStackTrace();
			System.exit(1);
		}

		
		String url = System.getenv("DB_URL"); // best not to hard code in your db credentials ! 
		String username = System.getenv("DB_USERNAME");
		String password = System.getenv("DB_PASSWORD");
		if(connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(url, username, password);
		}
		return connection;
	}

}
