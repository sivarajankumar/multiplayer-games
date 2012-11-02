package persistence.mock;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import model.Game;
import persistence.GameRepository;

public class GameRepositoryMock implements GameRepository {

	private List<Game> games = new LinkedList<>();

	public GameRepositoryMock() {
		Game x0 = new Game();
		x0.setGameId("x-0");
		x0.setShortName("X and 0");
		x0.setName("Tic-tac-toe - X and 0");
		String x0Description = "Tic-tac-toe, originally called noughts and crosses (and still known as this in Britain and Australia), Xs and Os (in Ireland) and X and 0 (in India) is a pencil-and-paper game for two players, X and O, who take turns marking the spaces in a 3x3 grid. The player who succeeds in placing three respective marks in a horizontal, vertical, or diagonal row wins the game.";
		x0.setDescription(x0Description);
		
		Game reversi = new Game();
		reversi.setGameId("reversi");
		reversi.setShortName("Reversi");
		reversi.setName("Reversi");
		String reversiDescription = "Reversi is a strategy board game for two players, played on an 8x8 uncheckered board. There are 64 identical pieces called 'disks' (often spelled 'discs'), which are light on one side and dark on the other to correspond with the opponents in a game. Players take alternate turns by placing a piece of their own color on the board, in such a position that there exists at least one straight (horizontal, vertical, or diagonal) occupied line between the new piece and another self-owned piece, with one or more contiguous pieces of the opponent between them, which are thus 'captured'. Each player's objective is generally to have as many disks one's own color at the end as possible and for one's opponent to have as few.";
		reversi.setDescription(reversiDescription);
		
		games.add(x0);
		games.add(reversi);
	}

	@Override
	public Collection<Game> getGames() {
		return games;
	}

	@Override
	public Game getGameByGameId(String gameId) {
		for (Game game : games) {
			if (game.getGameId().equals(gameId)) {
				return game;
			}
		}
		return null;
	}

}
