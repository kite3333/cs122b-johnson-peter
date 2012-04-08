import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class mainMenu {

static String username;
static String password;
static boolean successfullyLoggedIn = false;

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		logIn();
       
    }
	
	
	public static void logIn() throws InstantiationException, IllegalAccessException, ClassNotFoundException
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
	
	public static void Menu() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
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
        	
        	
        	
        }
        if(successfullyLoggedIn == false)
        {
        	logIn();
        }
        
	}
	
}
