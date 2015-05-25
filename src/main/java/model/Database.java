package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for database related tasks.
 */
public class Database {
	Logger logger = LoggerFactory.getLogger(Database.class);
	/**
	 * The database connection object.
	 */
	private Connection con;
	/**
	 * The username that will be used for the database connection.
	 */
	private String username;
	/**
	 * The password that will be used for the database connection.
	 */
	private String password;
	
	/**
	 * Constructs a {@code Database} object.
	 */
	public Database(){

	}
	
	/**
	 * Connects to the database.
	 * 
	 * @throws Exception in case the connection could not be made
	 */
	public void connect() throws Exception{
		if( con != null )
			return;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			logger.error("Nem található az illesztőprogram.");
		}

		String url = "jdbc:oracle:thin:@localhost:1521:ora11g";
		con = DriverManager.getConnection(url, this.username, this.password);
		
		logger.info("Adatbáziskapcsolat létesítés sikeres!");
	}
	
	/**
	 * Disconnects from the database.
	 */
	public void disconnect(){
		if( con != null ){
			try {
				con.close();
				if (con.isClosed()) 
					logger.info("Az adatbáziskapcsolat bezárása sikeres!");
			} catch (SQLException e) {
				logger.error("Nem sikerült bezárni az adatbáziskapcsolatot!");
			}
		}
	}
	
	/**
	 * Returns the connection.
	 * 
	 * @return {@code Connection} the connection object
	 */
	public Connection getConnection(){
		return con;
	}

	/**
	 * Configures database connection.
	 *
	 * @param username - the username that will be used for the database connection
	 * @param password - the password that will be used for the database connection
	 * 
	 * @throws Exception in case the connection could not be made after configuration
	 */
	public void configure(String username, String password) throws Exception{
		this.username = username;
		this.password = password;
		
		connect();
	}
}
