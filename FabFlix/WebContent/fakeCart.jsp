<%@ page import = "fabflix.ShoppingCart,
	fabflix.Item"
%>
<%
ShoppingCart dummyCart = new ShoppingCart("lind@yahoo.com");
Item item1 = new Item("One", 1, 1);
Item item2 = new Item("Two", 2, 2);
Item item3 = new Item("Three", 3, 3);
dummyCart.addItem(item1);
dummyCart.addItem(item2);
dummyCart.addItem(item3);
session.setAttribute("cart", dummyCart);
%>