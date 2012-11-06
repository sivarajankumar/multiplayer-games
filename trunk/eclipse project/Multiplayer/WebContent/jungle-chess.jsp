<%@page import="control.game.GameController"%>
<%@page import="constants.Keys"%>
<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<% 
	User user = (User)session.getAttribute(Keys.SESSION_USER); 
	GameController controller = new GameController();
%>
<!DOCTYPE html >
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Jungle Chess</title>
	
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
	
	<link rel="stylesheet" href="css/games.css" />
	<link rel="stylesheet" href="css/jungle-chess.css" />

	<script type="text/javascript">
		var gameParameters = <%= controller.getGameOptions(user) %>;
	</script>
	<script type="text/javascript" src="js/jungle-chess.js"></script>
</head>
<body>

<% // TODO beautify %>

	<div id="content" ng-app="jungle-chess" ng-controller="JungleChessController">
	
		<h1>Jungle Chess</h1>

		<div id="gameContainer">

			<table id="playerColors">
				<tr>
					<td>Player 1:</td>
					<td><div class="player1"></div></td>
				</tr>
				<tr>
					<td>Player 2:</td>
					<td><div class="player2"></div></td>
				</tr>
			</table>

			<div id="board">
				<div class="row" ng-repeat="row in board">
					<div class="cell {{cell.type}}" ng-repeat="cell in row" ng-click="cellClicked($parent.$index, $index)">
						<div class="cell-wrapper">
							<div ng-show="cell.selected || cell.movementShadow || cell.attackShadow || cell == opponentsLastMove" class="cell-overlay"
								ng-class="{ 'animal-selected' : cell.selected, 'move-shadow' : cell.movementShadow, 'attack-shadow' : cell.attackShadow, 'last-move' : cell == opponentsLastMove  }"></div>
							<img src="images/jungle-chess/placeholder.png" ng-show="cell.animal" ng-src="{{cell.getAnimalPicture()}}" class="contentImage"
								ng-class="cell.animal.player.name" />
							<p class="power-level" ng-show="cell.animal">{{cell.animal.powerLevel}}</p>
						</div>
					</div>
				</div>
			</div>

			<div id="victoryDialog" class="game-dialog" title="Game over">
		 		<p>{{ victoryText }}</p>
		 		<button class="styled-button" ng-click="playAgain()">Play again</button>
		 		<button class="styled-button" ng-click="backToLobby()">Back to lobby</button>
		 	</div>
		 	<div id="messageDialog" class="game-dialog" title="Game over">
		 		<p id="game-message"></p>
		 	</div>

		</div>

	</div>
</body>
</html>