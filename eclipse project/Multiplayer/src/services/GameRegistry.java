package services;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Game;
import model.User;
import persistence.GameRepository;
import persistence.mock.GameRepositoryMock;

public class GameRegistry {

	private static GameRegistry instance = new GameRegistry();

	public static GameRegistry getInstance() {
		return instance;
	}

	private Map<User, String> gameMap = new HashMap<>();

	// TODO dependency injection
	private GameRepository gameRepository = new GameRepositoryMock();
	
	private GameRegistry(){
		// singleton
	}

	public void addCreatedGame(User user, String gameType) {
		gameMap.put(user, gameType);
	}
	
	public void removeOpenGameForUser(User user) {
		gameMap.remove(user);
	};
	
	public void removeOpenGameForUserName(String gameCreator) {
		User user = null;
		for (User u : gameMap.keySet()){
			if (u.getUserName().equals(gameCreator)){
				user = u;
				break;
			}
		}
		if (user != null)
			gameMap.remove(user);
	}

	static class GameDTO {
		String playerName;
		
		String gameId;
		String gameName;
		String gameDescription;
		String gameTitle;
		
		public GameDTO(String userName, Game game) {
			playerName = userName;
			gameId = game.getGameId();
			gameTitle = game.getShortName();
			gameName = game.getName();
			gameDescription = game.getDescription();
		}
	}

	public List<GameDTO> getGameDTOs() {

		List<GameDTO> games = new LinkedList<>();

		for (Entry<User, String> entry : gameMap.entrySet()) {
			String gameId = entry.getValue();
			GameDTO dto = new GameDTO(entry.getKey().getUserName(), gameRepository.getGameByGameId(gameId));
			games.add(dto);
		}
		return games;
	}

}
