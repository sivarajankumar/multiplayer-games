package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import services.GameRegistry;
import web.GameListUpdater;

import com.google.gson.Gson;

import constants.Keys;

/**
 * Servlet implementation class GameCreator
 */
@WebServlet(name = "gameCreatorServlet", urlPatterns = { "/create-game" }, asyncSupported = true)
public class GameCreator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// TODO dependency injection
	private GameRegistry gameRegistry = GameRegistry.getInstance();
	private GameListUpdater gameListUpdater = new GameListUpdater();

	private Gson gson = new Gson();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GameCreator() {
		super();
	}

	public static class GameCreateCommand {
		public Object gameOptions;
		public String gameType;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// get JSON data
		StringBuilder sb = new StringBuilder();
		String commandString;
		while ((commandString = request.getReader().readLine()) != null) {
			sb.append(commandString);
		}
		commandString = sb.toString();
		// deserialize to find out which player and which opponent
		GameCreateCommand createCommand = gson.fromJson(commandString, GameCreateCommand.class);

		User user = (User) request.getSession().getAttribute(Keys.SESSION_USER);

		gameRegistry.addCreatedGame(user.getUserName(), createCommand);

		gameListUpdater.updateOpenGamesList(request);
	}

}
