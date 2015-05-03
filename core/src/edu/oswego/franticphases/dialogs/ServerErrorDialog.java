package edu.oswego.franticphases.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import edu.oswego.franticphases.FranticPhases;



public class ServerErrorDialog extends Dialog {
	private final FranticPhases game;
	private static enum Buttons {
		RETRY
	}
	
	public ServerErrorDialog(String title, Skin skin, FranticPhases game, String message) {
		super(title, skin, "dialog");
		this.game = game;
		padTop(50);
        setModal(true);
        setMovable(false);
        
        getContentTable().row().uniform().fill();
        text(message);
	
        getButtonTable().row().uniform().fill();
        button("Login", Buttons.RETRY);
        

		
	}
	
	@Override
	protected void result(Object object) {

		super.result(object);
		Gdx.app.log("dialog result", "" + object);
		if (object != null && object instanceof Buttons) {
			Buttons b = (Buttons)object;
			if (b == Buttons.RETRY){
				game.loadGameData();
			}
		} else {
			
		}

	}

}
