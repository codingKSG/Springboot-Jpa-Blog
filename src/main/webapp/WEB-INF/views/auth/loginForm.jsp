<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Giry`s Blog</title>
</head>
<body>
	<h1>Login Page</h1>
	<hr />
	<form action="/login" method="post">
		<input type="text" placeholder="Username" name="username" /> <br/>
		<input type="password" placeholder="Password" name="password" /> <br/>
		<button>로그인</button>
	</form>
	아직 회원가입이 안되셨나요?
	<a href="/joinForm">회원가입</a>
	<br/>
	<a href="/oauth2/authorization/google">구글 로그인</a>
	<br/>
	<a href="/oauth2/authorization/facebook">페이스북 로그인</a>
</body>
</html>