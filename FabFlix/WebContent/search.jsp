<%@ page language="java" 
	import = "coreservlets.ServletUtilities" 
%>
<%
out.print(ServletUtilities.headWithTitle("FabFlix - Search"));
%>
<h1>Search</h1>
<form name="search" action="./movieList.jsp" method="get">
Title: <input type="text" name="title" /><br />
Year: <input type="text" name="year" /><br />
Director: <input type="text" name="director"><br />
Actor's First Name: <input type="text" name="actor_first"><br />
Actor's Last Name: <input type="text" name="actor_last"><br />
<input type="submit" value="Submit" />
</form>
<% out.println(ServletUtilities.pageEnd()); %>