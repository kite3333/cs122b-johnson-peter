<%@ page import = "java.io.*,
	java.sql.Connection,
	java.sql.DriverManager,
	java.sql.ResultSet,
	java.sql.SQLException,
	java.sql.Statement, 
	java.util.*, 
	coreservlets.ServletUtilities"
	language = "java"
	%>

<%
out.print(ServletUtilities.headWithTitle("Fabflix - Browsing"));


String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
Statement statement = dbcon.createStatement();

String query = "SELECT * from genres;";

// Perform the query
ResultSet rs = statement.executeQuery(query);
%>

<!-- BROWSE BY Genre SECTION -->
<h2>Browse by Genre</h2>
<form action="../FabFlix/movieList.jsp" method="get">
<select name = "genre">
<%

String genre = "";
//Print drop-down list options
while (rs.next()) {
	genre = rs.getString("name");
	out.println("<option value = \"" + genre + "\">" + genre + "</option>");
}
%>
</select>
<input type="submit" value="Go" />
</form>

<!-- BROWSE BY MOVIE TITLE SECTION -->
<h2>Browse by Movie Title</h2>
<%
out.print("||  ");
for (int i = 0; i <= 9; i++) {
	out.print("<a href = \"../FabFlix/movieList.jsp?title=" + i + "\">" + i + "</a>  ||  ");
}
for (int i = 65; i <= 90; i++) {
	out.print("<a href = \"../FabFlix/movieList.jsp?title=" + (char) i + "\">" + (char) i + "</a>  ||  ");
}

ServletUtilities.pageEnd();
%>