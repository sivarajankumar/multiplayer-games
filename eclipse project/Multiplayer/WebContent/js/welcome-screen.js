angular.module('welcome-screen', [ 'uiComponents' ]);

function WelcomeScreenController($scope, $http) {

	var controller = this;
	this.user = loggedInUser;

	// configure create game select
	$scope.availableGames = availableGames;
	$scope.selectedGame = availableGames[0];

	// configure data table
	$scope.games = games;
	$scope.columns = [ {
		sTitle : "Player",
		"mData" : "playerName"
	}, {
		sTitle : "Game",
		"mData" : "gameTitle"
	}, {
		sTitle : "",
		"mData" : null,
		"mRender" : function(data, type, row) {
			return '<button class="join-game-button">Join!</button>';
		}
	} ];
	$scope.assignEvents = function(dataTable) {
		dataTable.on("click", "tr", function() {
			var data = dataTable.fnGetData(this);
			$scope.$apply(function() {
				$scope.selectedOpenGame = data;
			});
		});
		dataTable.on("click", "button", function() {
			$.blockUI();
			var data = dataTable.fnGetData($(this).parents('tr').get(0));
			$.ajax({
				type : 'GET',
				url : "join-game",
				data : "playerName=" + data.playerName + "&gameId=" + data.gameId,
				dataType : 'json',
				success : function(data) {
					controller.processRedirectResponse(data);
				}
			});
		});
	};

	$scope.createGame = function() {
		$.blockUI();
		$http({
			method : 'POST',
			url : "create-game",
			data : {
				"gameType" : $scope.selectedGame.id,
				"gameParameters" : {
					boardSize : 8,
					initialSetup : 'diagonal'
				}
			}
		}).success(function() {
			controller.connectToLoggedInUserChannel();
		});
	};

	// handle incoming open games list
	window.receiveUpdatedGameList = function(response) {
		$scope.$apply(function() {
			$scope.games = JSON.parse(response);
		});
	};
	new PushClient("games", receiveUpdatedGameList).connect();
}

WelcomeScreenController.prototype.connectToLoggedInUserChannel = function() {
	var controller = this;
	new PushClient(this.user, function(response) {
		var jsonResponse = JSON.parse(response);
		controller.processRedirectResponse(jsonResponse);
	}).connect();
};

WelcomeScreenController.prototype.processRedirectResponse = function(response) {
	window.location.href = response.page + "?player=" + response.player + "&opponent=" + response.opponent + "&begin=" + response.begin ;
};
