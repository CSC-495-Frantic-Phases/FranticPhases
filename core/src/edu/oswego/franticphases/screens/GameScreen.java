package edu.oswego.franticphases.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import edu.oswego.franticphases.FranticPhases;
import edu.oswego.franticphases.datasending.GameHandler;
import edu.oswego.franticphases.dialogs.PauseDialog;
import edu.oswego.franticphases.gamelogic.Card;
import edu.oswego.franticphases.gamelogic.Controller;
import edu.oswego.franticphases.gamelogic.DebugPhaseRenderer;
import edu.oswego.franticphases.gamelogic.DefaultPhaseRenderer;
import edu.oswego.franticphases.gamelogic.Hand;
import edu.oswego.franticphases.gamelogic.Phase;
import edu.oswego.franticphases.gamelogic.PhaseRenderer;
import edu.oswego.franticphases.gamelogic.Turn;
import edu.oswego.franticphases.gamelogic.WorldPopulator;
import edu.oswego.franticphases.objects.HandCardObject;
import edu.oswego.franticphases.widgets.Hud;

public class GameScreen extends AbstractScreen  {
	InputMultiplexer inputMux = new InputMultiplexer();
	private GameHandler handler;
	private State currentState;
	private Dialog pauseDialog;
	private Turn turn;
	private AssetManager assetManager;//will be used for loading sounds/music
	private PhaseRenderer renderer;
	private final WorldPopulator worldPopulator;
	private Phase phase;
	private Stage tmpStage;
	ArrayList<HandCardObject> hand;
	ArrayList<String> cards;
	Hand myHand;
	Hud hud;
	
	public GameScreen(FranticPhases game, GameHandler h) {
		super(game);
		hud = new Hud(this, skin, game.getAssetManager());
		assetManager = game.getAssetManager();
		handler = h;
		tmpStage = this.stage;
		turn = new Turn();
		
		worldPopulator = new WorldPopulator(game.getAssetManager());
		this.loadGameData();
	}
	
	private void loadGameData(){
	
		changeState(State.WAITING);
		
		Gdx.app.log("GameScreen", "Loading Phase");
		if (phase != null) {
			phase.dispose();
			phase = null;
		}
		if (renderer != null) {
			renderer.dispose();
			renderer = null;
		}
		
		Gdx.app.log("GameScreen", "Cleaned up previous phase");
		String num = handler.getPhaseNumber();//gotta load current phase from cardGame
		phase = new Phase(num,
				game.getPhases().get(Integer.parseInt(num)),
				worldPopulator, game.getAssetManager());
		Gdx.app.log("GameScreen", "Phase loaded");
		
		hand = phase.getHand();
		cards = handler.getCards();
		myHand = handler.getHand();
		
		
		renderer = new DefaultPhaseRenderer(phase,
				game.getWidth(), game.getHeight(),
				game.getSpriteBatch(),
				game.getAssetManager());
		hud.setPhase(num);
		hud.setScore(handler.getScore());
	
		//renderer = new DebugPhaseRenderer(renderer);
		
		
		ScalingViewport svp = new ScalingViewport(Scaling.stretch, renderer.getCamera().viewportWidth,  renderer.getCamera().viewportHeight);
		tmpStage = new Stage(svp, game.getSpriteBatch());
		//tmpStage.setDebugAll(true);
		
		this.setHandGraphics();
		this.setFaceupGraphic();
		
		//phase.setListeners(tmpStage, this);
		
		Gdx.app.log("GameScreen", "Renderer created");
		Gdx.app.log("GameScreen", "Phase starting...");
	}
	
	
	public void setHandGraphics(){
		tmpStage.clear();
		
		ArrayList<Card> cardHand = myHand.getCards();
		for(int i = 0; i < cardHand.size(); i++){
			hand.get(i).setGraphic(cardHand.get(i).getCardID(), tmpStage, renderer.getCamera());
		}
		//this.setShuffleButton();
		phase.setListeners(tmpStage, this);
		
		
	}
	
	public void shuffle(){
		if(myHand.isSortedByColor()){
			myHand.sortByValue();
		}else{
			myHand.sortBySuit();
		}
		this.setHandGraphics();
		this.addHud();
	}
	
	private void addHud(){
		tmpStage.addActor(hud);
	}
	
	public void setFaceupGraphic(){
		phase.getfCard().setGraphic(handler.getFaceUpCard());
	}
	
	
	
	private void changeState(State state) {
		System.out.println("GAME STATE CHANGED: "+ state.toString());
		currentState = state;
	}
	@Override
	protected void preStageRenderHook(float delta){
		renderer.render(delta, game.getSpriteBatch(), game.getFont());
		//currentState.render(this, delta);
	}
	
	public void togglePause() {
		currentState.togglePause(this);
	}


	@Override
	public void pause() {
		currentState.pause(this);
	}

	@Override
	public void resume() {
		currentState.resume(this);
	}
	
	public void deck() {
		currentState.deck(this);
	}
	public void faceUpCard() {
		currentState.faceUpCard(this);
	}
	public void refresh() {
		currentState.refresh(this);
	}
	public void start() {
		currentState.start(this);
	}
	public void discard() {
		currentState.discard(this);
	}
	
	public void pickUpCard(boolean wasFaceUp){
		if(wasFaceUp){
			myHand.addCard(new Card(handler.selectFaceUpCard()));
			this.setHandGraphics();
			this.setFaceupGraphic();
			this.addHud();
		}else{
			myHand.addCard(new Card(handler.getDeckCard()));
			this.setHandGraphics();
			this.setFaceupGraphic();
			this.addHud();
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		preStageRenderHook(delta);
		tmpStage.act(Gdx.graphics.getDeltaTime());
		tmpStage.draw();
		//postStageRenderHook(delta);
	}
	@Override
	public void resize(int width, int height) {
		
	
	}
	
	@Override
	public void show() {
		//set all the input processors here
		//first should be the hud
		//then the game
		//then the back button
		Gdx.input.setInputProcessor(inputMux);
		inputMux.addProcessor(tmpStage);
		
		inputMux.addProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				if(keycode == Keys.BACK){
					pause();
					return true;
				}
				return super.keyDown(keycode);
			}
		});

		hud.setPosition(0, 0);
		hud.setHeight(32);
		hud.setWidth(tmpStage.getWidth());
		tmpStage.addActor(hud);
		
		currentState.show(this);
	}
	
	
	@Override
	public void dispose() {
		super.dispose();
		//world.dispose();
		renderer.dispose();

	}
	
	private static enum State {
		WAITING {
			@Override
			public void start(GameScreen s) {
				s.handler.update();
			}
			@Override
			public void pause(GameScreen s) {
				s.pauseDialog = new PauseDialog("Paused", s.skin, s, s.game).show(s.tmpStage);
				
				s.changeState(State.PAUSED);
			}

			@Override
			public void togglePause(GameScreen s) {
				pause(s);
			}

			@Override
			public void refresh(GameScreen s){
				
				s.handler.update();
			}
			@Override
			public void faceUpCard(GameScreen s){
				System.out.println("NOT YOUR TURN");
				if(s.turn.canPickUp()){
					s.pickUpCard(true);
					
					s.turn.pickedUp();
				}
			}
			@Override
			public void deck(GameScreen s){
				System.out.println("NOT YOUR TURN");
			}
		},
		PAUSED {
			@Override
			public void resume(GameScreen s) {
				if (s.pauseDialog != null && s.pauseDialog.hasParent()) {
					s.pauseDialog.hide();
					s.pauseDialog = null;
				}
				
				s.changeState(State.WAITING);
			}

			@Override
			public void show(GameScreen s) {
				s.pauseDialog = new PauseDialog("Paused", s.skin, s, s.game).show(s.tmpStage);
			}

			@Override
			public void togglePause(GameScreen s) {
				resume(s);
			}
		},
		TAKINGTURN {
			@Override
			public void pause(GameScreen s) {
				s.pauseDialog = new PauseDialog("Paused", s.skin, s, s.game).show(s.tmpStage);
				s.changeState(State.PAUSED);
			}

			@Override
			public void togglePause(GameScreen s) {
				pause(s);
			}
			@Override
			public void start(GameScreen s) {
				s.turn = new Turn();
			}
			
			@Override
			public void faceUpCard(GameScreen s){
				//need to add card to hand if card not already taken
				//then change the faceup card display to the one under it?
				//System.out.println("YOUR TURN");
				if(s.turn.canPickUp()){
					s.pickUpCard(true);
					
					s.turn.pickedUp();
				}
			}
			@Override
			public void deck(GameScreen s){
				//need to add card to hand if card not already taken
				if(s.turn.canPickUp()){
					s.pickUpCard(false);
				
					s.turn.pickedUp();
				}
			}
			@Override
			public void discard(GameScreen s){
				//need to see if you can discard
				//if you can then turn ended
				if(s.turn.canDiscard()){
					System.out.println("CARD DISCARDED");
					//s.handler.endTurn();
				}else{
					System.out.println("YOU NEED TO PICK UP FIRST");
				}

			}
		},
		GAMEOVER;

		public void show(GameScreen s) {}
		public void start(GameScreen s) {}
		public void discard(GameScreen s) {}
		public void togglePause(GameScreen s) {}
		public void pause(GameScreen s) {}
		public void deck(GameScreen s) {}
		public void faceUpCard(GameScreen s) {}
		public void refresh(GameScreen s) {}
		public void resume(GameScreen s) {}
		
	}

}
