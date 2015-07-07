<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<c:import url="/WEB-INF/views/common/common.jsp" />

<script type="text/javascript">
	$(document).ready(function() {
		$("#frm1").submit(function() {
			$(this).ajaxSubmit({
				type: "POST",
				dataType: "json",
				success: function(data) {
					console.log(data);
				}
			});
			
			return false;
		});
	});
</script>
</head>
<body>

<div>
	<form id="frm1" action="/sample/fileupload" method="post" enctype="multipart/form-data">
		<input type="text" name="name" value="파일업로드입니다." /><br />
		<input type="file" name="file" /><br />
		<input type="file" name="file" />
		<input type="submit" value="업로드" />
	</form>
</div>

</body>
</html>