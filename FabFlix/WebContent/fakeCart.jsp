<%@ page import = "fabflix.ShoppingCart,
	fabflix.Item"
%>
<%
ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
if (cart == null || cart.isEmpty()) {
	ShoppingCart dummyCart = new ShoppingCart("658017");
	Item item1 = new Item("One", 156005, 1);
	Item item2 = new Item("Two", 693007, 2);
	Item item3 = new Item("Three", 755005, 3);
	dummyCart.addItem(item1);
	dummyCart.addItem(item2);
	dummyCart.addItem(item3);
	session.setAttribute("cart", dummyCart);
}
%>