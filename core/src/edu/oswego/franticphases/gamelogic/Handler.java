package edu.oswego.franticphases.gamelogic;

import com.badlogic.gdx.utils.JsonValue;

import edu.oswego.franticphases.screens.GameScreen;

public class Handler {
private GameScreen gameScreen;
	public Handler(GameScreen screen){
		gameScreen = screen;
		
	}
	
	public void sendUsersToGameScreen(JsonValue data){
		for(int i =0; i< data.size;i++){
			JsonValue jvtmp = data.get(i);
			gameScreen.userData.row();
			gameScreen.userData.add("ID: " + jvtmp.getString("id"));
			gameScreen.userData.row();
			gameScreen.userData.add("UserName: " + jvtmp.getString("username"));
		}
		
	}
	
	
}
