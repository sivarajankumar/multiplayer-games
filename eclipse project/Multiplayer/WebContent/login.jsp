<%@page import="constants.Keys"%>
<%@page import="model.User"%>
<%@page import="control.login.LoginException"%>
<%@page import="control.login.LoginController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	LoginController controller = new LoginController();
	LoginException loginException = null;
	if (request.getParameter("userName") != null) {
		try {
			User user = controller.login(request.getParameter("userName"), request.getParameter("password"));
			session.setAttribute(Keys.SESSION_USER, user);
			response.sendRedirect("welcome-screen.jsp");
		} catch (LoginException exception) {
			loginException = exception;
		}
	}
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link href="css/login.css" type="text/css" rel="stylesheet"/>
		<title>Sign in</title>
	</head>
	<body>
		<div id="container">
			<form action="login.jsp" method="get">
				<fieldset>
	    			<legend>Sign in:</legend>
	    			<div class="row-container">
						<label for="userName">User name:</label>
						<div><input type="text" name="userName" id="userName" /></div>
						<% if (loginException != null && loginException.isWrongUserName()){ %>
							<div><%=loginException.getMessage()%></div>
						<% } %>
					</div>
					<div class="row-container">
						<label for="password">Password:</label>
						<div><input type="password" name="password" id="password" /></div>
						<% if (loginException != null && loginException.isWrongPassword()){ %>
							<div><%=loginException.getMessage()%></div>
						<% } %>
					</div>
					<input type="submit" value="Sign in" />
				</fieldset>
			</form>
		</div>
	</body>
</html>