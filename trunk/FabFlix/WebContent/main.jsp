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
String firstName = null;
String lastName = null;
String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
String custID = null;

Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
Statement statement = dbcon.createStatement();

// Perform the query
ResultSet rs = statement.executeQuery("SELECT first_name, last_name, id FROM customers WHERE email = '" + 
	request.getParameter("email") + "' AND password = '" + request.getParameter("password") + "';");

if (rs.next()) {
	firstName = rs.getString("first_name");
	lastName = rs.getString("last_name");
	custID = rs.getString("id");
} 
else {
	response.sendRedirect("./index.jsp?login=bad");
}

out.println("<h2>Welcome " + firstName + " " + lastName + "</h2>");
System.out.println(custID);
//Does a session already exist and is not the same customer?
if (!session.isNew() && !custID.equals(session.getAttribute("custID"))) {
	session = request.getSession(true);
	session.setAttribute("custID", custID);
}
else if (session.isNew()) { // New session
	session.setAttribute("custID",custID);
	session.setAttribute("cart", new ShoppingCart(custID));
}
%>
<jsp:include page="./fakeCart.jsp"></jsp:include>
<a href="./browse.jsp">Browse the Movie Database</a><br />
<a href="./search.jsp">Search the Movie Database</a>
<% out.println(ServletUtilities.pageEnd()); %>