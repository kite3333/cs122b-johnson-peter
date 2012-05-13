<%@ page import ="
	java.sql.Connection,
	java.sql.DriverManager,
	java.sql.ResultSet,
	java.sql.Statement,
	coreservlets.ServletUtilities"
%>
<%
int star = Integer.parseInt(request.getParameter("star"));

String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
Statement statement = dbcon.createStatement();

String query = "select stars.id, stars.first_name, stars.last_name, stars.dob, stars.photo_url, group_concat(distinct movies.title separator ', ')" +
	"from stars, stars_in_movies, movies where stars.id = '" + star + "' AND stars.id = stars_in_movies.star_id AND stars_in_movies.movie_id = movies.id;";
ResultSet rs = statement.executeQuery(query);

if (rs.next()) {
	String firstName = rs.getString("first_name");
	String lastName = rs.getString("last_name");
	String listOfMovies = rs.getString("group_concat(distinct movies.title separator ', ')");
	
	//Create the movie links list
	StringBuilder movies = new StringBuilder();
	if (listOfMovies != null) {
		int start = 0;
		int end = listOfMovies.indexOf(',', start);
		while (end > 0) {
			movies.append("<a href=\"./movieList.jsp?title=");
			movies.append(listOfMovies.substring(start, end));
			movies.append("\">");
			movies.append(listOfMovies.substring(start, end));
			movies.append("</a>");
			start = end + 2;
			end = listOfMovies.indexOf(',', start);
		}
		movies.append("<a href=\"./movieList.jsp?title=");
		movies.append(listOfMovies.substring(start));
		movies.append("\">");
		movies.append(listOfMovies.substring(start));
		movies.append("</a>");
	}

	out.print(ServletUtilities.headWithTitle("Fabflix - Star: " + firstName + " " + lastName));
	out.print("<h1>" + firstName + " " + lastName + "</h1>");
%>
<img src="<%=rs.getString("photo_url")%>" />
<ul>
	<li>ID: <%=rs.getInt("id")%></li>
	<li>DOB: <%=rs.getString("dob") %></li>
	<li>Movies: <%=movies.toString()%></li>
</ul>
<%
}
else {
	out.print(ServletUtilities.headWithTitle("Fabflix - Star: NOT FOUND"));
}
out.println(ServletUtilities.pageEnd()); %>