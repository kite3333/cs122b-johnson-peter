<%@ page import = "java.sql.Connection,
	java.sql.DriverManager,
	java.sql.ResultSet,
	java.sql.SQLException,
	java.sql.Statement,
	coreservlets.ServletUtilities,
	fabflix.ShoppingCart"
%>
<%
out.print(ServletUtilities.headWithTitle("Fabflix Main"));

//Database Variables
String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

//Customer Variables
String firstName = (String) session.getAttribute("firstName");
String lastName = (String) session.getAttribute("lastName");
String custID = (String) session.getAttribute("custID");
String email = request.getParameter("email");

//Connect to DB
Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
Statement statement = dbcon.createStatement();

// Perform the query
ResultSet rs = null;
if (custID == null && email == null) { //No login entered and no user logged in
	response.sendRedirect("./index.jsp");
} else if (email != null) { //Query for user if login entered
	rs = statement.executeQuery("SELECT first_name, last_name, id FROM customers WHERE email = '" + 
		email + "' AND password = '" + request.getParameter("password") + "';");
	if (rs.next()) {
		firstName = rs.getString("first_name");
		lastName = rs.getString("last_name");
		custID = rs.getString("id");
	} 
	else { //new login but no matches = bad login
		response.sendRedirect("./index.jsp?login=bad");
	}
}
//Does a session already exist and is not the same customer?
if (custID != null && email != null) {//previous session exists and login entered. Same users?
	if (!custID.equals(session.getAttribute("custID"))) {//Different Users
		session = request.getSession(true);
		session.setAttribute("custID", custID);
		session.setAttribute("firstName", firstName);
		session.setAttribute("lastName", lastName);
		session.setAttribute("cart", new ShoppingCart(custID));
	}
}
out.println("<h2>Welcome " + firstName + " " + lastName + "</h2>");
%>
<form action="./checkout.jsp">
<input type="submit" value="Go to Checkout" /></form>
<a href="./browse.jsp">Browse the Movie Database</a><br />
<a href="./search.jsp">Search the Movie Database</a><br />
<a href="./fuzzysearch.jsp">Fuzzy Search the Movie Database</a>
<% out.println(ServletUtilities.pageEnd()); %>