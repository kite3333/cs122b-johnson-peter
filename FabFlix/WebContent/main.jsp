<%@ page import = "java.io.*,
	java.sql.Connection,
	java.sql.DriverManager,
	java.sql.ResultSet,
	java.sql.SQLException,
	java.sql.Statement,
	java.util.*,
	coreservlets.ServletUtilities"
%>
<%
out.print(ServletUtilities.headWithTitle("Fabflix Main"));
String email = request.getParameter("email");
String firstName = "";
String lastName = "";
String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
Statement statement = dbcon.createStatement();
String query = "SELECT * from customers where email = '" + email + "'" + ";";
// Perform the query
ResultSet rs = statement.executeQuery(query);

if (rs.next())
{
	firstName = rs.getString("first_name");
	lastName = rs.getString("last_name");
}
out.println("<h2>Welcome " + firstName + " " + lastName + "</h2>");
%>
<a href="./browse.jsp">Browse the Movie Database</a> <br />
<a href="./search.jsp">Search the Movie Database</a>
<% out.println(ServletUtilities.pageEnd()); %>