<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import = "java.sql.Connection,
	java.sql.DriverManager,
	java.sql.ResultSet,
	java.sql.SQLException,
	java.sql.Statement,
	java.util.StringTokenizer,
	java.util.ArrayList"
%>

<%

StringTokenizer terms = new StringTokenizer(request.getParameter("terms"));
if (terms.countTokens() == 0) {
	return;
}
else {
	//Database Connection
	String loginUser = "root";
	String loginPasswd = "";
	String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
	
	Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	Statement statement = dbcon.createStatement();
	
	//Build search query against full-text index
	StringBuilder query = new StringBuilder();
	query.append("SELECT title from movies_FT WHERE MATCH (title) AGAINST ('");
	while (terms.countTokens() > 1) {
		query.append("+" + terms.nextToken() + " ");
	}
	query.append("+" + terms.nextToken() + "*' IN BOOLEAN MODE);");
	
	//Execute search
	ResultSet results = statement.executeQuery(query.toString());
	
	//Convert search results to links
	while (results.next()) {
		out.println("<a href=\"./movieList.jsp?title=" + results.getString("title") + "\">" + results.getString("title") + "</a><br />");
	}
}
%>