<%@ page import = "java.util.PriorityQueue,
	coreservlets.ServletUtilities,
	fabflix.ShoppingCart,
	fabflix.Item"
	language = "java"
%>
<!-- Would be nice to have a nifty in-window pop-up -->
<p>Shopping Cart</p>
<%
	String custID = (String) session.getAttribute("custID");
	ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
	if (custID == null || cart == null) {
		out.println("<p align=\"center\">Your Cart is Empty</p>");
	}
	else { 
%>
<form>
	<table border="1">
	<tr>
		<th>ID</th>
		<th>Movie Title</th>
		<th>Quantity</th>
	</tr>
<%
		Item item = null;
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(cart.getMovieIDs());
		while (!pq.isEmpty()) {
			item = cart.getItem(pq.poll());
			out.println("<tr>" +
			        "<td>" + item.getID() + "</td>" +
			        "<td>" + item.getTitle() + "</td>" +
			        "<td><input type=\"text\" name = \"" + item.getID() + "\" value=\"" +
			        	item.getQuant() + "\" /></td></tr>");
		}
%>
	</table>
	<input type="submit" value="Update Cart" />
</form>
<form action="./checkout.jsp">
	<input type="submit" value="Checkout" />
</form>
<%	}//endElse 
ServletUtilities.pageEnd(); %>