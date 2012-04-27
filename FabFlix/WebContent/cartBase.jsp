<%@ page import = "java.util.PriorityQueue,
	java.util.Enumeration,
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
	if (custID == null || cart == null || cart.isEmpty()) {
		out.println("<p align=\"center\">Your Cart is Empty</p>");
	}
	else {
%>
<form 
	<%
	if (request.getRequestURI().contains("shoppingCart.jsp")) {
		out.println("\naction=\"./shoppingCart.jsp\"");
	}
	else if (request.getRequestURI().contains("checkout.jsp")) {
		out.println("\naction=\"./checkout.jsp\"");
	}
	%>
	method="post">
<%
		Enumeration<String> parameters = request.getParameterNames();
		String nextID = null;
		while (parameters.hasMoreElements()) {
			nextID = parameters.nextElement();
			if (nextID.equals("title")) { //Workaround for "Add to Cart" buttons.
				continue;
			}
			if (request.getParameter(nextID) == null || //No quantity value or value <= 0
					Integer.parseInt(request.getParameter(nextID)) <= 0 ) {
				cart.removeItem(Integer.parseInt(nextID)); //Delete item from cart.
			}
			else { //update the quantity for that time
				if (cart.getItem(Integer.parseInt(nextID)) != null) {
					cart.getItem(Integer.parseInt(nextID)).setQuant(Integer.parseInt(request.getParameter(nextID)));
				}
				else if (request.getParameter("title") != null){
					cart.addItem(request.getParameter("title"), Integer.parseInt(nextID));
				}
			}
		}
		Item item = null;
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(cart.getMovieIDs());
		if (cart.isEmpty()) {
			out.println("<p align=\"center\">Your Cart is Empty</p>");
		}
		else {
			%>
	<table border="1">
	<tr>
		<th>ID</th>
		<th>Movie Title</th>
		<th>Quantity</th>
	</tr>
			<%
			while (!pq.isEmpty()) {
				item = cart.getItem(pq.poll());
				out.println("<tr>" +
				        "<td>" + item.getID() + "</td>" +
				        "<td>" + item.getTitle() + "</td>" +
				        "<td><input type=\"text\" name = \"" + item.getID() + "\" value=\"" +
				        	item.getQuant() + "\" /></td></tr>");
			}
		}
%>
	</table>
	<input type="submit" value="Update Cart" />
</form>
<%	}//endElse %>