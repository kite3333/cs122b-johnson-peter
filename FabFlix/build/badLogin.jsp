<%@ page import = "coreservlets.ServletUtilities" %>
<% out.print(ServletUtilities.headWithTitle("Fabflix - Login")); %>
<table class="title">
  <tr><th>Fabflix</th></tr>
</table>

<a href = "http://localhost:8080/FabFlix/"> Home </a>

<%
if (request.getParameter("login").equals("bad")) {
%>
	<h2>Sorry!</h2>
	<p>Your login name and/or password was invalid. Please try again.</p>
<%
}
%>
<% out.println(ServletUtilities.pageEnd()); %>