package control.game;

import com.google.gson.Gson;

import model.User;
import services.GameRegistry;

public class GameController {
	
	// TODO Dependency injection
	private GameRegistry gameRegistry = GameRegistry.getInstance();
	
	private Gson gson = new Gson();
	
	public String getGameOptions(User u){
		return gson.toJson(gameRegistry.getGameCreateOptionsForUser(u.getUserName()));
	}

}
