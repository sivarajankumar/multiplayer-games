<%@page import="constants.Keys"%>
<%@page import="model.User"%>
<%@page import="control.welcome.WelcomeScreenController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	WelcomeScreenController controller = new WelcomeScreenController();
	User user = (User)session.getAttribute(Keys.SESSION_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Games</title>
	
	<!-- jQuery -->
	<link rel="stylesheet" href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.9.0/themes/base/jquery-ui.css" />
	<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.9.0/jquery-ui.min.js"></script>
	<!-- DataTables -->
	<link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">
	<script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script>
	<!-- Angular -->
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.1/angular.js"></script>

	<!-- Libs -->
	<script type="text/javascript" src="js/lib/jquery.blockUI.js"></script>
	<!-- My Libs -->
	<script type="text/javascript" src="js/my-lib/push_client.js"></script>
	<script type="text/javascript" src="js/my-lib/angular-datatable.js"></script>

	<link rel="stylesheet" type="text/css" href="css/welcome-screen.css" />

	<script type="text/javascript">
		var availableGames = <%= controller.getAvailableGames() %>;
		var games = <%=controller.getOpenGames()%>;
		var loggedInUser = '<%= user.getUserName() %>'; 
	</script>	
	<script type="text/javascript" src="js/welcome-screen.js"></script>
</head>
<body>
	<div id="container" ng-app="welcome-screen" ng-controller="WelcomeScreenController">

		<div id="games-container">
			<p>Join a game:</p>
			<datatable ao-columns="columns" aa-data="games" post-processor="assignEvents(dataTable)"></datatable>
			<p id="game-description">{{selectedOpenGame.gameName}}<br/>{{selectedOpenGame.gameDescription}}</p>
		</div>

		<div id="create-game-container">
			<form id="create-game-form" ng-submit="createGame()">
				<label for="game-selector">Create a game of:</label>
				<select id="game-selector" ng-model="selectedGame" ng-options="g.name for g in availableGames"></select> 
				<input id="create-game-submit" type="submit" value="Create!" />
			</form>
		</div>

	</div>
</body>
</html>