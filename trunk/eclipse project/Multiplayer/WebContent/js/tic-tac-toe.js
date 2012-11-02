angular.module('tic-tac-toe', []);

function TicTacToeController($scope, $http) {
	var controller = this;

	// parse URL params
	var params = $.url().param();
	// server handles sending
	// var sendChannel = params.player + 'vs' + params.opponent;
	var receiveChannel = params.opponent + 'vs' + params.player;
	// URL
	var url = 'game-command';

	// subscribe for opponent's moves
	var cometCallback = function(response) {
		var responseObj = JSON.parse(response);
		if (responseObj.commandType == 'move') {
			var move = responseObj.payload;
			$scope.$apply(function() {
				$scope.board[move.row][move.column].content = $scope.opponent;
				$scope.checkVictory();
			});
		} else if (responseObj.commandType == 'quit-game') {
			controller.showMessageDialog("Your opponent has left the game!", function() {
				controller.goToLobby();
			});
		} else if (responseObj.commandType == 'restart-game') {
			controller.showMessageDialog("Your opponent has restarted the game!", function() {
				controller.hideDialogs();
			});
			$scope.$apply(function() {
				$scope.resetBoard();
			});
		}
	};
	new PushClient(receiveChannel, function(response) {
		cometCallback(response);
	}).connect();

	$scope.playAgain = function() {
		$http({
			method : 'POST',
			url : url,
			data : {
				commandType : 'restart-game',
				player : params.player,
				opponent : params.opponent
			}
		}).success(function() {
			console.log("restart posted");
		});
		controller.hideDialogs();
		$scope.resetBoard();
	};

	$scope.backToLobby = function() {
		$http({
			method : 'POST',
			url : url,
			data : {
				commandType : 'quit-game',
				player : params.player,
				opponent : params.opponent
			}
		}).success(function() {
			console.log("quit posted");
		});
		controller.goToLobby();
	};

	// see which player is which side - X always begins
	$scope.players = [ 'X', '0' ];
	if (params.begin == 'true') {
		$scope.player = $scope.players[0];
		$scope.opponent = $scope.players[1];
	} else {
		$scope.player = $scope.players[1];
		$scope.opponent = $scope.players[0];
	}

	// setup board
	$scope.resetBoard = function() {
		$scope.board = controller.createBoard(boardSize);
		$scope.currentPlayerIndex = 0;
		$scope.winner = null;
	};
	$scope.resetBoard();

	// click on a cell
	$scope.placeSymbol = function(row, column) {
		if ($scope.winner || $scope.board[row][column].content || $scope.players[$scope.currentPlayerIndex] != $scope.player)
			return;

		// do move locally
		$scope.board[row][column].content = $scope.players[$scope.currentPlayerIndex];

		// post move to server
		$http({
			method : 'POST',
			url : url,
			data : {
				payload : {
					row : row,
					column : column,
				},
				commandType : 'move',
				player : params.player,
				opponent : params.opponent
			}
		}).success(function() {
			console.log("move posted");
		});

		// check victory
		$scope.checkVictory();
	};

	$scope.checkVictory = function() {
		if ($scope.currentPlayerIsVictorious()) {
			$scope.winner = $scope.players[$scope.currentPlayerIndex];
			$scope.victoryText = $scope.winner + " wins!";
			controller.displayVictoryDialog();
		} else if ($scope.boardIsFull()) {
			$scope.winner = 'noone';
			$scope.victoryText = 'Draw!';
			controller.displayVictoryDialog();
		} else {
			$scope.nextPlayer();
		}
	};

	$scope.boardIsFull = function() {
		for ( var i = 0; i < $scope.board.length; i++) {
			for ( var j = 0; j < $scope.board.length; j++) {
				if (!$scope.board[i][j].content)
					return false;
			}
		}
		return true;
	};

	$scope.nextPlayer = function() {
		if (this.currentPlayerIndex == 0)
			this.currentPlayerIndex = 1;
		else
			this.currentPlayerIndex = 0;
	};

	$scope.currentPlayerIsVictorious = function() {
		var board = $scope.board;
		var length = board.length;

		// check rows
		for ( var i = 0; i < length; i++) {
			var row = board[i];
			// check against first value
			var firstValueInRow = row[0].content;
			// if nothing in first cell, the row can't be full
			if (!firstValueInRow)
				break;
			// go through the cells in the row
			var rowFullSame = true;
			for ( var c = 1; c < length && rowFullSame; c++) {
				var cell = row[c];
				if (cell.content != firstValueInRow) {
					rowFullSame = false;
				}
			}
			if (rowFullSame)
				return true;
		}

		// check columns
		for ( var columnCounter = 0; columnCounter < length; columnCounter++) {
			// check against first value
			var firstValueInColumn = board[0][columnCounter].content;
			// if nothing in first cell, the row can't be full
			if (!firstValueInColumn)
				break;
			// go through the cells in the column
			var columnFullSame = true;
			for ( var rowCounter = 1; rowCounter < length && columnFullSame; rowCounter++) {
				var cell = board[rowCounter][columnCounter];
				if (cell.content != firstValueInColumn) {
					columnFullSame = false;
				}
			}
			if (columnFullSame)
				return true;
		}

		// check descending (first) diagonal
		var firstValueInDescendingDiagonal = board[0][0].content;
		if (firstValueInDescendingDiagonal) {
			var diagonalSame = true;
			for ( var diagonalCounter = 1; diagonalCounter < length && diagonalSame; diagonalCounter++) {
				var cell = board[diagonalCounter][diagonalCounter];
				if (cell.content != firstValueInDescendingDiagonal) {
					diagonalSame = false;
				}
			}
			if (diagonalSame)
				return true;
		}

		// check descending (second) diagonal
		var firstValueInAscendingDiagonal = board[0][length - 1].content;
		if (firstValueInAscendingDiagonal) {
			var diagonalSame = true;
			for ( var rowCounter = 1, columnCounter = length - 2; rowCounter < length && diagonalSame; rowCounter++, columnCounter--) {
				var cell = board[rowCounter][columnCounter];
				if (cell.content != firstValueInAscendingDiagonal) {
					diagonalSame = false;
				}
			}
			if (diagonalSame)
				return true;
		}

		return false;
	};

}

TicTacToeController.prototype.createBoard = function(length) {
	var board = [];

	for ( var i = 0; i < length; i++) {
		var row = [];
		for ( var j = 0; j < length; j++) {
			row.push({});
		}
		board.push(row);
	}

	return board;
};

TicTacToeController.prototype.displayVictoryDialog = function() {
	$('#victoryDialog').dialog({
		modal : true
	});
};

TicTacToeController.prototype.hideDialogs = function() {
	try {
		$('#victoryDialog').dialog("close");
	} catch (dialogNotInitializedException) {
	}
	try {
		$("#messageDialog").dialog(" close");
	} catch (dialogNotInitializedException) {
	}

};

TicTacToeController.prototype.goToLobby = function() {
	window.location.href = "welcome-screen.jsp";
};

TicTacToeController.prototype.showMessageDialog = function(message, callback) {
	$('#game-message').html(message);
	$("#messageDialog").dialog({
		modal : true,
		buttons : {
			OK : function() {
				$(this).dialog("close");
			}
		},
		close : function() {
			if (callback)
				callback();
		}
	});

};
