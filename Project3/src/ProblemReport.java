import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class ProblemReport {
	
	//Connection Fields
	String username = null;
	String password = null;
	private Connection connection;
	
	BufferedWriter writer = null;
	
	//MYSQL QUERIES
	private final static String QUERY_NO_FIRST_OR_LAST_NAME = "SELECT id, dob FROM stars WHERE " +
			"(first_name IS NULL OR first_name = '') AND (last_name IS NULL OR last_name = '');";
	
	private final static String QUERY_NO_FIRST_NAME = "SELECT id, last_name, dob FROM stars WHERE " +
			"first_name IS NULL OR first_name = '';";
	
	private final static String QUERY_NO_LAST_NAME = "SELECT id, first_name, dob FROM stars WHERE " +
			"last_name IS NULL OR last_name = '';";
	
	private final static String QUERY_EXPIRED_ACTIVE_CREDIT_CARDS = "SELECT customers.id, customers.first_name, "+
			"customers.last_name, email, creditcards.id, expiration FROM customers, creditcards WHERE customers.cc_id = creditcards.id AND " +
			"expiration < DATE(NOW());";	
	
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
	private final static String QUERY_SAME_GENRES = "SELECT one.id, one.name FROM genres as one, genres as two WHERE " +
			"one.name = two.name AND one.id != two.id GROUP BY one.id ORDER BY one.name";
	
	private final static String QUERY_NOT_BORN_YET = "SELECT id, first_name, last_name, dob FROM stars WHERE dob >= DATE(NOW()) OR dob < '1900-01-01';";
	
	private final static String QUERY_BAD_EMAIL = "SELECT id, first_name, last_name FROM customers WHERE email NOT IN " +
			"(SELECT email FROM stars WHERE email LIKE '%@%');";

	//CONSTRUCTORS
	public ProblemReport(String user, String pw, Connection con) {
		username = user;
		password = pw;
		connection = con; //connection parameter is assumed to be working.
	}

	//METHODS
	private ResultSet getResultSet(String query) throws SQLException {
		Statement statement = connection.createStatement();
		return statement.executeQuery(query);
	}
	
	private void writeResults(ResultSet result, String title) throws SQLException, IOException {
		ResultSetMetaData setData = result.getMetaData();
		writer.write("<h2>" + title + "</h2>"); 
		writer.newLine();
		writer.write("<a href=\"#top\">Back To The Top</a>");
		writer.newLine();
		if (result.next()) {
			writer.write("<table border=\"1\">");
			writer.newLine();
			writer.write("\t<tr>");
			for (int i = 1; i <= setData.getColumnCount(); i++) {
				writer.write("<th>" + setData.getColumnName(i) + "</th>");
			}
			writer.write("</tr>");
			writer.newLine();
			do {
				writer.write("\t<tr>");
				for (int i = 1; i <= setData.getColumnCount(); i++)
					writer.write("<td>" + result.getString(i) + "</td>");
				writer.write("</tr>");
				writer.newLine();
			}
			while (result.next()) ;
			writer.write("\n</table>");
		} else {
			writer.write("<p>No Results.</p>");
		} 
		 writer.newLine();
	}
	
	public String generateReport() throws SQLException {
		GregorianCalendar calendar = new GregorianCalendar();
		String date = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + 
				"-" + calendar.get(Calendar.DAY_OF_MONTH) + "(" + calendar.get(Calendar.HOUR_OF_DAY) + "." +
				calendar.get(Calendar.MINUTE) + ")";
		File file = new File("issues_report " + date + ".html");
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" " +
					"\"http://www.w3.org/TR/html4/loose.dtd\">"); writer.newLine();
			writer.write("<html>"); writer.newLine();
			writer.write("<head>"); writer.newLine();
			writer.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">"); writer.newLine();
			writer.write("<link rel=\"stylesheet\" href=\"./css/styles.css\" type=\"text/css\"/>"); writer.newLine();
			writer.write("<title>Issues Report " + date + "</title>"); writer.newLine();
			writer.write("</head>"); writer.newLine();
			writer.write("<body>"); writer.newLine();
			writer.write("<a name=\"top\"></a>");
			writer.write("<h1 style=\"text-align:center\">Issues Report " + date + "</h1>"); writer.newLine();
			writer.write("<h2>Quick Links</h2>"); writer.newLine();
			writer.write("<ul>"); writer.newLine();
			writer.write("\t<li><a href=\"#sameMovies\">Duplicate Movies</a></li>"); writer.newLine();
			writer.write("\t<li><a href=\"#sameStars\">Duplicate Stars</a></li>"); writer.newLine();
			writer.write("\t<li><a href=\"#sameGenres\">Duplicate Genres</a></li>"); writer.newLine();
			writer.write("\t<li><a href=\"#moviesNoStars\">Movies Without Any Stars</a></li>"); writer.newLine();
			writer.write("\t<li><a href=\"#starsNoMovies\">Stars Without Any Movies</a></li>"); writer.newLine();
			writer.write("\t<li><a href=\"#genresNoMovies\">Genres Without Any Movies</a></li>"); writer.newLine();
			writer.write("\t<li><a href=\"#moviesNoGenres\">Movies Without Any Genres</a></li>"); writer.newLine();
			writer.write("\t<li><a href=\"#starsNoName\">Stars Without A Name</a></li>"); writer.newLine();
			writer.write("\t<li><a href=\"#starsNoFirstName\">Stars Without A First Name</a></li>"); writer.newLine();
			writer.write("\t<li><a href=\"#starsNoLastName\">Stars Without A Last Name</a></li>"); writer.newLine();
			writer.write("\t<li><a href=\"#expiredCreditCards\">Customers With Expired Credit Cards</a></li>"); writer.newLine();
			writer.write("\t<li><a href=\"#invalidBirthdates\">Stars With Invalid Birthdates</a></li>"); writer.newLine();
			writer.write("\t<li><a href=\"#invalidEmails\">Customers With Invalid Email Addresses</a></li>"); writer.newLine();
			writer.write("</ul>"); writer.newLine();
			writer.write("<li><a name=\"sameMovies\"></a>");
			writeResults(getResultSet(QUERY_SAME_MOVIES), "Duplicate Movies");
			writer.write("<a name=\"sameStars\"></a>");
			writeResults(getResultSet(QUERY_SAME_STARS), "Duplicate Stars");
			writer.write("<a name=\"sameGenres\"></a>");
			writeResults(getResultSet(QUERY_SAME_GENRES), "Duplicate Genres");
			writer.write("<a name=\"moviesNoStars\"></a>");
			writeResults(getResultSet(QUERY_MOVIES_WITH_NO_STARS), "Movies Without Any Stars");
			writer.write("<a name=\"starsNoMovies\"></a>");
			writeResults(getResultSet(QUERY_STARS_WITH_NO_MOVIES), "Stars Without Any Movies");
			writer.write("<a name=\"genresNoMovies\"></a>");
			writeResults(getResultSet(QUERY_GENRES_WITH_NO_MOVIES), "Genres Without Any Movies");
			writer.write("<a name=\"moviesNoGenres\"></a>");
			writeResults(getResultSet(QUERY_MOVIES_WITH_NO_GENRES), "Movies Without Any Genres");
			writer.write("<a name=\"starsNoName\"></a>");
			writeResults(getResultSet(QUERY_NO_FIRST_OR_LAST_NAME), "Stars Without A Name");
			writer.write("<a name=\"starsNoFirstName\"Stars Without A First Name</a>");
			writeResults(getResultSet(QUERY_NO_FIRST_NAME), "Stars Without A First Name");
			writer.write("<a name=\"starsNoLastName\"></a>");
			writeResults(getResultSet(QUERY_NO_LAST_NAME), "Stars Without A Last Name");
			writer.write("<a name=\"expiredCreditCards\"></a>");
			writeResults(getResultSet(QUERY_EXPIRED_ACTIVE_CREDIT_CARDS), "Customers With Expired Credit Cards");
			writer.write("<a name=\"invalidBirthdates\"></a>");
			writeResults(getResultSet(QUERY_NOT_BORN_YET), "Stars With Invalid Birthdates");
			writer.write("<a name=\"invalidEmails\"></a>");
			writeResults(getResultSet(QUERY_BAD_EMAIL), "Customers With Invalid Email Addresses");
			writer.write("\n</body>\n</html>");
			writer.close();
		} catch (IOException e) {
			System.err.println("File Writer failed.");
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}
	
	public static void main(String[] args) {
		
		try {
    		// Incorporate mySQL driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql:///moviedb", "root", "");
			ProblemReport report = new ProblemReport("root", "", con);
			System.out.println(report.generateReport());
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
			e.printStackTrace();
		}
	}
	
}
