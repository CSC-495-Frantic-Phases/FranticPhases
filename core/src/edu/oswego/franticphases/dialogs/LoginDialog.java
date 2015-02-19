package edu.oswego.franticphases.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import edu.oswego.franticphases.FranticPhases;

public class LoginDialog extends Dialog {

	private final FranticPhases game;
	private static enum Buttons {
		LOGIN,
		CREATEACCOUNT
	}
	
	public LoginDialog(String title, Skin skin, FranticPhases game) {
		super(title, skin, "dialog");
		this.game = game;
		padTop(50);
        setModal(true);
        setMovable(false);
	
        getButtonTable().row().uniform().fill();
        button("Login", Buttons.LOGIN);
        getButtonTable().row().uniform().fill();
        button("Create Account", Buttons.CREATEACCOUNT);
	
	
}
	
	@Override
	protected void result(Object object) {

		super.result(object);
		Gdx.app.log("dialog result", "" + object);
		if (object != null && object instanceof Buttons) {
			Buttons b = (Buttons)object;
			if (b == Buttons.LOGIN){
				game.showLoginScreen();
			}else if(b == Buttons.CREATEACCOUNT){
				game.showCreateAccountScreen();
			}
		} else {
			
		}

	}
}
