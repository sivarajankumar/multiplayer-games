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
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GameCreator() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String gameType = request.getParameter("game-type");
		User user = (User) request.getSession().getAttribute(Keys.SESSION_USER);
		
		gameRegistry.addCreatedGame(user, gameType);
		
		gameListUpdater.updateOpenGamesList(request);
	}

}
