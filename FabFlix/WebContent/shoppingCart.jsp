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
<!-- Would be nice to have a nifty in-window pop-up -->
<p>Shopping Cart</p>

<TABLE border="1">
<tr>
	<td>ID</td>
	<td>Movie Title</td>
	<td>Quantity</td>
</tr>
</TABLE>

<%
String g = (String)session.getAttribute("MySession");
String getSessionValue= (String)session.getAttribute("sessionSet"); 
System.out.println("session is " + g);

 String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
// Declare our statement
Statement statement = dbcon.createStatement();
String query = "Select * from customers where id = " + g + ";";
ResultSet rs = statement.executeQuery(query);

String id = "id";
String title = "title";

out.println("<tr>" +
        "<td>" + id + "</td>" +
        "<td>" + title + "</td>" +
        "<td>" + "<input type=" + '"' + "text" + '"' + "name=" + '"' + "LastName" + '"' + "/>" + "</td>" +

        "</tr>");

%>


<%


/* out.println("<a href=" + "'" + "http://localhost:8080/FabFlix/checkout.jsp?movieID=" + movieID + "&customerID=" + customerID + "'"
+ ">" + "Checkout" + "</a>"); */

%>