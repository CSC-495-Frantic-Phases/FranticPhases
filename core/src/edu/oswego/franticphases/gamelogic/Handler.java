package edu.oswego.franticphases.gamelogic;

import com.badlogic.gdx.utils.JsonValue;

import edu.oswego.franticphases.screens.GameScreen;
import edu.oswego.franticphases.screens.GameSelectScreen;

public class Handler {
private GameSelectScreen gsScreen;
private JsonValue gamesJson;
	public Handler(GameSelectScreen screen){
		gsScreen = screen;
		
	}
	
	public void sendUsersToGameScreen(JsonValue data){
		for(int i =0; i< data.size;i++){
			JsonValue jvtmp = data.get(i);
			gsScreen.userData.row();
			gsScreen.userData.add("ID: " + jvtmp.getString("id"));
			gsScreen.userData.row();
			gsScreen.userData.add("UserName: " + jvtmp.getString("username"));
		}
		
	}
	
	public void setGamesJson(JsonValue j){
		gamesJson = j;
		System.out.println("You are in "+j.size + " games." );
	}
	
	public int getNumGames(){
		return gamesJson.size;
	}
	
	
}
