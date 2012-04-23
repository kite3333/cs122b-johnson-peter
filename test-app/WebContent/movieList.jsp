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
	query = "select * from movies m LEFT JOIN genres_in_movies mg on mg.movie_id = m.id LEFT JOIN genres g ON g.id = mg.genre_id LEFT JOIN stars_in_movies ma ON ma.movie_id = m.id LEFT JOIN stars a ON a.id = ma.star_id WHERE g.name = " + "'" + 
			genre + "'" + "ORDER BY m.title";
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
        "<td>" + "Stars" + "</td>" +
        "<td>" + "Banner URL" + "</td>" +
        "<td>" + "Trailer URL" + "</td>" +
        "</tr>");

ArrayList checkMovie = new ArrayList();
String star_list = "";
ArrayList starlist = new ArrayList();



while(rs.next())
{

	int mID = rs.getInt("id");
	String titleofMovie = rs.getString("title");
	String year = rs.getString("year");
	String director = rs.getString("director");
	String bannerURL = rs.getString("banner_url");
	String trailerURL = rs.getString("trailer_url");
	String star_fname = rs.getString("first_name");
	String star_lname = rs.getString("last_name");
	
	checkMovie.add(titleofMovie);
	System.out.println("get 0 is " + checkMovie.get(0) + " title is " + titleofMovie);
	
	if(checkMovie.get(0).equals(titleofMovie))
	{
		System.out.println("we found a same movie " + checkMovie.get(0));
		starlist.add(star_fname + " " + star_lname + ", ");
		continue;
	}
	
	else if(!checkMovie.get(0).equals(titleofMovie))
	{
		System.out.println("we found a different movie " + star_list);
		starlist.add(star_fname + " " + star_lname + ", ");
		
		for(int i = 0; i < starlist.size(); i++)
		{
			star_list += starlist.get(i).toString();
		}
		
		out.println("<tr>" +
            "<td>" + titleofMovie + "</td>" +
            "<td>" + year + "</td>" +
            "<td>" + director + "</td>" +
            "<td>" + star_list + "</td>" +
            "<td>" + bannerURL + "</td>" +
            "<td>" + trailerURL + "</td>" +
            "</tr>");
	}

	System.out.println("star list is now " + star_list);
	star_list = "";
	
	checkMovie.clear();
}
	



out.println("</TABLE>");





%>


</p>
</body></html>