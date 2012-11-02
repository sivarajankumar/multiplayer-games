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
		String description = "Tic-tac-toe, originally called noughts and crosses (and still known as this in Britain and Australia), Xs and Os (in Ireland) and X and 0 (in India) is a pencil-and-paper game for two players, X and O, who take turns marking the spaces in a 3x3 grid. The player who succeeds in placing three respective marks in a horizontal, vertical, or diagonal row wins the game.";
		x0.setDescription(description);
		
		games.add(x0);
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
