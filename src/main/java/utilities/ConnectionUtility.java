package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
// jdbc:<database-type>://<endpoint>:<port>/<database-name>

public class ConnectionUtility {
	
	public static Connection getConnection() {
		
		Properties properties = loadProperties();
		if(properties == null) return null;
		
		try {
			return DriverManager.getConnection(
					//System.getenv(properties.getProperty("url")),
					//System.getenv(properties.getProperty("user")),
					//System.getenv(properties.getProperty("password")));
					properties.getProperty("url"),
					properties.getProperty("user"),
					properties.getProperty("password"));
		} catch(SQLException e) {
			System.out.println("Unable to connect to database.");
		}
		return null;
	}
	
	private static Properties loadProperties() {
		InputStream is = ConnectionUtility.class
				.getClassLoader()
				.getResourceAsStream("database.properties");
		
		Properties properties = new Properties();
		try {
			properties.load(is);
			return properties;
		} catch (IOException e) {
			System.out.println("Unable to load properties.");
			return null;
		}
	}
	
	
	/**
	 * Example of how properties can be used
	 */
	public static void testProperties() {
		InputStream is = ConnectionUtility.class
				.getClassLoader()
				.getResourceAsStream("test.properties");
		
		Properties properties = new Properties();
		try {
			properties.load(is);
			properties.forEach((k, v) -> System.out.println(k + ":" + v));
		} catch (IOException e) { e.printStackTrace(); }
	}
}