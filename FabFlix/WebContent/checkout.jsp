<%@ page import = "java.sql.Connection,
	java.sql.DriverManager,
	java.sql.ResultSet,
	java.sql.SQLException,
	java.sql.Statement,
	coreservlets.ServletUtilities"
	language = "java"
%>
<!-- Would be nice to have a nifty in-window pop-up -->

<%
String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
// Declare our statement
Statement statement = dbcon.createStatement();
%>
<h2>CheckOut</h2>
<jsp:include page="./shoppingCart.jsp"></jsp:include>
<form action="processTransaction.jsp" method="post">
Credit Card #: <input type="text" name="ccnum" value="490001"/><br />
Expiration: <input type="text" name="exp" value="2007-09-20"/><br />
First Name: <input type="text" name="fname" value="James"/><br />
Second Name: <input type="text" name="lname" value="Brown"/>
<input type="submit" value="Submit" />
</form>

<%

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
