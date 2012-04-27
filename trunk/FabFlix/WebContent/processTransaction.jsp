<%@ page language="java" 
	import="coreservlets.ServletUtilities,
	java.sql.Connection,
	java.sql.DriverManager,
	java.sql.ResultSet,
	java.sql.SQLException,
	java.sql.Statement,
	java.sql.Date,
	java.util.GregorianCalendar,
	fabflix.ShoppingCart,
	java.util.Set,
	java.lang.StringBuilder"
%>
<%
//Database Variables
String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

//Transaction Variables
String ccNum = request.getParameter("ccNum");
String firstName = request.getParameter("firstName");
String lastName = request.getParameter("lastName");
String expiration = request.getParameter("expiration");
ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
String custID = (String) session.getAttribute("custID");

out.println(ServletUtilities.headWithTitle("Fabflix - Processing Transaction")); 

//Connect to DB
Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
Statement statement = dbcon.createStatement();

//Verify the credit card is valid
String query = "SELECT * FROM creditcards WHERE id ='" + ccNum + "' AND first_name = '" + firstName 
	+ "' AND last_name = '" + lastName + "' AND expiration = '" + expiration + "';"; 
ResultSet rs = statement.executeQuery(query);

if (cart != null && !cart.isEmpty() && rs.next())
{
	GregorianCalendar calendar = new GregorianCalendar();
	Date date = new Date(calendar.getTimeInMillis());
	StringBuilder qBuilder = new StringBuilder();
	//Build query to create sales record in DB
	qBuilder.append("INSERT into sales (customer_id, movie_id, sale_date) VALUES");
	Set<Integer> movieIDs = cart.getMovieIDs();
	int quant = 1;
	int countVerify = 0;
	for (int movieID : movieIDs) {
		quant = cart.getItem(movieID).getQuant();
		countVerify += quant;
		for (int i = 0; i < quant; i++) {
			qBuilder.append("(");
			qBuilder.append(custID);
			qBuilder.append(", ");
			qBuilder.append(movieID);
			qBuilder.append(", '");
			qBuilder.append(date.toString());
			qBuilder.append("'),");
		}
	}
	qBuilder.deleteCharAt(qBuilder.length() - 1); //Delete last comma
	qBuilder.append(";");
	System.out.println(qBuilder.toString());
	statement = dbcon.createStatement(); //Make new statement
	statement.execute(qBuilder.toString());
	if (statement.getUpdateCount() == countVerify) {
		//Empty shopping cart by overwriting the old one with a new one.
		session.setAttribute("cart", new ShoppingCart(custID));
%>
		<h1>Thank You For Your Purchase!</h1>
		<p>The transaction has successfully been processed. Have a nice day!</p>
<%
	}
	else {
		response.sendRedirect("./error.jsp?code=1");
	}
}
else {
	response.sendRedirect("./error.jsp?code=2");
}

out.println(ServletUtilities.pageEnd());
%>