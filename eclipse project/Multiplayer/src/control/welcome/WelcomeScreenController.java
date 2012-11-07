package control.welcome;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import model.Game;
import model.GameOption;
import persistence.GameRepository;
import services.GameRegistry;

import com.google.gson.Gson;

public class WelcomeScreenController {

	private Gson gson = new Gson();

	private GameRegistry gameRegistry = GameRegistry.getInstance();

	private GameRepository gameRepository;
	public void setGameRepository(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	static class GameDTO {
		String id;
		String name;
		List<GameOption> options;
	}
	
	public String getAvailableGames() {
		Collection<Game> games = gameRepository.getGames();
		List<GameDTO> gameDTOs = new LinkedList<>();
		for (Game game : games){
			GameDTO dto = new GameDTO();
			dto.id = game.getGameId();
			dto.name = game.getShortName();
			dto.options = game.getGameOptions();
			gameDTOs.add(dto);
		}
		return gson.toJson(gameDTOs);
	}

	public String getOpenGames() {
		return gson.toJson(gameRegistry.getGameDTOsForOpenGames());
	}

}
