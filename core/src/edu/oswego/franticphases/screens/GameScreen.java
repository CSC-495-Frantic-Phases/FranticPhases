package edu.oswego.franticphases.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import edu.oswego.franticphases.FranticPhases;
import edu.oswego.franticphases.datasending.GameHandler;
import edu.oswego.franticphases.dialogs.PauseDialog;
import edu.oswego.franticphases.gamelogic.DebugPhaseRenderer;
import edu.oswego.franticphases.gamelogic.DefaultPhaseRenderer;
import edu.oswego.franticphases.gamelogic.Phase;
import edu.oswego.franticphases.gamelogic.PhaseRenderer;
import edu.oswego.franticphases.gamelogic.Turn;
import edu.oswego.franticphases.gamelogic.WorldPopulator;

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
	
	
	public GameScreen(FranticPhases game) {
		super(game);
		//hud = new Hud(this, skin);
		assetManager = game.getAssetManager();
		handler = new GameHandler(this, game.getCurrentGame());
		worldPopulator = new WorldPopulator(game.getAssetManager());
		this.loadGameData();
	}
	
	private void loadGameData(){
		handler.loadGame();
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
		int num = 0;//gotta load current phase from cardGame
		phase = new Phase(num,
				game.getPhases().get(num),
				worldPopulator, game.getAssetManager());
		Gdx.app.log("GameScreen", "Phase loaded");

		renderer = new DefaultPhaseRenderer(phase,
				game.getWidth(), game.getHeight(),
				game.getSpriteBatch(),
				game.getAssetManager());
	
		renderer = new DebugPhaseRenderer(renderer);
		
		Gdx.app.log("GameScreen", "Renderer created");
//		audio = new AudioManager(
//				level,
//				game.getSettings().isMusicOn(),
//				game.getSettings().isSoundEffectOn(),
//				game.getAssetManager());
//		game.getSettings().addObserver(audio);
//		Gdx.app.log("GameScreen", "Audio manager created");
		//hud.setLevel(num + 1);

		Gdx.app.log("GameScreen", "Phase starting...");
	}
	
	private void changeState(State state) {
		System.out.println("GAME STATE CHANGED: "+ state.toString());
		currentState = state;
	}
	@Override
	protected void preStageRenderHook(float delta){
		renderer.render(delta, game.getSpriteBatch(), game.getFont());
		currentState.render(this, delta);
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
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(inputMux);
		inputMux.addProcessor(stage);
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
				s.pauseDialog = new PauseDialog("Paused", s.skin, s, s.game).show(s.stage);
				
				s.changeState(State.PAUSED);
			}

			@Override
			public void togglePause(GameScreen s) {
				pause(s);
			}
			@Override
			public void render(GameScreen s, float delta) {
				//s.discardLabel.setText(s.handler.getFaceUpCard());
				if(s.handler.getIsTurn()){
					
					s.changeState(State.TAKINGTURN);
					s.start();
				}
			}
			@Override
			public void refresh(GameScreen s){
				
				s.handler.update();
			}
			@Override
			public void faceUpCard(GameScreen s){
				System.out.println("NOT YOUR TURN");
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
				s.pauseDialog = new PauseDialog("Paused", s.skin, s, s.game).show(s.stage);
			}

			@Override
			public void togglePause(GameScreen s) {
				resume(s);
			}
		},
		TAKINGTURN {
			@Override
			public void pause(GameScreen s) {
				s.pauseDialog = new PauseDialog("Paused", s.skin, s, s.game).show(s.stage);
				
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
			public void render(GameScreen s, float delta) {
				if(!s.handler.getIsTurn()){
					s.changeState(State.WAITING);
				}
			}
			@Override
			public void faceUpCard(GameScreen s){
				//need to add card to hand if card not already taken
				//then change the faceup card display to the one under it?
				//System.out.println("YOUR TURN");
				if(s.turn.canPickUp()){
					System.out.println("YOU PICKED UP THE CARD");
					s.handler.faceUpCardSelected();
					s.turn.pickedUp();
				}else{
					System.out.println("YOU ALREADY PICKED UP A CARD");
				}
			}
			@Override
			public void deck(GameScreen s){
				//need to add card to hand if card not already taken
				if(s.turn.canPickUp()){
					System.out.println("YOU PICKED UP THE CARD");
					s.handler.faceUpCardSelected();
					s.turn.pickedUp();
				}else{
					System.out.println("YOU ALREADY PICKED UP A CARD");
				}
			}
			@Override
			public void discard(GameScreen s){
				//need to see if you can discard
				//if you can then turn ended
				if(s.turn.canDiscard()){
					System.out.println("CARD DISCARDED");
					s.handler.endTurn();
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
		public void render(GameScreen s, float delta) {}
	}

}
