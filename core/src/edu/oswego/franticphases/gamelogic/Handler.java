package edu.oswego.franticphases.gamelogic;

import java.util.ArrayList;

import com.badlogic.gdx.utils.JsonValue;

import edu.oswego.franticphases.GameSession;
import edu.oswego.franticphases.User;
import edu.oswego.franticphases.screens.GameScreen;
import edu.oswego.franticphases.screens.GameSelectScreen;

public class Handler {
	private ArrayList<User> users;
	private ArrayList<GameSession> games;
	private String newGameID;
	private boolean usersUpdated = false;
	private boolean gamesUpdated = false;
	private boolean newGameUpdated = false;

	public Handler(){
		users = new ArrayList<User>();
		games = new ArrayList<GameSession>();
		
	}
	
	public boolean isUsersUpdated(){
		return usersUpdated;
	}
	
	public boolean isGamesUpdated(){
		return gamesUpdated;
	}
	
	public boolean isNewGameUpdated(){
		return  newGameUpdated;
	}
	
	public void setNewGame(String id){
		newGameID = id;
		newGameUpdated = true;
			
	}
	
	public String getNewGameID(){
		newGameUpdated = false;
		return newGameID;
		
	}

	
	public void setGameSessionArray(JsonValue json){
		if(json == null){
			gamesUpdated = true;
			return;
		}
		for(int i = 0; i < json.size; i++){
			JsonValue tmp = json.get(i);
			String game = tmp.getString("gameID");
			JsonValue players = tmp.get("players");
			String pl;
			GameSession session = new GameSession(game);
			for(int j = 0; j < players.size; j++){
				JsonValue pltmp = players.get(j);
				pl = pltmp.getString("userName");
				session.addPlayer(j, pl);
			}
			
			games.add(session);
		}
		gamesUpdated = true;
	}
	
	public void setUserListArray(JsonValue json){
		for(int i = 0; i < json.size; i++){
			JsonValue tmp = json.get(i);
			String name = tmp.getString("username");
			String id = tmp.getString("userID");
			User newUser = new User(name, id);
			users.add(newUser);
		}
		usersUpdated = true;
	}
	
	public ArrayList<User> getUsers(){
		usersUpdated = false;
		return users;
	}
	
	public ArrayList<GameSession> getGames(){
		gamesUpdated = false;
		return games;
	}
	
	
	
}
