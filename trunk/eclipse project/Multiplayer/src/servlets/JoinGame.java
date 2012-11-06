package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import services.GameRegistry;
import servlets.GameCreator.GameCreateCommand;
import web.ChannelUpdater;
import web.GameListUpdater;

import com.google.gson.Gson;

import constants.Keys;

/**
 * Servlet implementation class JoinGame
 */
@WebServlet(name = "gameJoinerServlet", urlPatterns = { "/join-game" }, asyncSupported = true)
public class JoinGame extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// TODO dependency injection
	private GameRegistry gameService = GameRegistry.getInstance();
	private GameListUpdater gameListUpdater = new GameListUpdater();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JoinGame() {
		super();
	}

	static class GameStart {
		String page;
		String opponent;
		String player;
		boolean begin;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String gameCreator = request.getParameter("playerName");
		User player = (User) request.getSession().getAttribute(Keys.SESSION_USER);
		String gameJoiner = player.getUserName();

		if (gameCreator.equals(gameJoiner)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		String gameId = request.getParameter("gameId");

		// remove started game from list of games
		GameCreateCommand command = gameService.removeOpenGameForUserName(gameCreator);
		// add options
		gameService.setGameOptions(gameCreator, gameJoiner, command.gameOptions);
		// refresh game list
		gameListUpdater.updateOpenGamesList(request, gameCreator, gameJoiner);

		GameStart game = new GameStart();
		game.page = gameId + ".jsp";

		// redirect creator
		game.player = gameCreator;
		game.opponent = gameJoiner;
		// TODO hardcoded who begins
		game.begin = true;
		String gameStartForCreator = new Gson().toJson(game);

		ChannelUpdater channelUpdater = new ChannelUpdater();
		channelUpdater.sendMessageOnChannel(request, gameStartForCreator, gameCreator);

		// redirect joiner
		game.player = gameJoiner;
		game.opponent = gameCreator;
		game.begin = false;
		String gameStartForJoiner = new Gson().toJson(game);

		response.getOutputStream().print(gameStartForJoiner);
		response.flushBuffer();
	}

}
