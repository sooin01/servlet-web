<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<c:import url="/WEB-INF/views/common/common.jsp" />

<script type="text/javascript">
	$(document).ready(function() {
		$("#form1").submit(function() {
			$(this).ajaxSubmit({
				type: "POST",
				dataType: "json",
				success: function(data) {
					console.log(data);
				}
			});
			return false;
		});
		
		$("#form2").submit(function() {
			$(this).ajaxSubmit({
				url: "/sample/view",
				type: "POST",
				data: $("#form2").serialize(),
				dataType: "html",
				success: function(data) {
					console.log(data);
					$("#result2").html(data);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					if (jqXHR.status == 401) {
						$(location).attr("href", "http://127.0.0.1:8080/error/" + jqXHR.status + ".html");
					}
				}
			});
			return false;
		});

		$("#form3").submit(function() {
			$(this).ajaxSubmit({
				url : "/sample/viewAjax",
				type : "POST",
				data : $("#form2").serialize(),
				dataType : "html",
				success : function(data) {
					console.log(data);
					$("#result3").html(data);
				}
			});
			return false;
		});
	});
</script>
</head>
<body>

<div>
${domain}
</div>

<div>
	<form id="form1" action="/sample/fileupload" method="post" enctype="multipart/form-data">
		<input type="text" name="name" value="파일업로드입니다." /><br />
		<input type="file" name="file" /><br />
		<input type="file" name="file" />
		<input type="submit" value="업로드" />
	</form>
</div>

<div>
	<form id="form2" action="/sample/view" method="post">
		<input type="text" name="text" value="한글입니다." />
		<input type="submit" value="전송" />
	</form>
</div>
<div id="result2"></div>

<div>
	<form id="form3" action="/sample/viewAjax" method="post">
		<input type="text" name="text" value="한글입니다." />
		<input type="submit" value="전송" />
	</form>
</div>
<div id="result3"></div>

</body>
</html>