<%@ page language="java"
	import="coreservlets.ServletUtilities"
%>
<%
out.println(ServletUtilities.headWithTitle("Fabflix - Error"));
out.println(request.getParameter("code"));
out.println(ServletUtilities.pageEnd());
%>
