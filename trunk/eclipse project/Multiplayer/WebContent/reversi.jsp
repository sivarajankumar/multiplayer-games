<%@page import="constants.Keys"%>
<%@page import="control.game.GameController"%>
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
	
	<title>Reversi</title>
	
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
	<link rel="stylesheet" href="css/reversi.css" />
	<script type="text/javascript">
		var gameParameters = <%= controller.getGameOptions(user) %>;
	</script>
	<script type="text/javascript" src="js/reversi.js"></script>
</head>
<body>

	<div id="content" ng-app="reversi" ng-controller="ReversiController">
	
		<h1>Reversi</h1>
	
		<table id="score">
			<tr>
				<td>Black:</td>
				<td colspan="2" class="score">{{players[0].score}}</td>
			</tr>
			<tr>
				<td>White:</td>
				<td colspan="2" class="score">{{players[1].score}}</td>
			</tr>
		</table>
		
		<div id="current-player">
			<p>Current player: <span class="name">{{currentPlayer.name}}</span></p>
			<div id="player-color" class="{{currentPlayer.name}}"></div>
			<img id="spinner" src="images/spinner.gif" ng-hide="players[currentPlayerIndex] == player" />
		</div>
		
		<div id="board">
			<div class="row" ng-repeat="row in board">
				<div class="cell" ng-class="{ 'last-move' : cell == opponentsLastMove }" ng-repeat="cell in row" ng-click="placePiece($parent.$index, $index)" >
					<div class="circle {{cell.player.name}}" ng-show="cell.player"></div>
				</div>
			</div>
		</div>
		
		<victorydialog></victorydialog>
		<messagedialog></messagedialog>
		
	</div>
</body>
</html>