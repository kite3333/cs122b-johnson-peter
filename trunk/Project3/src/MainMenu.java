import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Scanner;


public class MainMenu {

private String username;
private String password;
private boolean loggedIn = false;
private boolean exit = false;
private Scanner in = new Scanner(System.in);
private Connection connection;

//mySQL Error Codes
private static final int USER_DNE = 1044;
private static final int BAD_PASSWORD = 1045;
private static final int DB_DNE = 1049;


	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, InterruptedException
	{
		MainMenu main = new MainMenu();
		while (!main.exit) {
			main.logIn();
			if (main.loggedIn) {
				main.menu();
			}
		}
		System.out.println("System Exited.");
       
    }
	
	
	public void logIn()
	{
		
        try {
    		// Incorporate mySQL driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e1) {
			System.err.print("ERROR: The JDBC object could not be instantiated.");
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			System.err.print("ERROR: Access Denied to mySQL. Check Environment.");
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			System.err.print("ERROR: Class does not exist.");
			e1.printStackTrace();
		}

        // Connect to the database
        while (!loggedIn) {
        	//ASSIGNMENT DESCRIPTION - When this program is run, the user is asked for the the user name and the user password 
        	//(the database user login info not the password in the above schema) . 
    		System.out.println("Please enter your username or type \"exit\" to quit (CASE SENSITIVE):");
    		username = in.nextLine();
        	//ASSIGNMENT DESCRIPTION - Allow a way for the employee to exit easily.
        	if (username.equals("exit")) {
        		exit = true;
        		return;
        	}
        	
    		System.out.println("Please enter your password for the database:");
    		password = in.nextLine();
    		
    		//ASSIGNMENT DESCRIPTION - If all is well, the employee is granted access (and a message to that effect appears 
    		//on the screen)
	        try {
			        connection = DriverManager.getConnection("jdbc:mysql:///moviedb", username, password);
			        //Assuming no exception is thrown
			        System.out.println("Access granted. Welcome " + username + ".");
			        loggedIn = true;
	        }
	        catch(SQLException e) {; 
	        //ASSIGNMENT DESCRIPTION - if access is not allowed, it says why (e.g., the database is not present, the password is wrong).
	        	System.out.print("ACCESS DENIED: ");
		        switch (e.getErrorCode()) {
			        case USER_DNE: //Do same thing as case: BAD_PASSWORD;
			        case BAD_PASSWORD: System.out.println("Bad Username/Password."); break;
			        case DB_DNE: System.out.println("Database not found/connected."); break;
			        default: 
			        {
			        	System.out.println("Error Unknown");
			        	e.printStackTrace();
			        }
		        }
	        	System.out.println("\n");
	        }
        }
	}
	
	private void printResults(ResultSet result) throws SQLException {
		if (result.next()) {
			result.beforeFirst();
			while (result.next()) {
			    System.out.println("Movie Id: " + result.getInt(1));
			    System.out.println("Title: " + result.getString(2));
			    System.out.println("Year: " + result.getInt(3));
			    System.out.println("Director: " + result.getString(4));
			    System.out.println("Banner URL: " + result.getString(5));
			    System.out.println("Trailer URL: " + result.getString(6));
			    System.out.println();
			}
		} else {
			System.out.println("No Results.");
		}
	}
	
	private void searchActor() throws SQLException {
		boolean done = false;
		while (!done) {
			System.out.println("Enter 1 to query actor by FIRST name.");
			System.out.println("      2 to query actor by LAST name.");
			System.out.println("      3 to query actor by FIRST and LAST name.");
			System.out.println("      4 to query actor by ID.");
			System.out.println("   or 5 to return to the main menu");
			int userChoice = in.nextInt();
			in.nextLine(); //Remove leftover return char
			switch (userChoice) {
				case 1: {
					System.out.println("Enter the first name: ");
	    			String firstName = in.next();
	                Statement select = connection.createStatement();
	                ResultSet result = select.executeQuery("SELECT movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url from movies, stars, stars_in_movies " +
	                		"WHERE stars.first_name =" + '"' + firstName + '"' + "AND stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id;");
	                printResults(result);
				}
                done = true;
				break;
				case 2: {
					System.out.print("Enter the last name: ");
					String lastName = in.next();
	    			System.out.println();
	                Statement select = connection.createStatement();
	                ResultSet result = select.executeQuery("SELECT movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url from movies, stars, stars_in_movies " +
	                		"WHERE stars.last_name =" + '"' + lastName + '"' + "AND stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id;");
	                printResults(result);
				}
                done = true;
				break;
				case 3: {
					System.out.print("Enter the first name: ");
					String firstName = in.next();
	    			System.out.println();
	    			System.out.println("Enter the last name here:");
	    			String lastName = in.next();
	    			System.out.println();
	                Statement select = connection.createStatement();
	                ResultSet result = select.executeQuery("SELECT movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url from movies, stars, stars_in_movies " +
	                		"WHERE stars.first_name =" + '"' + firstName + '"' + "AND stars.last_name =" + '"' + lastName + '"' + "AND stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id;");
	                printResults(result);
				}
                done = true;
				break;
				case 4: {
					System.out.println("Enter the id here:");
	                Statement select = connection.createStatement();
	                ResultSet result = select.executeQuery("SELECT movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url from movies, stars, stars_in_movies " +
	                		"WHERE stars.id =" + '"' + in.nextInt() + '"' + "AND stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id;");
	    			in.nextLine(); //Remove leftover return char
	    			printResults(result);
				}
                done = true;
				break;
				
				case 5: 
	                return;
				
				default:
					System.out.println("\nInvalid Option.\n");
			}
		}
	}
	
	public void menu() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException
	{
		int userInput = 0;
        while (userInput != 8)
        {
        	System.out.println("\n\n----MAIN MENU----");
        	System.out.println("-----------------\n");
        	System.out.println("1. Print to screen the movies featuring a given actor.");
        	System.out.println("2. Add a new movie into the database.");
        	System.out.println("3. Add a new actor into the database.");
        	System.out.println("4. Add a new customer into the database.");
        	System.out.println("5. Delete a customer from the database.");
        	System.out.println("6. Print to screen the database metadata.");
        	System.out.println("7. Enter a valid SELECT/UPDATE/INSERT/DELETE SQL command.");
        	System.out.println("8. Generate a report on database issues.");
        	System.out.println("9. Return to login screen.");
        	System.out.println("10. Exit the program.");
        	
        	userInput = in.nextInt();
        	in.nextLine();
        	switch (userInput) {
        		case 1: // Search for an actor
            		searchActor();
            		break;
        		case 2: {// Add a movie
        			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        			String title = null;
        			String director = null;
        			String year = null;
        			String firstName = null;
        			String lastName = null;
        			String genre = null;
        			int result = 3;
        			try {
	        			System.out.println("Enter the movie's name: ");
	        			title = reader.readLine();
	        			System.out.println("Enter the director's name: ");
	        			director = reader.readLine();
	        			System.out.println("Enter the year the movie was released: ");
	        			year = reader.readLine();
	        			System.out.println("Enter the primary star's first name. Leave blank if the actor has only one name.");
	        			firstName = reader.readLine();
	        			if (firstName == null) {
	        				firstName = "";
	        			}
	        			System.out.println("Enter the primary star's last name or only name.");
	        			lastName = reader.readLine();
	        			System.out.println("Enter the movie's genre.");
	        			genre = reader.readLine();
	        			CallableStatement call = connection.prepareCall("{CALL add_movie(?, ?, ?, ?, ?, ?, ?)}");
	        			call.registerOutParameter(7, Types.INTEGER);
	        			call.setString("m_title", title);
	        			call.setString("m_year", year);
	        			call.setString("m_director", director);
	        			call.setString("genre", genre);
	        			call.setString("star_fname", firstName);
	        			call.setString("star_lname", lastName);
	        			call.execute();
	        			result = call.getInt(7);
	        			System.out.println("Result Code: " + result);
	        			switch (result) {
	        				case 0: System.out.println("Movie insertion successful."); break;
	        				case 1: System.out.println("Movie insertion failed. Invalid input."); break;
	        				case 2: System.out.println("Movie insertion failed. Movie already exists."); break;
	        				default: System.out.println("Movie insertion failed. Unknown Error.");
	        			}
        			} catch (IOException e) {
        				System.out.println("System input reader failed. Returning to the main menu.");
        			} catch (SQLException e) {
        				System.err.println("Database query failed. Returning to the main menu.");
        			}
        			break;
        		}
        		case 3: { // Add an actor
        			System.out.println("Enter the first name of the star here (if star has one name only, type blank to leave empty):");
        			String firstName = in.next();
        			if(firstName.equals("blank"))
        			{
        				firstName = "";
        			}
        			System.out.println("Enter the last name of the star here (or if the star only has one name, enter it here):");
        			String lastName = in.next();
        			System.out.println("Enter the birthdate of the star here (type blank to leave empty):");
        			String dob = in.next();
        			System.out.println("Enter the photo url of the star here (type blank to leave empty):");
        			String photo_url = in.next();
        			if(photo_url.equals("blank"))
        			{
        				photo_url = "";
        			}
                    Statement insert = connection.createStatement();

                    if(dob.equals("blank"))
                    {
                    	insert.executeUpdate("INSERT into stars(first_name, last_name, photo_url) " +
                        		"VALUES (" + '"' + firstName + '"'
                        		+ "," + '"' + lastName + '"'
                        		+ "," + '"' + photo_url + '"' +");");
                    }
                    else
                    {insert.executeUpdate("INSERT into stars(first_name, last_name, dob, photo_url) " +
                    		"VALUES (" + '"' + firstName + '"'
                    		+ "," + '"' + lastName + '"'
                    		+ "," + '"' + dob + '"'
                    		+ "," + '"' + photo_url + '"' +");");
                    }
                    
                    System.out.println("Actor has been added");
        			}
                	break;
        		case 4: { // Add a customer
	        		System.out.println("Enter the first name of the customer here (if customer has one name only, type blank to leave empty):");
	        		String firstName = in.next();
	    			if(firstName.equals("blank"))
	    			{
	    				firstName = "";
	    			}
	    			System.out.println("Enter the last name of the customer here (or if the star only has one name, enter it here):");
	    			String lastName = in.next();
	    			System.out.println("Enter the credit card number of the customer here:");
	    			String credit_card = in.next();
	
	    			Statement select = connection.createStatement();
	
	                ResultSet result = select.executeQuery("SELECT * FROM creditcards " +
	                		"WHERE " + credit_card + "= creditcards.id;");
	
	                if(!result.next())
	                {
	                	System.out.println("Invalid credit card, returning to main menu...");
	                }
	                
	    			System.out.println("Enter the address of the customer here:");
	    			String address = in.nextLine();
	    			System.out.println("Enter the email of the customer here:");
	    			String email = in.nextLine();
	    			System.out.println("Enter the password of the customer here:");
	    			String password = in.nextLine();
	                Statement insert = connection.createStatement();
	
	                insert.executeUpdate("INSERT into customers(first_name, last_name, cc_id, address, email, password) " +
	                		"VALUES (" + '"' + firstName + '"'
	                		+ "," + '"' + lastName + '"'
	                		+ "," + '"' + credit_card + '"'
	                		+ "," + '"' + address + '"' 
	                		+ "," + '"' + email + '"'
	                		+ "," + '"' + password + '"' + ");");
	        
	                System.out.println("Query has been processed");
        		}
        		break;
	        	case 5: { // Delete a customer
		    		System.out.println("Please enter a selection from below:");
		        	System.out.println("1. Delete customer by first and last name");
		    		System.out.println("2. Delete customer by id");
		    		int userSelection = in.nextInt();
		    		in.nextLine();
		    		if(userSelection == 1)
		    		{
		    			System.out.println("Enter the customer's first name:");
		    			String firstName = in.next();
		    			System.out.println("Enter the customer's last name:");
		    			String lastName = in.next();
		    			
		                Statement update = connection.createStatement();
		                update.executeUpdate("delete from customers where first_name = " 
		                		+ '"' + firstName + '"' + " AND last_name = " + '"' + lastName + '"' + ";");
		                
		                System.out.println("Query has been processed.");
		    		}
		    		if(userSelection == 2)
		    		{
		    			System.out.println("Enter the customer's id:");
		    			int id = in.nextInt();
			    		in.nextLine();
		    			
		    			Statement update = connection.createStatement();
		                update.executeUpdate("delete from customers where id = " 
		                		+ id + ";");
		                
		                System.out.println("Query has been processed.");
		    		}
		    		
		        }
		        break;
	        	case 6: { // Print metadata for tables
		        	System.out.println("Please make a selection below:");
		        	System.out.println("1. Get metadata for a specific table");
		        	System.out.println("2. Get all metadata for the database");
		        	int selection = in.nextInt();
		        	
		        	if(selection == 1)
		        	{System.out.println("Enter the table name:");
		        	String databaseName = in.next();
		        	
		            Statement select = connection.createStatement();
		            ResultSet result = select.executeQuery("Select * from " + databaseName);
		            
		            System.out.println("Results:");
		            ResultSetMetaData metadata = result.getMetaData();
		            System.out.println("There are " + metadata.getColumnCount() + " columns");
		            
		            for (int i = 1; i <= metadata.getColumnCount(); i++)
//		                System.out.println("Type of column "+ i + " is " + metadata.getColumnTypeName(i));
		            	System.out.println(metadata.getColumnName(i) + " is " + metadata.getColumnTypeName(i));
		        	}
		        	
		        	if(selection == 2)
		        	{
		        		DatabaseMetaData dbmd = connection.getMetaData();
		        	    // Specify the type of object; in this case we want tables
		        	    String[] types = {"TABLE"};
		        	    ResultSet resultSet = dbmd.getTables(null, null, "%", types);
		
		        	    // Get the table names
		        	    while (resultSet.next()) {
		        	        // Get the table name
		        	        String tableName = resultSet.getString(3);
		                    Statement select = connection.createStatement();
		                    
		                    ResultSet result = select.executeQuery("Select * from " + tableName);
		                    
		                    System.out.println();
		                    System.out.println("---" + tableName + "---");
		                    System.out.println("The results of the query");
		                    ResultSetMetaData metadata = result.getMetaData();
		                    System.out.println("There are " + metadata.getColumnCount() + " columns");
		                    
		                    for (int i = 1; i <= metadata.getColumnCount(); i++)
//		                        System.out.println("Type of column "+ i + " is " + metadata.getColumnTypeName(i));
		                    	System.out.println(metadata.getColumnName(i) + " is " + metadata.getColumnTypeName(i));
		        	        
		        	    }
		        	}
		        }
	        	break;
	        	case 7: { //Enter SQL command
		        	System.out.println("Please make a selection below:");
		        	System.out.println("1. Make a valid SELECT SQL command");
		        	System.out.println("2. Make a valid UPDATE SQL command");
		        	System.out.println("3. Make a valid INSERT SQL command");
		        	System.out.println("4. Make a valid DELETE SQL command");
		        	
		        	int selection = in.nextInt();
		        	in.nextLine();
		        	if(selection == 1)
		        	{
		        		System.out.println("Enter your SELECT statement below:");
		        		String selectQuery = in.nextLine();
		        		
		        		Statement select = connection.createStatement();
	        			ResultSet result = null;
		        		try {
		        			result = select.executeQuery(selectQuery);
		        		}
		        		catch (SQLException e) {
		        			System.out.println("\nInvalid query. Returning to main menu...\n\n");
		        			break;
		        		}
		                ResultSetMetaData metadata = result.getMetaData();
		                while(result.next())
		                {
		                	for (int i = 1; i <= metadata.getColumnCount(); i++)
//		                        System.out.println("Type of column "+ i + " is " + metadata.getColumnTypeName(i));
		                  	{
		                		System.out.println(metadata.getColumnName(i) + " = " + result.getString(metadata.getColumnName(i)));
		                	}
		                }
		        	}
		        	if(selection == 2)
		        	{
		        		System.out.println("Enter your UPDATE statement below:");
		        		String updateQuery = in.nextLine();
		        		
		    	        PreparedStatement updateStars = connection.prepareStatement(updateQuery);
		    	        
		    	        int rows = 0;
		    	        try {
		    	        	rows = updateStars.executeUpdate();
		    	        }
		    	        catch (SQLException e){
		        			System.out.println("\nInvalid query. Returning to main menu...\n\n");
		        			break;
		    	        }
		    	        System.out.printf("%d row(s) updated!", rows);
		        	}
		        	if(selection == 3)
		        	{
		        		System.out.println("Enter your INSERT statement below:");
		        		String insertQuery = in.nextLine();
		        		
		                Statement insert = connection.createStatement();
		
		                int rows = insert.executeUpdate(insertQuery);
		                System.out.printf("%d row(s) updated!", rows);
		                
		                //wait 2 seconds
		                Thread.sleep(2000L);
		        	}
		        	if(selection == 4)
		        	{
		        		System.out.println("Enter your DELETE statement below:");
		        		String deleteQuery = in.nextLine();
		        		
		                Statement update = connection.createStatement();
		                int rows = 0;
		                try {
		                	rows = update.executeUpdate(deleteQuery);
		                }
		    	        catch (SQLException e){
		        			System.out.println("\nInvalid query. Returning to main menu...\n\n");
		        			break;
		    	        }
		                System.out.printf("%d row(s) updated!", rows);
		                
		                //wait 2 seconds
		                Thread.sleep(2000L);
		        	}
		        	
		        }
	        	break;
	        	case 8: ProblemReport report = new ProblemReport(connection);
	        		System.out.println("Report Address: " + report.generateReport());
	        		break;
	        	case 9: loggedIn = false; return; //Log out
	        	case 10: exit = true; return; // Exit the system
	        	default: {
	        		System.out.println("Invalid Option");
	        	}
	        }        
        }
	}
}
