import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class ProblemReport {
	
	String username = null;
	String password = null;
	private Connection connection;
	
	public ProblemReport(String user, String pw, Connection con) {
		username = user;
		password = pw;
		connection = con; //connection parameter is assumed to be working.
	}
	
	private final static String QUERY_NO_FIRST_OR_LAST_NAME = "SELECT id, dob FROM stars WHERE " +
			"(first_name IS NULL OR first_name = '') AND (last_name IS NULL OR last_name = '');";
	
	private final static String QUERY_NO_FIRST_NAME = "SELECT id, last_name, dob FROM stars WHERE " +
			"first_name IS NULL OR first_name = '';";
	
	private final static String QUERY_NO_LAST_NAME = "SELECT id, first_name, dob FROM stars WHERE " +
			"last_name IS NULL OR last_name = '';";
	
	private final static String QUERY_EXPIRED_ACTIVE_CREDIT_CARDS = "SELECT customers.id, first_name, last_name, email FROM customers, creditcards" +
			"WHERE customers.cc_id = creditcards.id AND expiration < DATE(NOW());";
	
	private final static String QUERY_MOVIES_WITH_NO_GENRES = "SELECT id, title, year FROM movies WHERE id NOT IN " +
			"(SELECT movie_id FROM genres_in_movies);";
	
	private final static String QUERY_GENRES_WITH_NO_MOVIES = "SELECT id, name FROM genres WHERE id NOT IN " +
			"(SELECT genre_id FROM genres_in_movies);";
	
	private final static String QUERY_STARS_WITH_NO_MOVIES = "SELECT id, first_name, last_name, dob FROM stars WHERE id NOT IN " +
			"(SELECT star_id FROM stars_in_movies);";
	
	private final static String QUERY_MOVIES_WITH_NO_STARS = "SELECT id, title, year FROM movies WHERE id NOT IN " +
			"(SELECT movie_id FROM stars_in_movies);";
	
	private final static String QUERY_SAME_STARS = "SELECT one.id, one.first_name, one.last_name, one.dob FROM stars AS one," + 
			" stars as two WHERE one.first_name = two.first_name AND one.last_name = two.last_name AND one.dob = two.dob " +
			"AND one.id != two.id GROUP BY one.id ORDER BY one.first_name, one.last_name, one.dob;";
	
	private final static String QUERY_SAME_MOVIES = "SELECT one.id, one.title, one.year FROM movies AS one, movies as two" +
			" WHERE one.title = two.title AND one.year = two.year AND one.id != two.id GROUP BY one.id ORDER BY one.title, " +
			"one.year;";
	
	private final static String QUERY_NOT_BORN_YET = "SELECT id, first_name, last_name, dob FROM stars WHERE dob >= DATE(NOW());";
	
	private final static String QUERY_MOVIE_TOO_OLD = "SELECT id, title, year FROM movies WHERE year < '1900-01-01'";
	
	private final static String QUERY_BAD_EMAIL = "SELECT id, first_name, last_name FROM customers WHERE email NOT IN " +
			"(SELECT email FROM stars WHERE email LIKE '%@%');";
	
	private ResultSet getResultSet(String query) throws SQLException {
		Statement statement = connection.createStatement();
		return statement.executeQuery(query);
	}
	
	private void printResults(ResultSet result) throws SQLException {
		ResultSetMetaData setData = result.getMetaData();
		if (result.next()) {
			do {
				for (int i = 1; i <= setData.getColumnCount(); i++)
					System.out.print(result.getString(i) + " ");
				System.out.println();
			}
			while (result.next()) ;
		} else {
			System.out.println("No Results.");
		}
	}
	
	public void generateReport() {
		GregorianCalendar calendar = new GregorianCalendar();
		FileWriter writer = null;
		try {
			writer = new FileWriter("issues_report " + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + 
					"-" + calendar.get(Calendar.DAY_OF_MONTH) + "(" + calendar.get(Calendar.HOUR_OF_DAY) + "." +
					calendar.get(Calendar.MINUTE) + ").html");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		try {
    		// Incorporate mySQL driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql:///moviedb", "root", "");
			ProblemReport report = new ProblemReport("root", "", con);
//			report.printResults(report.getResultSet(QUERY_GENRES_WITH_NO_MOVIES));
			report.generateReport();
		} catch (InstantiationException e1) {
			System.err.print("ERROR: The JDBC object could not be instantiated.");
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			System.err.print("ERROR: Access Denied to mySQL. Check Environment.");
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			System.err.print("ERROR: Class does not exist.");
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
