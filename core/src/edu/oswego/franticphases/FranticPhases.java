package edu.oswego.franticphases;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import edu.oswego.franticphases.datasending.Handler;
import edu.oswego.franticphases.gamelogic.CardGame;
import edu.oswego.franticphases.screens.CreateAGameScreen;
import edu.oswego.franticphases.screens.CreateAccountScreen;
import edu.oswego.franticphases.screens.GameScreen;
import edu.oswego.franticphases.screens.GameSelectScreen;
import edu.oswego.franticphases.screens.HelpScreen;
import edu.oswego.franticphases.screens.LoginScreen;
import edu.oswego.franticphases.screens.MainScreen;
import edu.oswego.franticphases.screens.SettingsScreen;
import edu.oswego.franticphases.settings.Settings;
import edu.oswego.franticphases.settings.SettingsObserver;
import edu.oswego.franticphases.settings.SettingsUpdate;


public class FranticPhases extends Game implements SettingsObserver{
	
	Stack<Screen> screenStack = new Stack<Screen>();
	private final List<String> phases = new ArrayList<String>();
	{
		phases.add("phase.tmx");//1
//		phases.add("two.tmx");//2
//		phases.add("three.tmx");//3
//		phases.add("four.tmx");//4
//		phases.add("five.tmx");//5
//		phases.add("six.tmx");//6
//		phases.add("seven.tmx");//7
//		phases.add("eight.tmx");//8
//		phases.add("nine.tmx");//9
//		phases.add("ten.tmx");//10


	}
	private GameScreen gameScreen;
	private MainScreen mainScreen;
	private LoginScreen loginScreen;
	private CreateAccountScreen caScreen;
	private SettingsScreen settingsScreen;
	private HelpScreen helpScreen;
	private GameSelectScreen gsScreen;
	private CreateAGameScreen createGameScreen;
	
	private Settings settings;
	private Stage stage;
	private Skin skin;
	private BitmapFont font;
	private AssetManager assetManager;
	
	private SpriteBatch batch;
	private int width;
	private int height;
	private CardGame currentGame;
	
	

	@Override
	public void create () {
		batch = new SpriteBatch();
		settings = new Settings();
		settings.addObserver(this);
		assetManager = new AssetManager();
		loadAssets();
		font = new BitmapFont();
		loadSkin();
		width = 480;//480
		height = 320;//320
		ScalingViewport svp = new ScalingViewport(Scaling.stretch, width, height);
		stage = new Stage(svp, batch);
		
		showMainScreen();
	}
	
	private void loadAssets(){
		String cardFile = "data/cards/Blue1.png";
		if (!assetManager.isLoaded(cardFile)) {
			assetManager.load(cardFile, Texture.class);
			assetManager.finishLoading();
		}
		//music = assetManager.get(musicFile, Music.class);
	}
	
	public AssetManager getAssetManager(){
		return assetManager;
	}
	
	private void loadSkin() {
		skin = new Skin(Gdx.files.internal("uiskin.json"));
	}
	
	public void setGame(CardGame g){
		currentGame = g;
	}
	
	public SpriteBatch getSpriteBatch() {
		return batch;
	}
	public BitmapFont getFont() {
		return font;
	}
	
	public CardGame getCurrentGame(){
		return currentGame;
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
	
	public void showCreateAGameScreen(Handler handler) {
		if (createGameScreen != null) {
			createGameScreen.dispose();
		}
		screenStack.push(getScreen());
		createGameScreen = new CreateAGameScreen(this, handler);
		setScreen(createGameScreen);
	}
	
	public void showHelpScreen() {
		if (helpScreen != null) {
			helpScreen.dispose();
		}
		screenStack.push(getScreen());
		helpScreen = new HelpScreen(this);
		setScreen(helpScreen);
	}
	
	public void showGameSelectionScreen() {
		if (gsScreen != null) {
			gsScreen.dispose();
		}
		screenStack.push(getScreen());
		gsScreen = new GameSelectScreen(this);
		setScreen(gsScreen);
	}
	
	public void showSettingsScreen() {
		if (settingsScreen != null) {
			settingsScreen.dispose();
		}
		screenStack.push(getScreen());
		settingsScreen = new SettingsScreen(this);
		setScreen(settingsScreen);
	}
	
	public void showPreviousScreen() {
		if(screenStack.peek() != gameScreen){
		}
		setScreen(screenStack.pop());
	}
	
	public Settings getSettings() {
		return settings;
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
		if (helpScreen != null) {
			helpScreen.dispose();
		}
		if (settingsScreen != null) {
			settingsScreen.dispose();
		}
		if (loginScreen != null) {
			loginScreen.dispose();
		}
		if (caScreen != null) {
			caScreen.dispose();
		}
		stage.dispose();
		batch.dispose();

	}
	public List<String> getPhases() {
		return phases;
	}
	
	@Override
	public void handleSettingsChangeUpdate(SettingsUpdate update) {
//		if (update.getSetting() == Settings.MUSIC) {
//			playMusic = update.getValue();
//			if (playMusic) {
//				playMusic();
//			} else {
//				endMusic();
//			}
//		}
	}
	
	
}
