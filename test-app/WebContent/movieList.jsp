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
String genre = request.getParameter("genre");
String title = request.getParameter("title");

String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
// Declare our statement
Statement statement = dbcon.createStatement();

String query = "";
if(genre != null)
{
	query = "select * FROM movies, genres_in_movies WHERE movies.id = genres_in_movies.movie_id AND genres_in_movies.genre_id = (SELECT id FROM genres WHERE name = "
			+ "'" + genre + "')" + ";";
}

if(title != null)
{
	query = "SELECT * from movies WHERE title LIKE " + "'" +  title + '%' + "'" + ";";
	
}


// Perform the query
ResultSet rs = statement.executeQuery(query);

out.println("<h2>" + "Results" + "</h2>");

out.println("<TABLE border>");
out.println("<tr>" +
        "<td>" + "Title" + "</td>" +
        "<td>" + "Year" + "</td>" +
        "<td>" + "Director" + "</td>" +
        "<td>" + "Banner URL" + "</td>" +
        "<td>" + "Trailer URL" + "</td>" +
        "</tr>");

while(rs.next())
{
	String titleofMovie = rs.getString("title");
	String year = rs.getString("year");
	String director = rs.getString("director");
	String bannerURL = rs.getString("banner_url");
	String trailerURL = rs.getString("trailer_url");
	
    out.println("<tr>" +
            "<td>" + titleofMovie + "</td>" +
            "<td>" + year + "</td>" +
            "<td>" + director + "</td>" +
            "<td>" + bannerURL + "</td>" +
            "<td>" + trailerURL + "</td>" +
            "</tr>");

}

out.println("</TABLE>");





%>


</p>
</body></html>