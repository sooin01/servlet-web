<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>

	<div>
		<form id="form1" action="/file/upload" method="post" enctype="multipart/form-data">
			<input type="text" name="name" value="이름이다!">
			<input type="file" name="file">
			<input type="submit" value="업로드1">
		</form>
	</div>
	
	<div>
		<table border="1">
			<tr>
				<th>ID</th>
				<th>NAME</th>
				<th>PATH</th>
			</tr>
			<c:forEach var="item" items="${fileList}">
				<tr>
					<td>${item.id}</td>
					<td><a href="/file/download?id=${item.id}">${item.name}</a></td>
					<td>${item.path}</td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>