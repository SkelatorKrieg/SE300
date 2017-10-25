package p1;

import java.sql.SQLException;
import java.sql.Timestamp;

public class User
{
	/* ---------------------- */
	/* ----- ATTRIBUTES ----- */
	/* ---------------------- */

	/** Name stored when a user is logged in */
	private String loggedInName;
	
	/** Email stored when a user is logged in */
	private String loggedInEmail;
	
	/** Password stored when a user is logged in */
	private String loggedInPassword;
	
	/** Picture URI stored when a user is logged in */
	private String pictureURI;
	
	/** Number of good ratings stored when a user is logged in */
	private int ratingGood;
	
	/** Number of bad ratings stored when a user is logged in */
	private int ratingBad;
	
	/** Number of items sold stored when a user is logged in */
	private int itemsSold;
	
	/** Admin or user rank */
	private String rank;
	
	/** Timestamp stored when a user is logged in */
	private String regDate;
	
	
	/** Variable storing whether a user is logged in or not */
	private boolean isLoggedIn;
	
	/** Variable storing the user's rank */
	private boolean isAdmin;
	
	
	/* -------------------------------- */
	/* ----- METHODS/CONSTRUCTORS ----- */
	/* -------------------------------- */

	/**
	 * Default constructor for User
	 */
	public User()
	{
		// The user created is empty (created when the application is started)
		// So initially, no one is logged in
		isLoggedIn = false;
	}
	
	
	/**
	 * Takes in an input email and password and attempts to match them to a row in the SQL database,
	 * through the class SQLManager
	 * 
	 * @param email
	 * @param password
	 */
	public void login(String email, String password)
	{
		// Set everything to null values before attempting login
		logout();
		
		// Such try
		try 
		{
			// ("SELECT name, email, password FROM users");
			MainPage.sqlm.resultSet = MainPage.sqlm.statement.executeQuery("SELECT * FROM users");
		
			while(MainPage.sqlm.resultSet.next())
			{
				if ((email.equals(MainPage.sqlm.resultSet.getString("email"))) && (password.equals(MainPage.sqlm.resultSet.getString("password"))))
				{
					loggedInName = MainPage.sqlm.resultSet.getString("name");
					loggedInEmail = MainPage.sqlm.resultSet.getString("email");
					loggedInPassword = MainPage.sqlm.resultSet.getString("password");
					pictureURI = MainPage.sqlm.resultSet.getString("picture");
					ratingGood = MainPage.sqlm.resultSet.getInt("ratinggood");
					ratingBad = MainPage.sqlm.resultSet.getInt("ratingbad");
					itemsSold = MainPage.sqlm.resultSet.getInt("itemssold");
					rank = MainPage.sqlm.resultSet.getString("rank");
					regDate = MainPage.sqlm.resultSet.getString("reg_date");
				
					isLoggedIn = true;
					System.out.println("Login successful, " + loggedInName + "!");
					
					if (MainPage.sqlm.resultSet.getString("rank").equals("Admin"))
					{
						isAdmin = true;
					}
						
					else
					{
						isAdmin = false;
					}
				}
			}
		}
		
		// Very catch
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.err.println("Failed to find passwords from database!");
		}
	}
	
	
	/**
	 * Method to log the user out<br>
	 * Simply sets all Java-stored user-info to null or default values<br>
	 * Does not touch the database in any way
	 */
	public void logout()
	{
		loggedInName = null;
	    loggedInEmail = null;
	    loggedInPassword = null;
	    pictureURI = null;
		ratingGood = 0;
		ratingBad = 0;
		itemsSold = 0;
		rank = null;
		regDate = null; 
		
		isLoggedIn = false;
	}
	
	
	/**
	 * Provides functionality for creating a new user and adding it to the connected database<br>
	 * 
	 * @param name
	 * @param email
	 * @param password
	 * @param pictureURI
	 */
	public void createNewUser(String name, String email, String password, String pictureURI)
	{
		// Set everything to null values before attempting login
		logout();
		
		// Grab a timestamp to store
		// TODO
		// Parse it for the database, or just keep it, doesn't matter
		Timestamp ts= new Timestamp(System.currentTimeMillis());
		String reg_date = ts.toString();
		
		// Set new id to use initially to 0
		int id = 0;
		
		// Such try
		try 
		{
			// Set a new id for the user by iterating through all of them and adding one to the last one found
			MainPage.sqlm.resultSet = MainPage.sqlm.statement.executeQuery("SELECT id FROM users");
			while(MainPage.sqlm.resultSet.next())
			{
				id = MainPage.sqlm.resultSet.getInt("id");
			}
			id++;
			
			// TODO update GUI
			// Iterate over every email in the database and check if the user-input email is already in use
			MainPage.sqlm.resultSet = MainPage.sqlm.statement.executeQuery("SELECT email FROM users");
			while(MainPage.sqlm.resultSet.next())
			{
				// If the input email is already in use, handle it
				if (email.equals(MainPage.sqlm.resultSet.getString("email")))
				{
					System.out.println("Email is already in use!");
					return;
				}
			}
			
			// If all is well, create a new user
			// Format is a little strange for entering non-concrete values (i.e. variables) into the database, but it's not terrible
			// For String variables: '"+string+"'
			// For int variables: "+int+"
			MainPage.sqlm.statement.executeUpdate("INSERT INTO users (id, name, email, password, picture, ratinggood, ratingbad, itemssold, rank, reg_date) "
	        + "VALUE ("+id+", '"+name+"', '"+email+"', '"+password+"', '"+pictureURI+"', 0, 0, 0, 'User', '"+reg_date+"')");
		} 
		
		// Very catch
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.err.println("Could not create new user!");
		}
	}
	
	
	/**
	 * Allows a user to create a new item listing<br>
	 */
	//test
	//public void createListing()
	public void createListing(String title, String description, int price, String category, String imageURI, String productcondition, 
    String courseprefix, int year, String model, int miles, int bathroom, int bedroom)
	{
		// Grab a timestamp to put into the database
		Timestamp ts= new Timestamp(System.currentTimeMillis());
		String timestamp = ts.toString();
		
		// Set a new id to 0
		int id = 0;
		
		// Such try
		try 
		{
			// Set a new id for the user by iterating through all of them and adding one to the last one found
			MainPage.sqlm.resultSet = MainPage.sqlm.statement.executeQuery("SELECT id FROM listings");
			while(MainPage.sqlm.resultSet.next())
			{
				id = MainPage.sqlm.resultSet.getInt("id");
			}
			id++;
			
			MainPage.sqlm.statement.executeUpdate("INSERT INTO listings (id, title, description, price, category, image, userid, productcondition, courseprefix, year, model, miles, bedroom, bathroom, timestamp) "
			+ "VALUE ("+id+", '"+title+"', '"+description+"', "+price+", '"+category+"', '"+imageURI+"', '"+loggedInName+"', '"+productcondition+"','"+courseprefix+"', "+year+", '"+model+"', "+miles+", "+bedroom+", "+bathroom+", '"+timestamp+"')");
			
			// test
			//MainPage.sqlm.statement.executeUpdate("INSERT INTO listings (id, title, description, price, category, image, userid, productcondition, courseprefix, year, model, miles, bedroom, bathroom, timestamp) "
			//+ "VALUE (1, 'yeetmachine', 'succsucc4u', 69, 'Vehicles', 'C:hello', 'tp', 'new', 'SEX101', 6969, 'goodone', 69, 2, 2, '"+timestamp+"')");
		} 
		
		// Very catch
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.err.println("Could not create a new listing in the database!");
		}
	}
	
	
	public boolean isLoggedIn() 
	{
		return isLoggedIn;
	}
	
	
	public boolean isAdmin()
	{
		return isAdmin;
	}
	
	
	public void setLoggedIn(boolean isLoggedIn)
	{
		this.isLoggedIn = isLoggedIn;
	}
	
	public String getLoggedInEmail() 
	{
		return loggedInEmail;
	}


	public String getName() 
	{
		return loggedInName;
	}


	public String getLoggedInPassword() 
	{
		return loggedInPassword;
	}
	
	
	/**
	 * Getter for the user's profile picture URI<br>
	 * In the URL/URI world, forward and backslash count as the same thing, apparently
	 * 
	 * @return pictureURI
	 */
	public String getPictureURI() 
	{
		return pictureURI;
	}


	public int getRatingGood() 
	{
		return ratingGood;
	}


	public int getRatingBad() 
	{
		return ratingBad;
	}


	public int getItemsSold()
	{
		return itemsSold;
	}


	public String getRegDate() 
	{
		return regDate;
	}
}
