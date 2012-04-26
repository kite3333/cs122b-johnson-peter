<%@ page import = "java.util.PriorityQueue,
	java.util.Enumeration,
	coreservlets.ServletUtilities,
	fabflix.ShoppingCart,
	fabflix.Item"
	language = "java"
%>
<!-- Would be nice to have a nifty in-window pop-up -->
<jsp:include page="./cartBase.jsp"></jsp:include>
<form action="./checkout.jsp">
	<input type="submit" value="Checkout" />
</form>