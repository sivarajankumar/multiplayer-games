angular.module('reversi', []);

function ReversiController($scope) {
	var controller = this;
	$scope.players = this.players = [ {
		name : 'player1',
		score : 0
	}, {
		name : 'player2',
		score : 0
	} ];

	$scope.board = this.createBoard();

	$scope.resetBoard = function() {
		$scope.board = controller.createBoard();
		$scope.currentPlayerIndex = 0;
		$scope.currentPlayer = controller.players[0];
		$scope.otherPlayer = controller.players[1];
		$scope.winner = null;
		$scope.updateScore();
	};

	$scope.placePiece = function(rowIndex, columnIndex) {

		if ($scope.isLegalMove(rowIndex, columnIndex)) {

			$scope.board[rowIndex][columnIndex].player = $scope.currentPlayer;

			$scope.capturePieces(rowIndex, columnIndex);
			$scope.updateScore();

			if ($scope.isBoardFull()) {
				$scope.evaluateVictory();
			} else {
				// next player
				$scope.updatePlayers();
				if (!$scope.playerCanMove()){
					$scope.updatePlayers();
					if (!$scope.playerCanMove()){
						$scope.evaluateVictory();
					}
				}
			}
		}
	};

	$scope.draw = function(){
		$scope.winner = $scope.players;
		alert("draw!");
	};
	
	$scope.victory = function(){
		$scope.winner = $scope.currentPlayer;
		alert("victory for " + $scope.winner.name);
	};
	
	$scope.evaluateVictory = function(){
		if ($scope.otherPlayer.score == $scope.currentPlayer.score){
			$scope.draw();
			return;
		}
		if ($scope.otherPlayer.score > $scope.currentPlayer.score)
			$scope.updatePlayers();
		$scope.victory();
	};
	
	$scope.isBoardFull = function(){
		for (var row = 0; row<$scope.board.length; row++){
			for (var column = 0; column < $scope.board[row].length; column ++){
				if (!$scope.board[row][column].player)
					return false;
			}
		}
		return true;
	};
	
	$scope.updateScore = function(){
		for (var i=0; i<$scope.players.length; i++){
			var player = $scope.players[i];
			var score = 0;
			for (var row = 0; row<$scope.board.length; row++){
				for (var column = 0; column < $scope.board[row].length; column ++){
					if ($scope.board[row][column].player == player)
						score++;
				}
			}
			player.score = score;
		}
	};
	
	$scope.playerCanMove = function(){
		for (var row = 0; row<$scope.board.length; row++){
			for (var column = 0; column < $scope.board[row].length; column ++){
				if ( (! $scope.board[row][column].player ) && $scope.isLegalMove(row, column)){
							return true;
				}
			}
		}
		return false;
	};
	
	$scope.getPiecesInDirection = function(rowIndex, columnIndex, direction) {
		var left = direction == 'left' || direction == 'upper-left' || direction == 'lower-left';
		var right = direction == 'right' || direction == 'upper-right' || direction == 'lower-right';
		var up = direction == 'up' || direction == 'upper-left' || direction == 'upper-right';
		var down = direction == 'down' || direction == 'lower-left' || direction == 'lower-right';

		function getNextRowIndex(rowIndex) {
			if (up)
				return rowIndex - 1;
			if (down)
				return rowIndex + 1;
			return rowIndex;
		}

		function getNextColumnIndex(columnIndex) {
			if (left)
				return columnIndex - 1;
			if (right)
				return columnIndex + 1;
			return columnIndex;
		}

		var cells = [];
		var cellHasDisc = false;
		do {
			var rowIndex = getNextRowIndex(rowIndex);
			var columnIndex = getNextColumnIndex(columnIndex);
			cellHasDisc = this.board[rowIndex] && this.board[rowIndex][columnIndex] && this.board[rowIndex][columnIndex].player;
			if (cellHasDisc)
				cells.push(this.board[rowIndex][columnIndex]);
		} while (cellHasDisc);
		
		return cells;
		
	};

	$scope.capturePieces = function(rowIndex, columnIndex) {
		var directions = [ 'left', 'right', 'up', 'down', 'upper-left', 'lower-left', 'upper-right', 'lower-right' ];
		for (var i=0; i<directions.length; i++){
			var cells = $scope.getPiecesInDirection(rowIndex, columnIndex, directions[i]);
			// if neighbor belongs to other player
			if (cells.length > 1 && cells[0].player == $scope.otherPlayer){
				for (var j=1; j<cells.length; j++)
					// look for cell belonging to current player
					if (cells[j].player == $scope.currentPlayer){
						// turn cells between
						for (var k=0; k<j; k++){
							cells[k].player = $scope.currentPlayer;
						}
						break;
					}
			}
		}
	};

	$scope.isLegalMove = function(rowIndex, columnIndex) {
		var directions = [ 'left', 'right', 'up', 'down', 'upper-left', 'lower-left', 'upper-right', 'lower-right' ];
		for (var i=0; i<directions.length; i++){
			var cells = $scope.getPiecesInDirection(rowIndex, columnIndex, directions[i]);
			// if neighbor belongs to other player
			if (cells.length > 1 && cells[0].player == $scope.otherPlayer){
				for (var j=1; j<cells.length; j++)
					// look for cell belonging to current player
					if (cells[j].player == $scope.currentPlayer)
						return true;
			}
		}

		return false;
	};

	$scope.updatePlayers = function() {
		if ($scope.currentPlayerIndex == 0) {
			$scope.currentPlayerIndex = 1;
			$scope.currentPlayer = controller.players[1];
			$scope.otherPlayer = controller.players[0];
		} else {
			$scope.currentPlayerIndex = 0;
			$scope.currentPlayer = controller.players[0];
			$scope.otherPlayer = controller.players[1];
		}
	};
	
	$scope.resetBoard();
};

ReversiController.prototype.createBoard = function() {
	var board = [];

	for ( var i = 0; i < boardSize; i++) {
		var row = [];
		for ( var j = 0; j < boardSize; j++) {
			row.push({

			});
		}
		board.push(row);
	}

	if (alternateRules.initialSetup == 'diagonal') {
		this.diagonalSetup(board);
	} else if (alternateRules.initialSetup == 'horizontal') {
		this.horizontalSetup(board);
	}

	return board;
};

ReversiController.prototype.diagonalSetup = function(board) {
	// TODO generalize for larger boards
	board[3][3].player = this.players[1];
	board[3][4].player = this.players[0];
	board[4][4].player = this.players[1];
	board[4][3].player = this.players[0];
};

ReversiController.prototype.horizontalSetup = function(board) {
	// TODO generalize for larger boards
	board[3][3].player = this.players[1];
	board[3][4].player = this.players[1];
	board[4][4].player = this.players[0];
	board[4][3].player = this.players[0];
};