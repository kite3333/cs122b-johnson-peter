package fabflix;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coreservlets.ServletUtilities;

/**
 * Servlet implementation class Search
 */
@WebServlet(description = "Processes search queries", urlPatterns = { "/Search" })
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Connection Values
		String loginUser = "root";
	    String loginPasswd = "";
	    String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
	    
	    //Search Values
		String title = request.getParameter("title");
		String year = request.getParameter("year");
		String director = request.getParameter("director");
		String actorFName = request.getParameter("actor_first");
		String actorLName = request.getParameter("actor_last");
		
		//Query Variables
		StringBuilder selectBuilder = new StringBuilder();
		StringBuilder clauseBuilder = new StringBuilder();
		boolean useAnd = false;
		String query = null;
		
		//Prints to client
		PrintWriter out = response.getWriter();
			
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
		    Statement statement = dbcon.createStatement();
		    
			//Build MySQL query
		    selectBuilder.append("SELECT movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url FROM movies");
		    if ( !(title.isEmpty() && year.isEmpty() && director.isEmpty() && actorFName.isEmpty() && actorLName.isEmpty() ) ) {
		    	clauseBuilder.append(" WHERE");
		    	if (!title.isEmpty()) {
		    		clauseBuilder.append(" title LIKE \"%" + title + "%\"");
		    		useAnd = true;
		    	}
		    	if (!year.isEmpty()) {
		    		if (useAnd) {
		    			clauseBuilder.append(" AND");
		    		}
		    		clauseBuilder.append(" year = '" + year + "'");
	    			useAnd = true;
		    	}
		    	if (!director.isEmpty()) {
		    		if (useAnd) {
		    			clauseBuilder.append(" AND");
		    		}
		    		clauseBuilder.append(" director = '" + director + "'");
	    			useAnd = true;
		    	}
		    	if (!actorFName.isEmpty()) {
		    		if (useAnd) {
		    			clauseBuilder.append(" AND");
		    		}
		    		clauseBuilder.append(" stars.first_name = '" + actorFName + "'");
	    			useAnd = true;
		    	}

		    	if (!actorLName.isEmpty()) {
		    		if (useAnd) {
		    			clauseBuilder.append(" AND");
		    		}
		    		clauseBuilder.append(" stars.last_name = '" + actorLName + "'");
	    			useAnd = true;
		    	}
		    	if (!actorLName.isEmpty() || !actorFName.isEmpty()) {
		    		selectBuilder.append(", stars, stars_in_movies");
		    		clauseBuilder.append(" AND stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id");
		    	}
		    }
		    query = selectBuilder.toString() + clauseBuilder.toString() + ";";
		    
		    System.out.println("Query: " + query);

		    // Perform the query
		    ResultSet rs = statement.executeQuery(query);
		    
		    // Print results page
		    out.println(ServletUtilities.headWithTitle("Results"));
		    out.println("<h1>Search Results</h1>");
		    ServletUtilities.printResults(rs, out);
		    out.println(ServletUtilities.pageEnd());
		    
		    //Close objects
		    rs.close();
		    statement.close();
		    dbcon.close();
		    
		} catch (InstantiationException e) {
			out.println("Sorry, looks like Fabflix has a problem. The JBDC class could not be instantiated.");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			out.println("Sorry, looks like Fabflix has a problem. The JBDC class could not be accessed.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			out.println("Sorry, looks like Fabflix has a problem. The JBDC class wasn't found.");
			e.printStackTrace();
		} catch (SQLException e) {
			out.println("The database query failed. Please notify the site administrator.");
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
