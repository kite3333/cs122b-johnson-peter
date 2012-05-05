<%@ page import = "coreservlets.ServletUtilities,
	java.util.Enumeration" %>
<% out.print(ServletUtilities.headWithTitle("Fabflix - Login")); %>
<table class="title">
  <tr><th>Fabflix</th></tr>
</table>

<%
if (request.getParameter("login") != null && request.getParameter("login").equals("bad")) {
%>
	<h2>Sorry!</h2>
	<p>Your login name and/or password was invalid. Please try again.</p>
<%
}
%>
<FORM ACTION="./main.jsp" METHOD="POST">
  Email: <INPUT TYPE="TEXT" NAME="email"><br />
  Password: <INPUT TYPE="PASSWORD" NAME="password"><br />
  <INPUT TYPE="SUBMIT" VALUE="Login">
</FORM>

<% out.println(ServletUtilities.pageEnd()); %>