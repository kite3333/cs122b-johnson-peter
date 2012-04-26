<%@ page language="java" import="java.util.*"%>
<%
String sessionSet=request.getParameter("sessionVariable");
session.setAttribute("MySession",sessionSet);
/// String getSessionValue= (String)session.getAttribute("sessionSet"); 
//this is use for session value in String data
%>

<html>
<head>
<title>Cookie Create Example</title>
</head>
<body>
Session :    <%=(String)session.getAttribute("MySession")%>
</body>
</html>