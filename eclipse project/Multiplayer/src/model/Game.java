package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Game {

	private String gameId;
	private String shortName;
	private String name;
	private String description;

	private List<GameParameter> parameters;

	public void addGameParameter(GameParameter parameter) {
		if (parameters == null)
			parameters = new LinkedList<>();
		parameters.add(parameter);
	}

	public List<GameParameter> getGameParameters() {
		return Collections.unmodifiableList(parameters);
	}

	public String getGameId() {
		return gameId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
