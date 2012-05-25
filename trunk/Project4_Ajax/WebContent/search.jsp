<%@ page language="java" 
	import = "coreservlets.ServletUtilities,
	java.sql.Connection,
	java.sql.DriverManager,
	java.sql.ResultSet,
	java.sql.SQLException,
	java.sql.Statement" 
%>
<%
//Database Connection
String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
Statement statement = dbcon.createStatement();

//Drop existing full-text table in case it is outdated
statement.execute("DROP TABLE IF EXISTS movies_FT;");
//create full-text index query
statement.execute("CREATE TABLE movies_FT (id INT AUTO_INCREMENT, title text NOT NULL, PRIMARY KEY (id), FULLTEXT (title)) ENGINE=MyISAM;");
//Populate full-text table with movies data.
statement.execute("INSERT INTO movies_FT (id, title) SELECT id, title FROM movies;");

out.print(ServletUtilities.headWithTitle("Fabflix - Search"));
%>
<h1>Search</h1>
<form action="./checkout.jsp">
<input type="submit" value="Go to Checkout" /></form>
<form name="search" action="./movieList.jsp" method="get">
	Title: <input type="text" name="title" id="titleField" onkeyup="autoComplete(this.value)"/><br />
	<div class="suggestionBox" id="suggestions" style="display: none;"></div>
	<div style="clear: both;">Year: <input type="text" name="year" /><br /></div>
	Director: <input type="text" name="director"><br />
	Actor's First Name: <input type="text" name="actor_first"><br />
	Actor's Last Name: <input type="text" name="actor_last"><br />
	<input type="submit" value="Submit" />
</form>

<script>
function autoComplete(terms) {
	var ajaxRequest;
	if (terms.length == 0) {
		document.getElementById("suggestions").innerHTML = "";
		document.getElementById("suggestions").setAttribute("style", "display: none;");
		return;
	}
	else {
		try{
			// Opera 8.0+, Firefox, Safari
			ajaxRequest = new XMLHttpRequest();
		} 
		catch (e) {
			// Internet Explorer Browsers
			try{
				ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
			} 
			catch (e) {
				try{
					ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
				} 
				catch (e) {
					// Something went wrong
					alert("Update your browser!");
					return false;
				}
			}
		}
		ajaxRequest.onreadystatechange = function() {
			if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
				if (ajaxRequest.responseText.length > 4) {
					document.getElementById("suggestions").innerHTML = ajaxRequest.responseText;
					document.getElementById("suggestions").setAttribute("style", "display: block;");
				}
				else {
					document.getElementById("suggestions").innerHTML = "";
					document.getElementById("suggestions").setAttribute("style", "display: none;");
				}
			}
		}; //end anonymous function
		ajaxRequest.open("GET", "autoComplete.jsp?terms=" + terms, true);
		ajaxRequest.send();
	}
}

</script>
<% out.println(ServletUtilities.pageEnd()); %>