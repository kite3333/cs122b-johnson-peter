package testPackage; // Always use packages. Never use default package.

import java.io.*; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

/** Very simplistic servlet that generates plain text.
 *  Uses the @WebServlet annotation that is supported by
 *  Tomcat 7 and other servlet 3.0 containers. 
 */

@WebServlet("/hello")



public class HelloWorld extends HttpServlet {
  @Override
  
  
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	  
	  String email = request.getParameter("email");
	  String password = request.getParameter("password");
	  
	  String loginUser = "root";
      String loginPasswd = "";
      String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

      response.setContentType("text/html");    // Response mime type

	  
    PrintWriter out = response.getWriter();
    out.println("Hello World");
    try{
    Class.forName("com.mysql.jdbc.Driver").newInstance();

    Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
    // Declare our statement
    Statement statement = dbcon.createStatement();
    
    String query = "SELECT * from customers where email = '" + email + "'" + "AND password = '" 
    + password + "'" +  ";";

    // Perform the query
    ResultSet rs = statement.executeQuery(query);
    
    if(rs.next()){

    	response.sendRedirect("http://localhost:8080/FabFlix/main.jsp?email=" + email);
    	return;
    }
    if(!rs.next())
    {
    		
    		response.sendRedirect("http://localhost:8080/FabFlix/badLogin.jsp?login=bad");
    		return;
    }
    
    }
    catch (SQLException ex) {
        while (ex != null) {
              System.out.println ("SQL Exception:  " + ex.getMessage ());
              ex = ex.getNextException ();
          }  // end while
      }  // end catch SQLException
 catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException
	    {
		doGet(request, response);
	    }
  
  
}
