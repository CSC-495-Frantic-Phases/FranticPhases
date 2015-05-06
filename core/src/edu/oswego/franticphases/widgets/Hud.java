package edu.oswego.franticphases.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import edu.oswego.franticphases.screens.GameScreen;


public class Hud extends Window {


	private final Label phaseDisplay;
	private final Label scoreDisplay;
	private final GameScreen screen;
	

	
	public Hud(final GameScreen screen, final Skin skin, AssetManager assetManager) {
		super("", skin);
		this.screen = screen;
		row().uniform().expandX();
		//add("Hand ").right().padLeft(10);

		row().uniform().expandX();
		Image shuffleImage = new Image(assetManager.get("data/WorldObjects/shuffle.png", Texture.class));
		add(shuffleImage).left().fill(false).padLeft(10);
		shuffleImage.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				//Gdx.app.log("GameScreen", "Shuffle");
				screen.shuffle();
				return true;
			}
		});
		
		add("Phase: ").right().padLeft(10);
		phaseDisplay = new Label("", skin);
		add(phaseDisplay).left();
		add("Score: ").right();
		scoreDisplay = new Label("", skin);
		add(scoreDisplay).left();


		
		Image pauseImage = new Image(assetManager.get("data/ui/unpacked/PauseButton2.png", Texture.class));
		add(pauseImage).right().fill(false).padRight(10);
		pauseImage.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				screen.pause();
				return true;
			}
		});
	}
	
	public void setPhase(String phase) {
		phaseDisplay.setText(phase);
	}

	public void setPhase(int phase) {
		setPhase(String.valueOf(phase));
	}

	public void setScore(String score) {
		scoreDisplay.setText(score);
		//timerDisplay.setText(score.getFormattedTime());
	}
	

	
}
