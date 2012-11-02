package persistence;

import java.util.Collection;

import model.Game;

public interface GameRepository {

	Collection<Game> getGames();
	
	Game getGameByGameId(String gameId);
}
