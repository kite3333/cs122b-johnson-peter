<%@ page import = "java.io.*,
	java.sql.Connection,
	java.sql.DriverManager,
	java.sql.ResultSet,
	java.sql.SQLException,
	java.sql.Statement,
	java.util.StringTokenizer, 
	coreservlets.ServletUtilities"
	language = "java"
%>

<%
//-------VARIABLES------//

//Browse Variables
String genre = request.getParameter("genre");
String titleStart = request.getParameter("titleStart");

//Pagination Variables
int pageNum = 1; //Use given if available and positive. Default is 1.
if (request.getParameter("pageNum") != null && Integer.parseInt(request.getParameter("pageNum")) > 0) {
	pageNum = Integer.parseInt(request.getParameter("pageNum"));
}
int limit = 10; //Use given if available and positive. Default is 10.
if (request.getParameter("pageSize") != null && Integer.parseInt(request.getParameter("pageSize")) > 0) {
	limit = Integer.parseInt(request.getParameter("pageSize"));
}
int offset = (pageNum - 1) * limit;
String urlParameters = null;

//Search Variables
String inTitle = request.getParameter("title");
String inYear = request.getParameter("year");
String inDirector = request.getParameter("director");
String inActorFName = request.getParameter("actor_first");
String inActorLName = request.getParameter("actor_last");

//Query Variables
StringBuilder selectBuilder = new StringBuilder();
StringBuilder clauseBuilder = new StringBuilder();
boolean useAnd = false;
String query = null;

//Database Variables
String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

//-------END VARIABLES------//

out.println(ServletUtilities.headWithTitle("Results List"));
out.println("Page " + pageNum);

if(genre != null)
{
	urlParameters = "genre=" + genre + "&pageNum=";
}
if(titleStart != null)
{
	urlParameters = "titleStart=" + titleStart + "&pageNum=";
}

//Previous Page Link
out.println("<a href=\"./movieList.jsp?" + urlParameters + (pageNum - 1) + "&pageSize=" + limit + "\">Prev</a> || ");
//Next Page Link
out.println("<a href=\"./movieList.jsp?" + urlParameters + (pageNum + 1) + "&pageSize=" + limit + "\">Next</a><br />");

urlParameters += pageNum; //pageNum no longer varies so combine with urlParameters
out.println("<a href=\"./movieList.jsp?" + urlParameters + "&pageSize=10\">10</a>");
out.println("<a href=\"./movieList.jsp?" + urlParameters + "&pageSize=25\">25</a>");
out.println("<a href=\"./movieList.jsp?" + urlParameters + "&pageSize=50\">50</a>");
out.println("<a href=\"./movieList.jsp?" + urlParameters + "&pageSize=100\">100</a>");	

//sorting here
String sortByTitle = request.getParameter("sortByTitle");
String sortByYear = request.getParameter("sortByYear");

String columnToSort = "m.title";
String sort = "ASC";
if(sortByYear != null)
{
	columnToSort = "m.year";
	sort = sortByYear;
}
else if(sortByTitle != null)
{
	columnToSort = "m.title";
	sort = sortByTitle;
}

urlParameters += "&sortByTitle=" + sortByTitle;
urlParameters += "&sortByYear=" + sortByYear;

Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
Statement statement = dbcon.createStatement();

//Browse overrides Search
if (genre != null || titleStart != null) { //Make Browse Query
	try{
		if(genre != null)
		{ 
			query = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, GROUP_CONCAT(distinct g.name separator ', '), "
				+ "GROUP_CONCAT(DISTINCT a.id, ' ', a.first_name, ' ', a.last_name SEPARATOR ', ') FROM movies m, genres_in_movies mg, genres g, "
				+ "stars_in_movies ma, stars a WHERE mg.movie_id = m.id AND g.id = mg.genre_id AND ma.movie_id = m.id AND a.id = ma.star_id " 
				+ "AND g.name = '" + genre + "' GROUP BY m.id ORDER BY " + columnToSort + " " + sort +  " LIMIT " +  limit + " OFFSET " + 0 + ";"; 				
		}
		//Title overrides genre browse
		else if(titleStart != null)
		{
			query = "select m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, GROUP_CONCAT(DISTINCT g.name SEPARATOR ', '), "
				+ "GROUP_CONCAT(DISTINCT a.id, ' ', a.first_name, ' ', a.last_name SEPARATOR ', ') FROM movies m, genres_in_movies mg, genres g, "
				+ "stars_in_movies ma, stars a WHERE mg.movie_id = m.id AND g.id = mg.genre_id AND ma.movie_id = m.id AND a.id = ma.star_id AND " 
				+ "m.title LIKE '" + titleStart + "%' GROUP BY m.id ORDER BY " + columnToSort + " " + sort + " LIMIT " + limit + " OFFSET " + offset + ";";	
		}
	}	
	catch(NullPointerException e)
	{
	
	}
}
else { //Make Search Query
	selectBuilder.append("select m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, GROUP_CONCAT(DISTINCT g.name SEPARATOR ', '), "
		+ "GROUP_CONCAT(DISTINCT a.id, ' ', a.first_name, ' ', a.last_name SEPARATOR ', ') FROM movies m LEFT JOIN genres_in_movies "
		+ "mg ON mg.movie_id = m.id LEFT JOIN genres g ON g.id = mg.genre_id LEFT JOIN stars_in_movies ma ON ma.movie_id = m.id LEFT JOIN "
		+ "stars a ON a.id = ma.star_id");
	if (inTitle != null || inYear != null || inDirector != null || inActorFName != null || inActorLName != null) {
		clauseBuilder.append(" WHERE");
		if (inTitle != null && !inTitle.isEmpty()) {
			clauseBuilder.append(" m.title LIKE '%" + inTitle + "%'");
			useAnd = true;
		}
		if (inYear != null && !inYear.isEmpty()) {
			if (useAnd) {
				clauseBuilder.append(" AND");
			}
			clauseBuilder.append(" m.year = '" + inYear + "'");
			useAnd = true;
		}
		if (inDirector != null && !inDirector.isEmpty()) {
			if (useAnd) {
				clauseBuilder.append(" AND");
			}
			clauseBuilder.append(" m.director LIKE '%" + inDirector + "%'");
			useAnd = true;
		}
		if (inActorFName != null && !inActorFName.isEmpty()) {
			if (useAnd) {
				clauseBuilder.append(" AND");
			}
			clauseBuilder.append(" a.first_name = '" + inActorFName + "'");
			useAnd = true;
		}
	
		if (inActorLName != null && !inActorLName.isEmpty()) {
			if (useAnd) {
				clauseBuilder.append(" AND");
			}
			clauseBuilder.append(" a.last_name = '" + inActorLName + "'");
			useAnd = true;
		}
	}
	query = selectBuilder.toString() + clauseBuilder.toString() + "GROUP BY m.id ORDER BY m.title ASC LIMIT 10 OFFSET 0;";
}
ResultSet rs = statement.executeQuery(query);
%>

<h1>Fabflix - Browse Results</h1>
<h2>Results</h2>
<form action="./checkout.jsp">
<input type="submit" value="Go to Checkout" /></form>
<%
try{
	int id = 0;
	String year = null;
	String title = null;
	String director = null;
	String bannerURL = null;
	String trailerURL = null;
	String stars = null;
	String genres = null;
	String actors = null;
	if (rs.next()) {
%>
<TABLE border="1">
<tr>
	<td></td>
	<td>ID</td>
	<%
	out.println("<td>Title <a href=\"./movieList.jsp?sortByTitle=ASC&" + urlParameters + "\"> ASC </a> || <a href=\"./movieList.jsp?sortByTitle=DESC&" + urlParameters + "\"> DESC</a></td>");
	out.println("<td>Year <a href=\"./movieList.jsp?sortByYear=ASC&" + urlParameters + "\"> ASC </a> || <a href=\"./movieList.jsp?sortByYear=DESC&" + urlParameters + "\"> DESC </a></td>");
	%>
	<td>Director</td>
	<td>Stars</td>
	<td>Genres</td>
	<td>Banner Link</td>
	<td>Trailer Link</td>
</tr>
<%
		do {
			id = rs.getInt("id");
			title = rs.getString("title");
			year = rs.getString("year");
			director = rs.getString("director");
			bannerURL = rs.getString("banner_url");
			trailerURL = rs.getString("trailer_url");
			stars = rs.getString("group_concat(distinct a.id, ' ', a.first_name, ' ', a.last_name separator ', ')");
			genres = rs.getString("group_concat(distinct g.name separator ', ')");
			actors = "";
			
			if (stars != null) {
				int start = 0;
				int idBreak = stars.indexOf(' ', start);
				int end = stars.indexOf(',', start);
				while (end > 0) {
					actors += "<a href= \"./starPage.jsp?star=" + stars.substring(start, idBreak) + "\">" + stars.substring(idBreak + 1, end) + "</a>, ";
					start = end + 2;
					idBreak = stars.indexOf(' ', start);
					end = stars.indexOf(',', start);
				}
				actors += "<a href= \"./starPage.jsp?star=" + stars.substring(start, idBreak) + "\">" + stars.substring(idBreak + 1) + "</a>";
			}
			out.println("<tr><td><a href=\"./shoppingCart.jsp?" + id + "=1&title='" + title + "'\">Add to Cart</a></td>" +
				"<td>" + id + "</td><td>" + title + "</td><td>" + year + "</td><td>" + director + "</td>" +
				"<td>" + actors + "</td><td>" + genres + "</td><td><img src='" + bannerURL + "'/></td>" +
				"<td>" + trailerURL + "</td></tr>");
		
		} while(rs.next());
	}
	else {
		out.println("<p>No Results</p>");
	}
}
catch(NullPointerException e)
{
	response.sendRedirect("./error.jsp?code=3");
}
 %>
</TABLE>
<% out.println(ServletUtilities.pageEnd()); %>