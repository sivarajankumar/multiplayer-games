package services;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Game;
import persistence.GameRepository;
import persistence.mock.GameRepositoryMock;
import servlets.GameCreator.GameCreateCommand;

public class GameRegistry {

	private static GameRegistry instance = new GameRegistry();

	public static GameRegistry getInstance() {
		return instance;
	}

	private Map<String, GameCreateCommand> gameMap = new HashMap<>();
	private Map<String, Object> gameOptionsMap = new HashMap<>();

	// TODO dependency injection
	private GameRepository gameRepository = new GameRepositoryMock();

	private GameRegistry() {
		// singleton
	}

	public void addCreatedGame(String userName, GameCreateCommand createCommand) {
		gameMap.put(userName, createCommand);
	}
	
	public GameCreateCommand removeOpenGameForUserName(String gameCreator) {
		return gameMap.remove(gameCreator);
	}
	
	public void setGameOptions(String player1, String player2, Object options){
		gameOptionsMap.put(player1, options);
		gameOptionsMap.put(player2, options);
	}

	public Object getGameCreateOptionsForUser(String userName) {
		return gameOptionsMap.get(userName);
	}

	static class GameDTO {
		String playerName;

		String gameId;
		String gameName;
		String gameDescription;
		String gameTitle;

		Object parameters;

		public GameDTO(String userName, Game game, Object gameParameters) {
			playerName = userName;
			gameId = game.getGameId();
			gameTitle = game.getShortName();
			gameName = game.getName();
			gameDescription = game.getDescription();
			parameters = gameParameters;
		}
	}

	public List<GameDTO> getGameDTOsForOpenGames() {

		List<GameDTO> games = new LinkedList<>();

		for (Entry<String, GameCreateCommand> entry : gameMap.entrySet()) {
			GameCreateCommand gameCreateCommand = entry.getValue();
			String gameId = gameCreateCommand.gameType;
			GameDTO dto = new GameDTO(entry.getKey(), gameRepository.getGameByGameId(gameId), gameCreateCommand.gameOptions);
			games.add(dto);
		}
		return games;
	}

}
