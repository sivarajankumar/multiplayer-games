<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link href="css/login.css" type="text/css" rel="stylesheet"/>
		<title>Sign in</title>
	</head>
	<body>
		<div id="container">

		<c:if test="${not empty param.login_error}">
			<div id="errorblock">
				Your login attempt was not successful, try again.
			</div>
		</c:if>

		<form action="<c:url value='spring_security_check'/>" method="post">
				<fieldset>
	    			<legend>Sign in:</legend>
	    			<div class="row-container">
						<label for="userName">User name:</label>
						<div><input type="text" name="j_username" id="userName" /></div>
					</div>
					<div class="row-container">
						<label for="password">Password:</label>
						<div><input type="password" name="j_password" id="password" /></div>
					</div>
					<input type="submit" value="Sign in" />
				</fieldset>
			</form>
		</div>
	</body>
</html>