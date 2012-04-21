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
String genre = "";

String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
// Declare our statement
Statement statement = dbcon.createStatement();

String query = "SELECT * from genres;";

// Perform the query
ResultSet rs = statement.executeQuery(query);

out.println("<h2>" + "Browse by Category" + "</h2>");
while(rs.next())
{
	genre = rs.getString("name");

	out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/test-app/movieList.jsp?genre=" 
			+ genre + '"' + ">"  + genre + "</a>" + "</td>" + "</tr>");

}


out.println("<h2>" + "Browse by Movie Title" + "<h2>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/test-app/movieList.jsp?title=" 
		+ 0 + '"' + ">"  + 0 + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/test-app/movieList.jsp?title=" 
		+ 1 + '"' + ">"  + 1 + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/test-app/movieList.jsp?title=" 
		+ 2 + '"' + ">"  + 2 + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/test-app/movieList.jsp?title=" 
		+ 3 + '"' + ">"  + 3 + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/test-app/movieList.jsp?title=" 
		+ 4 + '"' + ">"  + 4 + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/test-app/movieList.jsp?title=" 
		+ 5 + '"' + ">"  + 6 + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/test-app/movieList.jsp?title=" 
		+ 7 + '"' + ">"  + 8 + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/test-app/movieList.jsp?title=" 
		+ 9 + '"' + ">"  + 9 + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/test-app/movieList.jsp?title=" 
		+ "A" + '"' + ">"  + "A" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/test-app/movieList.jsp?title=" 
		+ "B" + '"' + ">"  + "B" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/test-app/movieList.jsp?title=" 
		+ "C" + '"' + ">"  + "C" + "</a>" + "</td>" + "</tr>");
%>


</p>
</body></html>