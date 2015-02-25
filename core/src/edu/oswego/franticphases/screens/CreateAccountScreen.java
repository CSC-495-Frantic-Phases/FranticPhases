package edu.oswego.franticphases.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import edu.oswego.franticphases.FranticPhases;
import edu.oswego.franticphases.Settings;
import edu.oswego.franticphases.datasending.DataSender;
import edu.oswego.franticphases.datasending.WebCallback;


public class CreateAccountScreen extends AbstractScreen implements TextInputListener {

	private boolean state = false;
	
	private String username;
	private String password;
	
	WebCallback accountCreationCB;
	
	private boolean waitingForResponse = false;
	
	public CreateAccountScreen(FranticPhases game) {
		super(game);
	}

	@Override
	public void show() {
		Window window = new Window("\nCreate Account Screen", skin);
        window.setFillParent(true);
        window.setModal(true);
        window.setMovable(false);
        stage.addActor(window);
        
		state = false;
		waitingForResponse = false;
		username = "";
		password = "";
		accountCreationCB = new WebCallback();
		
		Gdx.input.getTextInput(this, "Enter desired username", "", "hint");
	}
		

	@Override
	public void input(String text) {
		if (!state){
			username = text;
			Gdx.input.getTextInput(this, "Enter desired password", "", "hint");
			state = !state;
		}else{
			password = text;
			
			DataSender aSender = new DataSender();
			aSender.createNewAccount(username, password, accountCreationCB);
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
					game.showGameScreen();
					waitingForResponse = false;
				}else{
					game.showMainScreen();
					waitingForResponse = false;
					//some sort of popup that creation failed
				}
			}
		}
		
	}

}
