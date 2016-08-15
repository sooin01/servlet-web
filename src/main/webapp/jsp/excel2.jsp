<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
	Object obj = pageContext.getAttribute("rowList", PageContext.SESSION_SCOPE);
	pageContext.removeAttribute("rowList", PageContext.SESSION_SCOPE);
%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<p>obj: <%=obj%></p>
</body>
</html>