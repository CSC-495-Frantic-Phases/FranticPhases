package edu.oswego.franticphases.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import edu.oswego.franticphases.FranticPhases;
import edu.oswego.franticphases.dialogs.PauseDialog;
import edu.oswego.franticphases.gamelogic.GameHandler;
import edu.oswego.franticphases.gamelogic.Turn;
import edu.oswego.franticphases.widgets.Hud;



public class GameScreen extends AbstractScreen  {
	InputMultiplexer inputMux = new InputMultiplexer();
	private final Hud hud;
	private GameHandler handler;
	private State currentState;
	private Dialog pauseDialog;
	private Button faceupCardButton;
	private Button deckButton;
	private Button refresh;
	private Turn turn;
	
	public GameScreen(FranticPhases game) {
		super(game);
		hud = new Hud(this, skin);

		handler = new GameHandler(game.getCurrentGame(), hud);
		this.loadGameData();
	}
	
	private void loadGameData(){
		changeState(State.WAITING);
		handler.loadGame();
	}
	private void changeState(State state) {
		System.out.println("GAME STATE CHANGED: "+ state.toString());
		currentState = state;
	}
	@Override
	protected void preStageRenderHook(float delta){
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
		Window window = new Window("\nGame", skin);
		window.setHeight(100);
		window.setWidth(stage.getWidth());
		window.setPosition(0, stage.getWidth()/2);
        stage.addActor(window);
        Table table = new Table();
        table.setFillParent(true);
        //table.bottom();
        window.addActor(table);
        
        faceupCardButton = new TextButton("Faceup Card", skin);
        table.add(faceupCardButton).pad(25);
        faceupCardButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	faceUpCard();
            }
        });
        
        
        deckButton = new TextButton("DECK", skin);
        table.add(deckButton).pad(25);
        deckButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	deck();
            }
        });
        
        refresh = new TextButton("Refresh", skin);
        table.add(refresh).pad(25);
        refresh.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	refresh();
            }
        });
        
		hud.setPosition(0, 0);
		hud.setHeight(150);
		hud.setWidth(stage.getWidth());
		stage.addActor(hud);
		
		currentState.show(this);
	}
	
	
	@Override
	public void dispose() {
		super.dispose();

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
				s.faceupCardButton.setName(s.handler.getFaceUpCard());
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
