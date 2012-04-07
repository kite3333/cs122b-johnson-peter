import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


public class mainMenu {


	
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Scanner in = new Scanner(System.in);
		boolean successfullyLoggedIn = false;
		
		
//		When this program is run, the user is asked for the the user name and the user password 
//		(the database user login info not the password in the above schema) . 
		System.out.println("Please enter your username for the database:");
		
		String username = in.nextLine();
		
		System.out.println("Please enter your username for the database:");
		String password = in.nextLine();
		
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
        }
        catch(SQLException e){
        System.out.println("Access denied! Please verify credentials.");
        successfullyLoggedIn = false;
        }
        
        
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
        		
        	}
        }
        if(successfullyLoggedIn == false)
        {
        	main(args);
        }
        
        
       }
}
