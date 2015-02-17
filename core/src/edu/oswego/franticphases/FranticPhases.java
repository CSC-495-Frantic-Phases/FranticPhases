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
import com.nextpeer.libgdx.NextpeerPlugin;
import com.nextpeer.libgdx.Tournaments;
import com.nextpeer.libgdx.TournamentsCallback;

import edu.oswego.franticphases.screens.*;

public class FranticPhases extends Game {
	
	Stack<Screen> screenStack = new Stack<Screen>();
	//private AssetManager assetManager;
	private GameScreen gameScreen;
	private MainScreen mainScreen;
	private Stage stage;
	private Skin skin;

	
	SpriteBatch batch;
	Texture img;
	private int width;
	private int height;
	
	public FranticPhases() {

	}
	 
	public FranticPhases(Tournaments tournaments) {
	    // If we have a supported tournaments object, set the game as callback
	    if (tournaments != null && tournaments.isSupported()) {
	        tournaments.setTournamentsCallback(mNextpeerTournamentsCallback);
	         
	        // Load Nextpeer plugin with the tournaments instance
	        NextpeerPlugin.load(tournaments);
	    }
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
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
	
    private TournamentsCallback mNextpeerTournamentsCallback = new TournamentsCallback() {
		
		@Override
		public void onTournamentStart(long tournamentRandomSeed) {
	        // Start the game scene
	        NextpeerPlugin.instance().lastKnownTournamentRandomSeed = tournamentRandomSeed;
	        showGameScreen();
	        //setScreen(new GameScreen(FranticPhases.this));
		}
		
		@Override
		public void onTournamentEnd() {
	        // End the game scene, switch to main menu
	        NextpeerPlugin.instance().lastKnownTournamentRandomSeed = 0;
	        showMainScreen();
	        //setScreen(new MainScreen(FranticPhases.this));
		}
	};
	
	
}
