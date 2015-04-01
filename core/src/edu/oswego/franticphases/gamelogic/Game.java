package edu.oswego.franticphases.gamelogic;

import com.badlogic.gdx.utils.JsonValue;

import edu.oswego.franticphases.settings.Settings;

public class Game {

	Player[] players;
	String gameID;
	String deckID;
	String faceUpCard;
	String faceDownCard;
	String userPlayerNum;
	
	public Game(JsonValue json){
		gameID = json.getString("gameID");
		deckID = json.getString("deckID");
		faceUpCard = json.getString("faceUpCard");
		JsonValue tmp = json.get("players");
		players = new Player[4];
		for(int i = 0; i < tmp.size; i++){
			JsonValue pl = tmp.get(i);
			String name = pl.getString("userName");
			
			String score = pl.getString("score");
			String phase = pl.getString("phase");
			String playerNum = pl.getString("playerNum");
			if(name.equals(Settings.getUsername())){
				userPlayerNum = playerNum;
			}
			Player player = new Player(name, score, phase, playerNum);
			players[i] = player;
		}
	}
	
	public String getGameID(){
		return gameID;
	}
	
	public String getUserPlayerNum(){
		return userPlayerNum;
	}
	
	public String getDeckID(){
		return deckID;
	}
	
	public String getFaceUpCard(){
		return faceUpCard;
	}
	
	public void setFaceUpCard(String card){
		faceUpCard = card;
	}
	
	public String getPlayer1Name(){
		return players[0].getUserName();
	}
	public String getPlayer2Name(){
		return players[1].getUserName();
	}
	public String getPlayer3Name(){
		return players[2].getUserName();
	}
	public String getPlayer4Name(){
		return players[3].getUserName();
	}
	
}
