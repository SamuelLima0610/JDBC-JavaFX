package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Db {
	
	private static Connection conn;
	
	public static Connection getConnection() {
		if(conn != null) return conn;
		else {
			try {
				Properties properties = loadProperties("db.properties");
				String url = properties.getProperty("dburl");
				conn = DriverManager.getConnection(url, properties);
				return conn;
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeConnection() {
		try {
			conn.close();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	public static void closeStatement(Statement st) {
		try {
			st.close();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	public static void closeResultSet(ResultSet rst) {
		try {
			rst.close();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	private static Properties loadProperties(String path) {
		try (FileInputStream fis = new FileInputStream(path)){
			Properties properties = new Properties();
			properties.load(fis);
			return properties;
		}catch(IOException e) {
			throw new DbException(e.getMessage());
		}
	}
}
