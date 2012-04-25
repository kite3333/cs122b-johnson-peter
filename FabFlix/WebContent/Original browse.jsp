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

	out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?genre=" 
			+ genre + '"' + ">"  + genre + "</a>" + "</td>" + "</tr>");

}


out.println("<h2>" + "Browse by Movie Title" + "<h2>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ 0 + '"' + ">"  + 0 + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ 1 + '"' + ">"  + 1 + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ 2 + '"' + ">"  + 2 + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ 3 + '"' + ">"  + 3 + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ 4 + '"' + ">"  + 4 + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ 5 + '"' + ">"  + 6 + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ 7 + '"' + ">"  + 8 + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ 9 + '"' + ">"  + 9 + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "A" + '"' + ">"  + "A" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "B" + '"' + ">"  + "B" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "C" + '"' + ">"  + "C" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "D" + '"' + ">"  + "D" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "E" + '"' + ">"  + "E" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "F" + '"' + ">"  + "F" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "G" + '"' + ">"  + "G" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "H" + '"' + ">"  + "H" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "I" + '"' + ">"  + "I" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "J" + '"' + ">"  + "J" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "K" + '"' + ">"  + "K" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "L" + '"' + ">"  + "L" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "M" + '"' + ">"  + "M" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "N" + '"' + ">"  + "N" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "O" + '"' + ">"  + "O" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "P" + '"' + ">"  + "P" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "Q" + '"' + ">"  + "Q" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "R" + '"' + ">"  + "R" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "S" + '"' + ">"  + "S" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "T" + '"' + ">"  + "T" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "U" + '"' + ">"  + "U" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "V" + '"' + ">"  + "V" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "W" + '"' + ">"  + "W" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "X" + '"' + ">"  + "X" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "Y" + '"' + ">"  + "Y" + "</a>" + "</td>" + "</tr>");

out.println("<tr> + <td>" + "<a href = " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?title=" 
		+ "Z" + '"' + ">"  + "Z" + "</a>" + "</td>" + "</tr>");




%>


</p>
</body></html>