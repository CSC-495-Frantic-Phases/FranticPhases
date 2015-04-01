package edu.oswego.franticphases.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import edu.oswego.franticphases.FranticPhases;
import edu.oswego.franticphases.screens.GameScreen;


public class PauseDialog extends Dialog {
	private final GameScreen screen;
	private final FranticPhases game;
	//private final Sound button;
	private static enum Buttons {
		QUIT,
		SETTINGS,
		CONTINUE
	}

	public PauseDialog(String title, Skin skin, GameScreen screen, final FranticPhases game) {
		super(title, skin, "dialog");
		this.screen = screen;
		this.game = game;
		padTop(50);
        setModal(true);
        setMovable(false);


        getButtonTable().row().uniform().fill();
        button("Quit", Buttons.QUIT);
        getButtonTable().row().uniform().fill();
        button("Settings", Buttons.SETTINGS);
        getButtonTable().row().uniform().fill();
		button("Continue", Buttons.CONTINUE);
		this.addListener(new InputListener(){
			@Override
			public boolean keyDown(InputEvent event, int keycode){
				 if(keycode == Keys.BACK){
				   	 game.showPreviousScreen();
				   	 return true;
				 }
				return false;
			}
		});
	}

	@Override
	protected void result(Object object) {

		super.result(object);
		Gdx.app.log("dialog result", "" + object);
		if (object != null && object instanceof Buttons) {
			//button.play();
			Buttons b = (Buttons)object;
			if (b == Buttons.QUIT) {
				game.showPreviousScreen();
			} else if (b == Buttons.SETTINGS) {
				game.showSettingsScreen();
			} else if (b == Buttons.CONTINUE) {
				screen.resume();
			}
		} else {
			screen.resume();
		}

	}
}

