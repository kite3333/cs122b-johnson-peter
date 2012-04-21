<%@ page import ="java.io.*,java.sql.Connection,java.sql.DriverManager,java.sql.ResultSet,
java.sql.SQLException,java.sql.Statement"%>

<!DOCTYPE html>
<html>
<head><title>Fabflix Main Page - Browsing</title></head>
<body>
<h1>Fabflix Main Page - Browsing</h1>
<%@ page import="java.util.*" %>
<p>

<%
String email = request.getParameter("email");
String firstName = "";
String lastName = "";
String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
// Declare our statement
Statement statement = dbcon.createStatement();

String query = "SELECT * from customers where email = '" + email + "'" + ";";

// Perform the query
ResultSet rs = statement.executeQuery(query);

while(rs.next())
{
	firstName = rs.getString("first_name");
	lastName = rs.getString("last_name");
}

out.println("Welcome " + firstName + " " + lastName);
%>

<a href="http://localhost:8080/test-app/browse.jsp"> Browse Movies</a>
<a href="http://localhost:8080/test-app/search.jsp"> Search Movies</a>

</p>
</body></html>