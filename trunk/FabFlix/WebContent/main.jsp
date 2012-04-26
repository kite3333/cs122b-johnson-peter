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
String firstName = "";
String lastName = "";
String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
Statement statement = dbcon.createStatement();
String query = "SELECT * FROM customers WHERE email = '" + request.getParameter("email") + "'"
	+ " AND password = '" + request.getParameter("password") + "';";
System.out.println(query);
// Perform the query
ResultSet rs = statement.executeQuery(query);

if (rs.next())
{
	firstName = rs.getString("first_name");
	lastName = rs.getString("last_name");
}
else {
	response.sendRedirect("./index.jsp?login=bad");
}
out.println("<h2>Welcome " + firstName + " " + lastName + "</h2>");
%>
<a href="./browse.jsp">Browse the Movie Database</a> <br />
<a href="./search.jsp">Search the Movie Database</a>
<% out.println(ServletUtilities.pageEnd()); %>