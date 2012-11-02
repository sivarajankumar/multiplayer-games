package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import web.ChannelUpdater;

/**
 * Servlet implementation class MoveDispatcherServlet
 */
@WebServlet(name = "gameCommandDispatcherServlet", urlPatterns = { "/game-command" }, asyncSupported = true)
public class GameCommandDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameCommandDispatcherServlet() {
        super();
    }

    static class GameCommand {
    	Object payload;
    	String player;
    	String opponent;
    	String commandType;
    }
    
    private Gson gson = new Gson();
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
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
		GameCommand move = gson.fromJson(commandString, GameCommand.class);
		// send move to opponent
		ChannelUpdater channelUpdater = new ChannelUpdater();
		channelUpdater.sendMessageOnChannel(request, commandString, move.player + "vs" + move.opponent);
	}

}
