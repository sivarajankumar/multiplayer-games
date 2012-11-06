package control.welcome;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import model.Game;
import model.GameOption;
import persistence.GameRepository;
import persistence.mock.GameRepositoryMock;
import services.GameRegistry;

import com.google.gson.Gson;

public class WelcomeScreenController {

	private Gson gson = new Gson();

	private GameRegistry gameService;

	// TODO dependency injection
	private GameRepository gameRepository = new GameRepositoryMock();

	public WelcomeScreenController() {
		gameService = GameRegistry.getInstance();
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
		return gson.toJson(gameService.getGameDTOsForOpenGames());
	}

}
