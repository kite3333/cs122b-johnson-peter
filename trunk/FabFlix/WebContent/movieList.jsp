<%@ page import = "java.io.*,
	java.sql.Connection,
	java.sql.DriverManager,
	java.sql.ResultSet,
	java.sql.SQLException,
	java.sql.Statement,
	java.util.Scanner, 
	java.util.*, 
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

//System.out.println("URL PARAMETERS" + urlParameters);

Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
// Declare our statement
Statement statement = dbcon.createStatement();

//Browse overrides Search
if (genre != null || titleStart != null) { //Make Browse Query
	try{
	if(genre != null)
	{
		/* query = "select m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, group_concat(distinct g.name separator ', '), group_concat(distinct a.first_name, " + "' " + "'" + ", a.last_name separator " + "'" + ", " + "'" + ") from movies m LEFT JOIN genres_in_movies mg on mg.movie_id = m.id LEFT JOIN genres g ON g.id = mg.genre_id LEFT JOIN stars_in_movies ma ON ma.movie_id = m.id LEFT JOIN stars a ON a.id = ma.star_id " 
				+ "WHERE g.name = " + "'" + genre + "'" + " GROUP BY m.id LIMIT " + limit + " OFFSET " + 0 + ";"; */
			 
			 
		query = "select m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, group_concat(distinct g.name separator ', '), group_concat(distinct a.first_name, " + "' " + "'" + ", a.last_name separator " + "'" + ", " + "'" + ") from movies m, genres_in_movies mg, genres g, stars_in_movies ma, stars a WHERE mg.movie_id = m.id AND g.id = mg.genre_id AND ma.movie_id = m.id AND a.id = ma.star_id " 
				+ "AND g.name = " + "'" + genre + "'" + " GROUP BY m.id ORDER BY " + columnToSort + " " + sort +  " LIMIT " +  limit + " OFFSET " + 0 + ";"; 
			
	}
	//Title overrides genre browse
	else if(titleStart != null)
	{
/* 		query = "select m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, group_concat(distinct g.name separator ', '), group_concat(distinct a.first_name, ' ', a.last_name separator ', ') from movies m LEFT JOIN genres_in_movies mg on mg.movie_id = m.id LEFT JOIN genres g ON g.id = mg.genre_id LEFT JOIN stars_in_movies ma ON ma.movie_id = m.id LEFT JOIN stars a ON a.id = ma.star_id WHERE m.title LIKE '" + titleStart + "%' GROUP BY m.id"
				+ " LIMIT " + limit + " OFFSET " + offset + ";";	 */
		
				query = "select m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, group_concat(distinct g.name separator ', '), group_concat(distinct a.first_name, ' ', a.last_name separator ', ') FROM movies m, genres_in_movies mg, genres g, stars_in_movies ma, stars a WHERE mg.movie_id = m.id AND g.id = mg.genre_id AND ma.movie_id = m.id AND a.id = ma.star_id AND m.title LIKE '" + titleStart + "%' GROUP BY m.id"
						+ " ORDER BY " + columnToSort + " " + sort + " LIMIT " + limit + " OFFSET " + offset + ";";	
	}
}
catch(NullPointerException e)
{
	
}
}
else { //Make Search Query
	selectBuilder.append("select m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, ISNULL(group_concat(distinct g.name separator ', ')), ISNULL(group_concat(distinct a.first_name, ' ', a.last_name separator ', ')) from movies m LEFT JOIN genres_in_movies mg on mg.movie_id = m.id LEFT JOIN genres g ON g.id = mg.genre_id LEFT JOIN stars_in_movies ma ON ma.movie_id = m.id LEFT JOIN stars a ON a.id = ma.star_id");
	//selectBuilder.append("select m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, group_concat(distinct g.name separator ', '), group_concat(distinct a.first_name, " + "' " + "'" + ", a.last_name separator " + "'" + ", " + "'" + ") FROM movies m, genres_in_movies mg, genres g, stars_in_movies ma, stars a WHERE mg.movie_id = m.id AND g.id = mg.genre_id AND ma.movie_id = m.id AND a.id = ma.star_id");	

if ( !(inTitle.isEmpty() && inYear.isEmpty() && inDirector.isEmpty() && inActorFName.isEmpty() && inActorLName.isEmpty() ) ) {
		clauseBuilder.append(" WHERE");
		if (!inTitle.isEmpty()) {
			clauseBuilder.append(" m.title LIKE \"%" + inTitle + "%\"");
			useAnd = true;
		}
		if (!inYear.isEmpty()) {
			if (useAnd) {
				clauseBuilder.append(" AND");
			}
			clauseBuilder.append(" m.year = '" + inYear + "'");
			useAnd = true;
		}
		if (!inDirector.isEmpty()) {
			if (useAnd) {
				clauseBuilder.append(" AND");
			}
			clauseBuilder.append(" m.director = '" + inDirector + "'");
			useAnd = true;
		}
		if (!inActorFName.isEmpty()) {
			if (useAnd) {
				clauseBuilder.append(" AND");
			}
			clauseBuilder.append(" a.first_name = '" + inActorFName + "'");
			useAnd = true;
		}

		if (!inActorLName.isEmpty()) {
			if (useAnd) {
				clauseBuilder.append(" AND");
			}
			clauseBuilder.append(" a.last_name = '" + inActorLName + "'");
			useAnd = true;
		}
		if (!inActorLName.isEmpty() || !inActorFName.isEmpty()) {
			//selectBuilder.append(", stars, stars_in_movies");
			//clauseBuilder.append(" AND stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id");
		}
	}
	query = selectBuilder.toString() + clauseBuilder.toString() + "Group BY m.id;";
}

// Perform the query
ResultSet rs = statement.executeQuery(query);


%>

<h1>Fabflix - Browse Results</h1>
<h2>Results</h2>
<form action="./checkout.jsp">
<input type="submit" value="Go to Checkout" /></form>
<TABLE border="1">
<tr>
	<td>Get it?</td>
	<td>ID</td>
	<%
	out.println("<td>Title <a href=" + "'" + "./movieList.jsp?sortByTitle=ASC&" + urlParameters + "'" + "> ASC </a> || <a href=" + "'" + "http://localhost:8080/FabFlix/movieList.jsp?sortByTitle=DESC&" + urlParameters + "'" + "> DESC</a></td>");
	out.println("<td>Year <a href=" + "'" + "./movieList.jsp?sortByYear=ASC&" + urlParameters + "'" + "> ASC </a> || <a href=" + "'" + "http://localhost:8080/FabFlix/movieList.jsp?sortByYear=DESC&" + urlParameters + "'" + "> DESC </a></td>");
	%>
	<td>Director</td>
	<td>Stars</td>
	<td>Genres</td>
	<td>Banner Link</td>
	<td>Trailer Link</td>
</tr>

<%
try{
while(rs.next())
{

	int id = rs.getInt("id");
	String titleOfMovie = rs.getString("title");
	String year = rs.getString("year");
	String director = rs.getString("director");
	String bannerURL = rs.getString("banner_url");
	String trailerURL = rs.getString("trailer_url");

	String stars = rs.getString("ISNULL(group_concat(distinct a.first_name, ' ', a.last_name separator ', '))");
	String genres = rs.getString("ISNULL(group_concat(distinct g.name separator ', '))");
	
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
    
    if(count == 0)
    {
    	j += "<a href= " + '"' + "./starPage.jsp?star=" + star_copy + '"' + ">" + star_copy + "</a>";
    }
    
    if(count != 0)
    {	
    for(int i = 0; i < count; i++)
	{
		int l = star_copy.indexOf(",");
		j += "<a href= " + '"' + "./starPage.jsp?star=" + star_copy.substring(0, l) + '"' + ">" + star_copy.substring(0, l) + "</a>" + ", ";
		star_copy = star_copy.substring(l+2);
		/* System.out.println("star_copy is now " + star_copy); */
	}
    //we take the last one too
    j += "<a href= " + '"' + "./starPage.jsp?star=" + star_copy + '"' + ">" + star_copy + "</a>" ;
    }

		out.println("<tr>" +
            "<td>" + "<a href=\"./shoppingCart.jsp?" + id + "=1&title=" + titleOfMovie + "\">Add to Cart</a></td>" +
	        "<td>" + id + "</td>" +
            "<td>" + titleOfMovie + "</td>" +
            "<td>" + year + "</td>" +
            "<td>" + director + "</td>" +
            "<td>" + j + "</td>" +
            "<td>" + genres + "</td>" +
            "<td>" + "<img src=" + "'" + bannerURL + "'" + "/>" + "</td>" +
            "<td>" + trailerURL + "</td>" +
            "</tr>");

		j = "";
}
}
catch(NullPointerException e)
{
	response.sendRedirect("./error.jsp?code=3");
}

 %>

</TABLE>
<% out.println(ServletUtilities.pageEnd()); %>