package edu.oswego.franticphases.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import edu.oswego.franticphases.FranticPhases;
import edu.oswego.franticphases.datasending.DataSender;
import edu.oswego.franticphases.datasending.WebCallback;
import edu.oswego.franticphases.settings.Settings;


public class LoginScreen extends AbstractScreen implements TextInputListener{
	private boolean state = false;
	
	private String username;
	private String password;
	
	WebCallback accountCreationCB;
	
	private boolean waitingForResponse = false;
	public LoginScreen(FranticPhases game) {
		super(game);
	}

	@Override
	public void show() {
		Window window = new Window("\nLogin Screen", skin);
        window.setFillParent(true);
        window.setModal(true);
        window.setMovable(false);
        stage.addActor(window);
        
        state = false;
		waitingForResponse = false;
		username = "";
		password = "";
		accountCreationCB = new WebCallback();
		
		Gdx.input.getTextInput(this, "Enter Username", "", "hint");
		
	}

	@Override
	public void input(String text) {
		if (!state){
			username = text;
			Gdx.input.getTextInput(this, "Enter Password", "", "hint");
			state = !state;
		}else{
			password = text;
			
			DataSender aSender = new DataSender();
			aSender.login(username, password, accountCreationCB);
			waitingForResponse = true;
		}
		
	}

	@Override
	public void canceled() {
		game.showMainScreen();
		
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
		
		if (waitingForResponse){
			if (accountCreationCB.getRecieved()){
				if(accountCreationCB.getResult()){
					Settings.setUsername(username);
					Settings.setPassword(password);
					
					waitingForResponse = false;
					game.showGameSelectionScreen();
				}else{
					waitingForResponse = false;
					game.showMainScreen();
					//show some sort of popup that login failed
				}
			}
		}
	}

}
