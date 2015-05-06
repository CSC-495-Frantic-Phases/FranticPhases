package edu.oswego.franticphases.gamelogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import edu.oswego.franticphases.screens.GameScreen;

public class DeckListener extends InputListener {

	GameScreen screen;
	
	public DeckListener(GameScreen s){
		screen = s;
	}
	@Override
	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		Gdx.app.log("DeckListener", "clicked");
		screen.deck();
		
		return true;
	}
}
