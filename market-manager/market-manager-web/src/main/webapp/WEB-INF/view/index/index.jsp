<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script src="http://libs.baidu.com/jquery/2.0.0/jquery.js"></script>

</head>
<body>
	<div align="center">
		<p>请注册</p>
	</div>
	
	<div align="center">
		<form action="/web/user/register" method="post">
			<input name="name" type="text"/>
			<input name="password" type="text"/>
			<input name="sex" type="text"/>
			<input name="cellphone" type="text"/>
			<input type="submit" value="提交"/>
		</form>
	</div>
	
	<div align="center">
		<p>请查询</p>
	</div>
	<div align="center">
		<form action="/web/user/find" method="post">
			<input name="userId" value="${id}" type="text">
			<input value="${name}" type="text"/>
			<input value="${password}" type="text"/>
			<input value="${sex}" type="text"/>
			<input value="${cellphone}" type="text"/>
			<input type="submit" value="提交"/>
		</form>
	</div>
	
</body>
</html>