<%@ page import = "coreservlets.ServletUtilities"
	language = "java"
%>
<%
String loginUser = "root";
String loginPasswd = "";
String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

out.println(ServletUtilities.headWithTitle("Fabflix - Checkout"));

%>
<h2>CheckOut</h2>
<jsp:include page="./shoppingCart.jsp"></jsp:include>
<form action="processTransaction.jsp" method="post">
Credit Card #: <input type="text" name="ccNum" value="490001"/><br />
Expiration: <input type="text" name="expiration" value="2007-09-20"/><br />
First Name: <input type="text" name="firstName" value="James"/><br />
Second Name: <input type="text" name="lastName" value="Brown"/><br />
<input type="submit" value="Submit" />
</form>

<%
out.println(ServletUtilities.pageEnd());
%>
