<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<c:import url="/WEB-INF/views/common/common.jsp" />
</head>
<body>

<table border="1">

<c:forEach var="row" items="${excel}" varStatus="status" end="10">
	<tr>
		<td>${status.index}</td>
		<c:forEach var="col" items="${row}">
			<td>${col}</td>
		</c:forEach>
	</tr>
</c:forEach>
</table>

</body>
</html>