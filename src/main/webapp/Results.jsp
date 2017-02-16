<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Output</title>
</head>
<body>
	<%
		ArrayList<String> output = (ArrayList<String>) request.getAttribute("output");
	%>
	<h1>
		<%
			for (String s : output) {
				%><%=s%>
				<br>
			<%}
		%>
	</h1>
</body>
</html>