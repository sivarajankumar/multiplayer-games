<%@page import="control.game.GameController"%>
<%@page import="constants.Keys"%>
<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<% 
	User user = (User)session.getAttribute(Keys.SESSION_USER); 
	GameController controller = new GameController();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<title>Tic-Tac-Toe</title>
	
	<link rel="stylesheet" href="css/blueprint/reset.css" />
	
	<!-- jQuery -->
	<link rel="stylesheet" href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.9.0/themes/base/jquery-ui.css" />
	<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.9.0/jquery-ui.min.js"></script>
	<!-- Angular -->
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.1/angular.min.js"></script>
	
	<script type="text/javascript" src="js/lib/purl.js"></script>
	<script type="text/javascript" src="js/my-lib/push_client.js"></script>
	<script type="text/javascript" src="js/my-lib/angular-components.js"></script>
	<script type="text/javascript" src="js/my-lib/angular-ui-components.js"></script>
	
	<link rel="stylesheet" href="css/games.css" />
	<link rel="stylesheet" href="css/tic-tac-toe.css" />
	<script type="text/javascript">
		var gameOptions = <%= controller.getGameOptions(user) %>;
	</script>
	<script type="text/javascript" src="js/tic-tac-toe.js"></script>
</head>
<body>

	<div id="content" ng-app="tic-tac-toe" ng-controller="TicTacToeController">
	
		<h1>Tic-Tac-Toe : X - 0</h1>
	
		<p id="next-move">Next move: {{ players[currentPlayerIndex] }} <img src="images/spinner.gif" ng-hide="players[currentPlayerIndex] == player" /></p>
	
		<div id="board">
			<div class="row" ng-repeat="row in board">
				<div class="cell" ng-class="{ 'last-move' : cell == opponentsLastMove }" ng-repeat="cell in row" ng-click="placeSymbol($parent.$index, $index)">{{cell.content}}</div>
			</div>
		</div>
		
		<victory-dialog></victory-dialog>
		<message-dialog></message-dialog>
	</div>

</body>
</html>