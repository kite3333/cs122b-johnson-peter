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

out.println("<h2>Browse by Category</h2>");
out.println("<form action=\"../FabFlix/movieList.jsp\" method=\"get\">");
out.println("<select name = \"genre\">");
//Print drop-down list options
while (rs.next()) {
	genre = rs.getString("name");
	out.println("<option value = \"" + genre + "\">" + genre + "</option>");
}

out.println("</select>");
out.println("<input type=\"submit\" value=\"Go\" />\n</form>");
//Browse By Movie
out.println("<h2>Browse by Movie Title</h2>");

out.print("||  ");
for (int i = 0; i <= 9; i++) {
	out.print("<a href = \"../FabFlix/movieList.jsp?title=\"" + i + "\">" + i + "</a>  ||  ");
}
for (int i = 65; i <= 90; i++) {
	out.print("<a href = \"../FabFlix/movieList.jsp?title=" + (char) i + "\">" + (char) i + "</a>  ||  ");
}


%>


</p>
</body></html>