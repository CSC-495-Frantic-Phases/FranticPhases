package edu.oswego.franticphases;

import java.util.Stack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import edu.oswego.franticphases.screens.*;

public class FranticPhases extends Game {
	
	Stack<Screen> screenStack = new Stack<Screen>();
	//private AssetManager assetManager;
	private GameScreen gameScreen;
	private MainScreen mainScreen;
	private LoginScreen loginScreen;
	private CreateAccountScreen caScreen;
	private Stage stage;
	private Skin skin;

	
	SpriteBatch batch;
	Texture img;
	private int width;
	private int height;
	
	

	@Override
	public void create () {
		batch = new SpriteBatch();
		Settings.Init();
		loadSkin();
		width = 480;
		height = 320;
		ScalingViewport svp = new ScalingViewport(Scaling.stretch, width, height);
		stage = new Stage(svp, batch);
		
		showMainScreen();
	}
	
	private void loadSkin() {
		//assetManager.load("data/ui/skin.json", Skin.class,
			//	new SkinLoader.SkinParameter("data/ui/franticphases.pack"));
	//	assetManager.finishLoading();

		//skin = assetManager.get("data/ui/skin.json", Skin.class);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
	}
	
	public void showMainScreen() {
		if (mainScreen == null) {
			mainScreen = new MainScreen(this);
		}
		if (getScreen() != null) {
			screenStack.push(getScreen());
		}
		setScreen(mainScreen);
	}
	
	public void showGameScreen() {
		if (gameScreen != null) {
			gameScreen.dispose();
		}
		screenStack.push(getScreen());
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}
	
	public void showLoginScreen() {
		if (loginScreen != null) {
			loginScreen.dispose();
		}
		screenStack.push(getScreen());
		loginScreen = new LoginScreen(this);
		setScreen(loginScreen);
	}
	
	public void showCreateAccountScreen() {
		if (caScreen != null) {
			caScreen.dispose();
		}
		screenStack.push(getScreen());
		caScreen = new CreateAccountScreen(this);
		setScreen(caScreen);
	}
	
	public void showPreviousScreen() {
		if(screenStack.peek() != gameScreen){
		}
		setScreen(screenStack.pop());
	}
	public Skin getSkin() {
		return skin;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
	
	@Override
	public void dispose() {

		mainScreen.dispose();

		if (gameScreen != null) {
			gameScreen.dispose();
		}
		stage.dispose();
		batch.dispose();

	}
	
	
}
