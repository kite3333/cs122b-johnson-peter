<%@ page import ="
	java.io.*,
	java.sql.Connection,
	java.sql.DriverManager,
	java.sql.ResultSet,
	java.sql.SQLException,
	java.sql.Statement,
	java.util.*,
	coreservlets.ServletUtilities"
%>
<%
out.print(ServletUtilities.headWithTitle("Fabflix - Star Page"));
String star = request.getParameter("star");

int o = star.indexOf(' ');
String firstname = star.substring(0, o);
String lastname = star.substring(o);
lastname = lastname.trim();
firstname = firstname.trim();

System.out.println("first name =" + firstname + " last name =" + lastname);

String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
// Declare our statement
Statement statement = dbcon.createStatement();

String query = "select stars.id, stars.first_name, stars.last_name, stars.dob, stars.photo_url, group_concat(distinct movies.title separator ', ') from stars, stars_in_movies, movies where stars.first_name = " + 
"'" + firstname + "'" +  " AND stars.last_name = " + "'" + lastname + "'" + " AND stars.id = stars_in_movies.star_id AND stars_in_movies.movie_id = movies.id;";
%>
<h2>Results</h2>
<TABLE border=1>
<tr>
	<td>ID</td>
	<td>First Name</td>
	<td>Last Name</td>
	<td>DOB</td>
	<td>Picture</td>
	<td>List of Movies</td>
</tr>
<%
// Perform the query
ResultSet rs = statement.executeQuery(query);
while(rs.next())
{

	int id = rs.getInt("id");
	String fname = rs.getString("first_name");
	String lname = rs.getString("last_name");
	String dob = rs.getString("dob");
	String picture = rs.getString("photo_url");
	String listOfMovies = rs.getString("group_concat(distinct movies.title separator ', ')");

	String movie_copy = listOfMovies;
	String j = "";
	
    int count = 0;
    for (int i=0; i < movie_copy.length(); i++)
    {
        if (movie_copy.charAt(i) == ',')
        {
             count++;
        }
    }
	
    if(count == 0)
    {
    	movie_copy = movie_copy.trim();
    	j += "<a href= " + '"' + "./movieList.jsp?title=" + movie_copy + '"' + ">" + movie_copy + "</a>";
    }
    
    if(count != 0)
    {	
    for(int i = 0; i < count; i++)
	{
		int l = movie_copy.indexOf(",");
		j += "<a href= " + '"' + "./movieList.jsp?title=" + movie_copy.substring(0, l) + '"' + ">" + movie_copy.substring(0, l) + "</a>" + ", ";
		movie_copy = movie_copy.substring(l+2);
		System.out.println("star_copy is now " + movie_copy);
	}
    //we take the last one too
    movie_copy = movie_copy.trim();
    j += "<a href= " + '"' + "./movieList.jsp?title=" + movie_copy + '"' + ">" + movie_copy + "</a>" ;
    }
	

		out.println("<tr>" +
            "<td>" + id + "</td>" +
            "<td>" + fname + "</td>" +
            "<td>" + lname + "</td>" +
            "<td>" + dob + "</td>" +
            "<td>" + picture + "</td>" +
            "<td>" + j + "</td>" +
            "</tr>");


		j = "";
}
%>
</TABLE>
<% out.println(ServletUtilities.pageEnd()); %>