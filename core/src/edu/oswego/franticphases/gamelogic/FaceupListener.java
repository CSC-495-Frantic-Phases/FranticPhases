package edu.oswego.franticphases.gamelogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import edu.oswego.franticphases.screens.GameScreen;

public class FaceupListener extends InputListener{

	GameScreen screen;
	
	public FaceupListener(GameScreen s){
		screen = s;
	}
	@Override
	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		Gdx.app.log("FaceupListener", "clicked");
		screen.faceUpCard();
		
		return true;
	}
	
	
}
