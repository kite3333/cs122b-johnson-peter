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
int pageNum = 1;
out.println("Page " + pageNum);
out.println("<a href= " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?page=" + "Previous" + "&pageNum=" + pageNum + '"' + ">" + "Previous" + "</a>" + " || ");
out.println("<a href= " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?page=" + "Next" + "&pageNum=" + pageNum + '"' + ">" + "Next" + "</a>");


out.println("<p>");



//Browse Variables
String genre = request.getParameter("genre");
String titleStart = request.getParameter("titleStart");

if(genre != null)
{
	out.println("<a href= " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?pageSize=" + "10" + "&pageNum=" + pageNum  + "&genre=" + genre + '"' + ">" + 10 + "</a>");
	out.println("<a href= " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?pageSize=" + "25" + "&pageNum=" + pageNum  + "&genre=" + genre + '"' + ">" + 25 + "</a>");
	out.println("<a href= " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?pageSize=" + "50" + "&pageNum=" + pageNum  + "&genre=" + genre + '"' + ">" + 50 + "</a>");
	out.println("<a href= " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?pageSize=" + "100" + "&pageNum=" + pageNum  + "&genre=" + genre + '"' + ">" + 100 + "</a>");	
	
	out.println("<a href= " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?pageSize=" + "10" + "&pageNum=" + pageNum  + "&genre=" + genre + "&page=" + "Next" + '"' + ">" + "Next" + "</a>");
}
if(titleStart != null)
{
	out.println("<a href= " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?pageSize=" + "10" + "&pageNum=" + pageNum + "&titleStart=" + titleStart +  '"' + ">" + 10 + "</a>");
	out.println("<a href= " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?pageSize=" + "25" + "&pageNum=" + pageNum + "&titleStart=" + titleStart +  '"' + ">" + 25 + "</a>");
	out.println("<a href= " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?pageSize=" + "50" + "&pageNum=" + pageNum + "&titleStart=" + titleStart +  '"' + ">" + 50 + "</a>");
	out.println("<a href= " + '"' + "http://localhost:8080/FabFlix/movieList.jsp?pageSize=" + "100" + "&pageNum=" + pageNum + "&titleStart=" + titleStart +  '"' + ">" + 100 + "</a>");	
}


out.println(ServletUtilities.headWithTitle("Results List"));

//Pagination Variables
if(request.getParameter("page") == "Next")
{
	pageNum++;
}

int limit = 20;
int offset = pageNum * limit;

int pageSize = 0;

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

String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
// Declare our statement
Statement statement = dbcon.createStatement();

Integer s = null;
try { 
	pageSize = Integer.parseInt(request.getParameter("pageSize"));
	  s = Integer.valueOf(pageSize);
	}
	catch (NumberFormatException e) {
	  // ...
	}
if (s != null) { 
	limit = pageSize; 
offset = pageNum * limit;}



//Browse overrides Search

if (genre != null || titleStart != null) { //Make Browse Query
	try{
	if(genre != null && genre != "null")
	{
		query = "select m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, group_concat(distinct g.name separator ', '), group_concat(distinct a.first_name, " + "' " + "'" + ", a.last_name separator " + "'" + ", " + "'" + ") from movies m LEFT JOIN genres_in_movies mg on mg.movie_id = m.id LEFT JOIN genres g ON g.id = mg.genre_id LEFT JOIN stars_in_movies ma ON ma.movie_id = m.id LEFT JOIN stars a ON a.id = ma.star_id " 
				+ "WHERE g.name = " + "'" + genre + "'" + " GROUP BY m.title LIMIT " + limit + " OFFSET " + 0 + ";";
				
				System.out.println("pagenum is " + pageNum);
				
	}
	
	else if(titleStart != null && titleStart != "null")
	{
		query = "select m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, group_concat(distinct g.name separator ', '), group_concat(distinct a.first_name, ' ', a.last_name separator ', ') from movies m LEFT JOIN genres_in_movies mg on mg.movie_id = m.id LEFT JOIN genres g ON g.id = mg.genre_id LEFT JOIN stars_in_movies ma ON ma.movie_id = m.id LEFT JOIN stars a ON a.id = ma.star_id WHERE m.title LIKE '" + titleStart + "%' GROUP BY m.title"
				+ " LIMIT " + limit + " OFFSET " + 0 + ";";	
		System.out.println("we are in the title loop");
		System.out.println("limit is " + limit);
		System.out.println("title is " + titleStart);
		
	}
	}
catch(NullPointerException e)
{
	
}
}
else { //Make Search Query
	selectBuilder.append("SELECT movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url FROM movies");
	if ( !(inTitle.isEmpty() && inYear.isEmpty() && inDirector.isEmpty() && inActorFName.isEmpty() && inActorLName.isEmpty() ) ) {
		clauseBuilder.append(" WHERE");
		if (!inTitle.isEmpty()) {
			clauseBuilder.append(" title LIKE \"%" + inTitle + "%\"");
			useAnd = true;
		}
		if (!inYear.isEmpty()) {
			if (useAnd) {
				clauseBuilder.append(" AND");
			}
			clauseBuilder.append(" year = '" + inYear + "'");
			useAnd = true;
		}
		if (!inDirector.isEmpty()) {
			if (useAnd) {
				clauseBuilder.append(" AND");
			}
			clauseBuilder.append(" director = '" + inDirector + "'");
			useAnd = true;
		}
		if (!inActorFName.isEmpty()) {
			if (useAnd) {
				clauseBuilder.append(" AND");
			}
			clauseBuilder.append(" stars.first_name = '" + inActorFName + "'");
			useAnd = true;
		}

		if (!inActorLName.isEmpty()) {
			if (useAnd) {
				clauseBuilder.append(" AND");
			}
			clauseBuilder.append(" stars.last_name = '" + inActorLName + "'");
			useAnd = true;
		}
		if (!inActorLName.isEmpty() || !inActorFName.isEmpty()) {
			selectBuilder.append(", stars, stars_in_movies");
			clauseBuilder.append(" AND stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id");
		}
	}
	query = selectBuilder.toString() + clauseBuilder.toString() + ";";
}

// Perform the query
ResultSet rs = statement.executeQuery(query);


%>

<h1>Fabflix - Browse Results</h1>
<h2>Results</h2>
<TABLE border="1">
<tr>
	<td>ID</td>
	<td>Title</td>
	<td>Year</td>
	<td>Director</td>
	<td>Stars</td>
	<td>Genres</td>
	<td>Banner URL</td>
	<td>Trailer URL</td>
</tr>

<%
try{
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
    
    
/*     System.out.println("count is now " + count);
	
    System.out.println("star_copy is now " + star_copy); */
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
		/* System.out.println("star_copy is now " + star_copy); */
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
}
catch(NullPointerException e)
{
	
}

 %>

</TABLE>
<% out.println(ServletUtilities.pageEnd()); %>