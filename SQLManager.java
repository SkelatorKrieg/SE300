package p1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLManager 
{
	/* ---------------------- */
	/* ----- ATTRIBUTES ----- */
	/* ---------------------- */
	
	/** Prepares a connection to the database */
	Connection connection = null;
	
	/** 
	 * Creates a statement to talk with the database with<br> 
	 * A prepared statement is not vulnerable to SQL injection, and is better
	 */
	Statement statement = null;
	
	/** Stores any data fetched from the database */
	ResultSet resultSet = null;
	
	/** Instance of User class, which adds functionality to the database */
	User user;
	
	/* -------------------------------- */
	/* ----- METHODS/CONSTRUCTORS ----- */
	/* -------------------------------- */
	
	/**
	 * Default constructor for SQLManager<br>
	 * Sets up a connection to the specified database and creates a statement for use<br>
	 * Additionally, creates a User instance that has further functionality
	 */
	public SQLManager() 
	{
		String url = "jdbc:mysql://127.0.0.1:3306/test?autoReconnect=true&useSSL=false";
		// "jdbc:mysql://127.0.0.1:3306/test" works, but Java will throw an unverified server connection error
		// the IP address starting with 127 is the local machine's, and 3306 is the default port
		String username = "admin";
		String password = "admin";
		
		// Such try
		try
		{
			// Attempt connection to the database and create an SQL statement
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.createStatement();
			
			System.out.println("Connection to database successful!");
			// Create new User instance
			user = new User();
		}
		
		// Very catch
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("Database connection failed!");
		}
	}
}
