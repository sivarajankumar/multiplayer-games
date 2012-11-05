package web;

import javax.servlet.http.HttpServletRequest;

import services.GameRegistry;

import com.google.gson.Gson;

import constants.Constants;

public class GameListUpdater {

	// TODO dependency injection
	private GameRegistry gameRegistry = GameRegistry.getInstance();
	
	private Gson gson = new Gson();
	
	public void updateOpenGamesList(HttpServletRequest request, String... excludeUsers){
		String gamesList = gson.toJson(gameRegistry.getGameDTOsForOpenGames());
		String channel = Constants.GAMES_CHANNEL;
		
		ChannelUpdater channelUpdater = new ChannelUpdater();
		channelUpdater.sendMessageOnChannel(request, gamesList, channel, excludeUsers);
	}

}
