<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
      href="./css/styles.css"
      type="text/css"/>
<title>Fabflix - Search</title>
</head>

<body>

<form name="search" action="Search" method="get">
Title: <input type="text" name="title" /><br />
Year: <input type="text" name="year" /><br />
Director: <input type="text" name="director"><br />
Actor's First Name: <input type="text" name="actor_first"><br />
Actor's Last Name: <input type="text" name="actor_last"><br />
<input type="submit" value="Submit" />
</form>

</body>

</html>