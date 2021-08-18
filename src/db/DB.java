package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	
	private static Connection conn = null;
	
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties prop = loadProperties();
				String url = prop.getProperty("dburl");
				conn = DriverManager.getConnection(url, prop);
			}
			catch (SQLException e) {
				throw new DbException("ERROR OPEN CONNECTION cod.:01 >>> " + e.getMessage());
			}
		}
		return conn;
	}
	
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties prop = new Properties();
			prop.load(fs);
			return prop;
		}
		catch (IOException e) {
			throw new DbException("ERROR CONNECTION cod.:02>>> " + e.getMessage());
		}
	}
	
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} 
			catch (SQLException e) {
				throw new DbException("ERROR CLOSE CONNECTION cod.:03 >>> " + e.getMessage());
			}
		}
	}
	
	public static void closeConnection(Connection conn, Statement statement) {
		if (conn != null && statement != null) {
			try {
				conn.close();
				statement.close();
			} 
			catch (SQLException e) {
				throw new DbException("ERROR CLOSE CONNECTION Statement cod.:03 >>>");
			}
		}
	}
	
	public static void closeConnection(Connection conn, Statement statement, ResultSet resultSet) {
		if (conn != null && statement != null && resultSet != null) {
			try {
				conn.close();
				statement.close();
				resultSet.close();
			} 
			catch (SQLException e) {
				throw new DbException("ERROR CLOSE CONNECTION Statement AND ResultSet cod.:03 >>>");
			}
		}
	}
}
