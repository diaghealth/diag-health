<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
<div class="container" align="center">
	<form:form id="signup" action="login" method="POST">
	<div class="header">     
		<h2>Login Form</h2>
		</div>		
		<label class="error">${errorMessage}</label>
		<%
			String errorString = (String)request.getAttribute("error");
			if(errorString != null && errorString.trim().equals("true")){
			out.println("Incorrect login name or password. Please retry using correct login name and password.");
			}
		%>
		<!-- <table border="0" width="90%"> -->
			<div class="sep"></div>
        		<div class="inputs">        		
				<p>
					<%-- <form:errors class="error"/> --%>
					<input size="30" type="text" name="username" value="" placeholder="Username"/>
					<%-- <form:errors path="username" cssClass="error" /> --%>
				</p>
				<p>
					<input size="30" type="password" name="password" value="" placeholder="Password"/>
					<%-- <form:errors path="password" cssClass="error" /> --%>
				</p>
				<p class="submit">
					<input id="submit" type="submit" name="commit" value="Login">
				</p>
				<p>
					<label class="register"><a href="register">New User Register here</a></label>
				</p>
				</div>
			</form:form>
			
</div>		
		<!-- </table> -->
	
</body>
</html>