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
%>
<form action="checkout.jsp" method="post">
Credit Card #: <input type="text" name="ccnum" value="490001"/><br />
Expiration: <input type="text" name="exp" value="2007-09-20"/><br />
First Name: <input type="text" name="fname" value="James"/><br />
Second Name: <input type="text" name="lname" value="Brown"/>
<input type="submit" value="Submit" />
</form>




<%
session.setAttribute("ccnum",request.getParameter("ccnum"));
session.setAttribute("exp",request.getParameter("exp"));
session.setAttribute("fname",request.getParameter("fname"));
session.setAttribute("lname",request.getParameter("lname"));


	String s_ccnum = (String)session.getAttribute("ccnum");
	String s_fname2 = (String)session.getAttribute("fname");
	String s_lname = (String)session.getAttribute("lname");
	String s_exp = (String)session.getAttribute("exp");
	
	String query2 = "SELECT * FROM creditcards WHERE id =" + "'" + s_ccnum + "'" + " AND first_name = " + "'" + s_fname2 + "'" 
	+ " AND last_name = " + "'" + s_lname + "'" + " AND expiration = " + "'" + s_exp + "'" + ";"; 

	Statement statement2 = dbcon.createStatement();
	ResultSet rs2 = statement.executeQuery(query2);
	
	while(rs2.next())
	{
		out.println("Success!");
		String query3 = "";
		Statement statement3 = dbcon.createStatement();
		ResultSet rs3 = statement.executeQuery(query3);
		
	}


%>
