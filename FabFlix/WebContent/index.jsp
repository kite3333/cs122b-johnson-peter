<%@ page import = "coreservlets.ServletUtilities" %>
<% out.print(ServletUtilities.headWithTitle("Fabflix - Login")); %>
<table class="title">
  <tr><th>Fabflix</th></tr>
</table>


<FORM ACTION="hello"
      METHOD="GET">
  Email: <INPUT TYPE="TEXT" NAME="email" value="lind@yahoo.com"><br />
  Password: <INPUT TYPE="PASSWORD" NAME="password" value="pw17"><br />
  <INPUT TYPE="SUBMIT" VALUE="Login">
</FORM>



<% out.println(ServletUtilities.pageEnd()); %>