<%@ page language="java"
	import="coreservlets.ServletUtilities"
%>
<%
out.println(ServletUtilities.headWithTitle("Fabflix - Error"));
out.println("ERROR CODE: " + request.getParameter("code") +
		"Please Contact the Site Administrator.");
out.println(ServletUtilities.pageEnd());
%>
