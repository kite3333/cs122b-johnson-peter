<%@ page import ="java.io.*,java.sql.Connection,java.sql.DriverManager,java.sql.ResultSet,
java.sql.SQLException,java.sql.Statement,java.util.Scanner"%>

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
	query = "select m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, group_concat(distinct g.name separator ', '), group_concat(distinct a.first_name, " + "' " + "'" + ", a.last_name separator " + "'" + ", " + "'" + ") from movies m LEFT JOIN genres_in_movies mg on mg.movie_id = m.id LEFT JOIN genres g ON g.id = mg.genre_id LEFT JOIN stars_in_movies ma ON ma.movie_id = m.id LEFT JOIN stars a ON a.id = ma.star_id " 
			+ "WHERE g.name = " + "'" + genre + "'" + " GROUP BY m.title;";
}

if(title != null)
{
	query = "select m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, group_concat(distinct g.name separator ', '), group_concat(distinct a.first_name, ' ', a.last_name separator ', ') from movies m LEFT JOIN genres_in_movies mg on mg.movie_id = m.id LEFT JOIN genres g ON g.id = mg.genre_id LEFT JOIN stars_in_movies ma ON ma.movie_id = m.id LEFT JOIN stars a ON a.id = ma.star_id WHERE m.title LIKE '" + title + "%' GROUP BY m.title;";
	
}

// Perform the query
ResultSet rs = statement.executeQuery(query);

out.println("<h2>" + "Results" + "</h2>");

out.println("<TABLE border>");
out.println("<tr>" +
        "<td>" + "ID" + "</td>" +
        "<td>" + "Title" + "</td>" +
        "<td>" + "Year" + "</td>" +
        "<td>" + "Director" + "</td>" +
        "<td>" + "Stars" + "</td>" +
        "<td>" + "Genres" + "</td>" +
        "<td>" + "Banner URL" + "</td>" +
        "<td>" + "Trailer URL" + "</td>" +
        "</tr>");



while(rs.next())
{

	int id = rs.getInt("id");
	String titleofMovie = rs.getString("title");
	String year = rs.getString("year");
	String director = rs.getString("director");
	String bannerURL = rs.getString("banner_url");
	String trailerURL = rs.getString("trailer_url");

	String stars = rs.getString("group_concat(distinct a.first_name, ' ', a.last_name separator ', ')");
	String genres = rs.getString("group_concat(distinct g.name separator ', ')");
	
	//need help on this part to add the url to pass to the starlist page...
	String star_copy = stars;
	String j = "";
	
    int count = 0;
    for (int i=0; i < star_copy.length(); i++)
    {
        if (star_copy.charAt(i) == ',')
        {
             count++;
        }
    }
    
    
    System.out.println("count is now " + count);
	
    System.out.println("star_copy is now " + star_copy);
    if(count == 0)
    {
    	j += "<a href= " + '"' + "http://localhost:8080/FabFlix/starPage.jsp?star=" + star_copy + '"' + ">" + star_copy + "</a>";
    }
    
    if(count != 0)
    {	
    for(int i = 0; i < count; i++)
	{
		int l = star_copy.indexOf(",");
		j += "<a href= " + '"' + "http://localhost:8080/FabFlix/starPage.jsp?star=" + star_copy.substring(0, l) + '"' + ">" + star_copy.substring(0, l) + "</a>" + ", ";
		star_copy = star_copy.substring(l+2);
		System.out.println("star_copy is now " + star_copy);
	}
    //we take the last one too
    j += "<a href= " + '"' + "http://localhost:8080/FabFlix/starPage.jsp?star=" + star_copy + '"' + ">" + star_copy + "</a>" ;
    }

		out.println("<tr>" +
	        "<td>" + id + "</td>" +
            "<td>" + titleofMovie + "</td>" +
            "<td>" + year + "</td>" +
            "<td>" + director + "</td>" +
            "<td>" + j + "</td>" +
            "<td>" + genres + "</td>" +
            "<td>" + bannerURL + "</td>" +
            "<td>" + trailerURL + "</td>" +
            "</tr>");

		j = "";

	

}

out.println("</TABLE>");




%>


</p>
</body></html>