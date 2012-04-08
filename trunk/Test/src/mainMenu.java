import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class mainMenu {

static String username;
static String password;
static boolean successfullyLoggedIn = false;

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, InterruptedException
	{
		logIn();
       
    }
	
	
	public static void logIn() throws InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException
	{
		Scanner in = new Scanner(System.in);
		
//		When this program is run, the user is asked for the the user name and the user password 
//		(the database user login info not the password in the above schema) . 
		System.out.println("Please enter your username for the database:");
		
		username = in.nextLine();
		
		System.out.println("Please enter your password for the database:");
		password = in.nextLine();
		
//		If all is well, the employee is granted access (and a message to that effect appears 
//		on the screen); if access is not allowed, it says why (e.g., the database is 
//		not present, the password is wrong). Allow a way for the employee to 
//		exit easily.
		
		// Incorporate mySQL driver
        Class.forName("com.mysql.jdbc.Driver").newInstance();

         // Connect to the test database
        // Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb","mytestuser", "mypassword");
        try{
        Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb",username, password);
        System.out.println("Access granted! Welcome " + username + ".");
        successfullyLoggedIn = true;
        Menu();
        }
        catch(SQLException e){
        System.out.println("Access denied! Please verify credentials.");
        e.printStackTrace();
        successfullyLoggedIn = false;
        logIn();
        }
	}
	
	public static void Menu() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException
	{
		Scanner in = new Scanner(System.in);
		
        if(successfullyLoggedIn == true)
        {
        	System.out.println("Here is the menu");
        	System.out.println("------------------");
        	System.out.println("1. Print out (to the screen) the movies featuring a given star.");
        	System.out.println("2. Insert a new star into the database.");
        	System.out.println("3. Insert a customer into the database.");
        	System.out.println("4. Delete a customer from the database.");
        	System.out.println("5. Provide the metadata of the database");
        	System.out.println("6. Enter a valid SELECT/UPDATE/INSERT/DELETE SQL command");
        	System.out.println("7. Exit the menu (and return to the get-the-database/user/password state).");
        	System.out.println("8. Exit the program.");
        	
        	int userinput = in.nextInt();
        	if(userinput == 1)
        	{
        		System.out.println("Enter 1 to query star by FIRST name.");
        		System.out.println("Enter 2 to query star by LAST name.");
        		System.out.println("Enter 3 to query star by FIRST and LAST name.");
        		System.out.println("Enter 4 to query star by ID.");
        		
        		Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb",username, password);
        		int oneInput = in.nextInt();
        		if(oneInput == 1)
        		{
        			System.out.println("Enter the first name here:");
        			String queryFirstNameOnly = in.next();
                    Statement select = connection.createStatement();
                    ResultSet result = select.executeQuery("SELECT movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url from movies, stars, stars_in_movies " +
                    		"WHERE stars.first_name =" + '"' + queryFirstNameOnly + '"' + "AND stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id;");

                	System.out.println("The results of the query");
                    while (result.next())
                    {
                            System.out.println("Id = " + result.getInt(1));
                            System.out.println("Title = " + result.getString(2));
                            System.out.println("Year = " + result.getInt(3));
                            System.out.println("Director = " + result.getString(4));
                            System.out.println("Banner_URL = " + result.getString(5));
                            System.out.println("Trailer_URL = " + result.getString(6));
                            System.out.println();
                    }
                    Menu();
        		}
        		if(oneInput == 2)
        		{
        			System.out.println("Enter the last name here:");
        			String queryLastNameOnly = in.next();
                    Statement select = connection.createStatement();
                    ResultSet result = select.executeQuery("SELECT movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url from movies, stars, stars_in_movies " +
                    		"WHERE stars.last_name =" + '"' + queryLastNameOnly + '"' + "AND stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id;");

                	System.out.println("The results of the query");
                    while (result.next())
                    {
                            System.out.println("Id = " + result.getInt(1));
                            System.out.println("Title = " + result.getString(2));
                            System.out.println("Year = " + result.getInt(3));
                            System.out.println("Director = " + result.getString(4));
                            System.out.println("Banner_URL = " + result.getString(5));
                            System.out.println("Trailer_URL = " + result.getString(6));
                            System.out.println();
                    }
                    Menu();
        		}
        		if(oneInput == 3)
        		{
        			System.out.println("Enter the first name here:");
        			String queryFirstNameOnly = in.next();
        			System.out.println("Enter the last name here:");
        			String queryLastNameOnly = in.next();
                    Statement select = connection.createStatement();
                    ResultSet result = select.executeQuery("SELECT movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url from movies, stars, stars_in_movies " +
                    		"WHERE stars.first_name =" + '"' + queryFirstNameOnly + '"' + "AND stars.last_name =" + '"' + queryLastNameOnly + '"' + "AND stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id;");

                	System.out.println("The results of the query");
                    while (result.next())
                    {
                            System.out.println("Id = " + result.getInt(1));
                            System.out.println("Title = " + result.getString(2));
                            System.out.println("Year = " + result.getInt(3));
                            System.out.println("Director = " + result.getString(4));
                            System.out.println("Banner_URL = " + result.getString(5));
                            System.out.println("Trailer_URL = " + result.getString(6));
                            System.out.println();
                    }
                    Menu();
        		}
        		if(oneInput == 4)
        		{
        			System.out.println("Enter the id here:");
        			int queryIdOnly = in.nextInt();
                    Statement select = connection.createStatement();
                    ResultSet result = select.executeQuery("SELECT movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url from movies, stars, stars_in_movies " +
                    		"WHERE stars.id =" + '"' + queryIdOnly + '"' + "AND stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id;");

                	System.out.println("The results of the query");
                    while (result.next())
                    {
                            System.out.println("Id = " + result.getInt(1));
                            System.out.println("Title = " + result.getString(2));
                            System.out.println("Year = " + result.getInt(3));
                            System.out.println("Director = " + result.getString(4));
                            System.out.println("Banner_URL = " + result.getString(5));
                            System.out.println("Trailer_URL = " + result.getString(6));
                            System.out.println();
                    }
                    Menu();
        		}
        		
        	}
        	
        	
        	if(userinput == 2)
        	{
        		Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb",username, password);
        		System.out.println("Enter the first name of the star here (if star has one name only, type blank to leave empty):");
    			String firstName = in.next();
    			if(firstName.equals("blank"))
    			{
    				firstName = "";
    			}
    			System.out.println("Enter the last name of the star here (or if the star only has one name, enter it here):");
    			String lastName = in.next();
    			System.out.println("Enter the dob of the star here (type blank to leave empty):");
    			String dob = in.next();

    			System.out.println("Enter the photo url of the star here (type blank to leave empty):");
    			String photo_url = in.next();
    			if(photo_url.equals("blank"))
    			{
    				photo_url = "";
    			}
                Statement insert = connection.createStatement();

                System.out.println("DOB IS " + dob);
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
                
                System.out.println("Query has been processed");
                
                Menu();
        	}
        	
        	if(userinput == 3)
        	{
        		Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb",username, password);
        		System.out.println("Enter the first name of the customer here (if customer has one name only, type blank to leave empty):");
        		String firstName = in.next();
    			if(firstName.equals("blank"))
    			{
    				firstName = "";
    			}
    			System.out.println("Enter the last name of the customer here (or if the star only has one name, enter it here):");
    			String lastName = in.next();
    			System.out.println("Enter the credit card number of the customer here (required):");
    			in.nextLine();
    			String credit_card = in.nextLine();

    			Statement select = connection.createStatement();

                ResultSet result = select.executeQuery("SELECT * FROM creditcards " +
                		"WHERE " + credit_card + "= creditcards.id;");

                if(!result.next())
                {
                	System.out.println("Invalid credit card, returning to main menu...");
                	Menu();
                }
                
    			System.out.println("Enter the address of the customer here (required):");
    			String address = in.nextLine();
    			in.nextLine();
    			System.out.println("Enter the email of the customer here (required):");
    			String email = in.next();
    			
    			System.out.println("Enter the password of the customer here (required):");
    			String password = in.next();
    			
                Statement insert = connection.createStatement();

                insert.executeUpdate("INSERT into customers(first_name, last_name, cc_id, address, email, password) " +
                		"VALUES (" + '"' + firstName + '"'
                		+ "," + '"' + lastName + '"'
                		+ "," + '"' + credit_card + '"'
                		+ "," + '"' + address + '"' 
                		+ "," + '"' + email + '"'
                		+ "," + '"' + password + '"' + ");");
        
                System.out.println("Query has been processed");
                
                Menu();
        	}
        
        if(userinput == 4)
        {
        	Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb",username, password);
    		System.out.println("Please enter a selection from below:");
        	System.out.println("1. Delete customer by first and last name");
    		System.out.println("2. Delete customer by id");
    		in.nextLine();
    		int userSelection = in.nextInt();
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
                Menu();
    		}
    		if(userSelection == 2)
    		{
    			System.out.println("Enter the customer's id:");
    			int id = in.nextInt();
    			
    			Statement update = connection.createStatement();
                update.executeUpdate("delete from customers where id = " 
                		+ id + ";");
                
                System.out.println("Query has been processed.");
                Menu();
    		}
    		
        }
        
        if(userinput == 5)
        {
        	Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb",username, password);
        	System.out.println("Please make a selection below:");
        	System.out.println("1. Specify metadata for a specific table");
        	System.out.println("2. Get all metadata for the database");
        	int selection = in.nextInt();
        	
        	if(selection == 1)
        	{System.out.println("Please provide the database name of which you want metadata for:");
        	String databaseName = in.next();
        	
            Statement select = connection.createStatement();
            ResultSet result = select.executeQuery("Select * from " + databaseName);
            
            System.out.println("The results of the query");
            ResultSetMetaData metadata = result.getMetaData();
            System.out.println("There are " + metadata.getColumnCount() + " columns");
            
            for (int i = 1; i <= metadata.getColumnCount(); i++)
//                System.out.println("Type of column "+ i + " is " + metadata.getColumnTypeName(i));
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
//                        System.out.println("Type of column "+ i + " is " + metadata.getColumnTypeName(i));
                    	System.out.println(metadata.getColumnName(i) + " is " + metadata.getColumnTypeName(i));
        	        
        	    }
        	}
        }
        
        if(userinput == 6)
        {
        	System.out.println("Please make a selection below:");
        	System.out.println("1. Make a valid SELECT SQL command");
        	System.out.println("2. Make a valid UPDATE SQL command");
        	System.out.println("3. Make a valid INSERT SQL command");
        	System.out.println("4. Make a valid DELETE SQL command");
        	
        	int selection = in.nextInt();
        	
        	if(selection == 1)
        	{
        		System.out.println("Enter your SELECT statement below:");
        		in.nextLine();
        		String selectQuery = in.nextLine();
        		
        		Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb",username, password);
        		Statement select = connection.createStatement();
                ResultSet result = select.executeQuery(selectQuery);
                ResultSetMetaData metadata = result.getMetaData();
                while(result.next())
                {
                	for (int i = 1; i <= metadata.getColumnCount(); i++)
//                      System.out.println("Type of column "+ i + " is " + metadata.getColumnTypeName(i));
                  	{
                		System.out.println(metadata.getColumnName(i) + " = " + result.getString(metadata.getColumnName(i)));
                	}
                }
                Menu();
        	}
        	if(selection == 2)
        	{
        		System.out.println("Enter your UPDATE statement below:");
        		in.nextLine();
        		String updateQuery = in.nextLine();
        		
        		Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb",username, password);
    	        PreparedStatement updateStars = connection.prepareStatement(updateQuery);
    	        
    	        Statement update = connection.createStatement();
    	        int rows = updateStars.executeUpdate();
    	        System.out.printf("%d row(s) updated!", rows);
    	        
    	        Menu();
        	}
        	if(selection == 3)
        	{
        		System.out.println("Enter your INSERT statement below:");
        		in.nextLine();
        		String insertQuery = in.nextLine();
        		
        		Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb",username, password);
                Statement insert = connection.createStatement();

                int rows = insert.executeUpdate(insertQuery);
                System.out.printf("%d row(s) updated!", rows);
                
                //wait 2 seconds
                Thread.sleep(2000L);
                Menu();
        	}
        	if(selection == 4)
        	{
        		System.out.println("Enter your DELETE statement below:");
        		in.nextLine();
        		String deleteQuery = in.nextLine();
        		
        		Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb",username, password);
                Statement update = connection.createStatement();
                
                int rows = update.executeUpdate(deleteQuery);
                System.out.printf("%d row(s) updated!", rows);
                
                //wait 2 seconds
                Thread.sleep(2000L);
                Menu();
        	}
        	
        }
        
        if(userinput == 7)
        {
        	logIn();
        }
        
        if(userinput == 8)
        {
        	System.exit(0);
        }
        	
        	
        }
        if(successfullyLoggedIn == false)
        {
        	logIn();
        }
        
	}
	
}
