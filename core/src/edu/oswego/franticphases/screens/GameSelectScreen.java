package edu.oswego.franticphases.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import edu.oswego.franticphases.FranticPhases;
import edu.oswego.franticphases.datasending.DataSender;
import edu.oswego.franticphases.datasending.GameSession;
import edu.oswego.franticphases.datasending.Handler;
import edu.oswego.franticphases.datasending.WebCallback;
import edu.oswego.franticphases.gamelogic.CardGame;
import edu.oswego.franticphases.settings.Settings;
import edu.oswego.franticphases.widgets.Hud;

public class GameSelectScreen extends AbstractScreen  {
	InputMultiplexer inputMux = new InputMultiplexer();
	public Table gameData;
	Button playGame;
	boolean debug = true;
	ArrayList<GameSession> games;
	GameSession selectedGame;
	
	public GameSelectScreen(FranticPhases game) {
		super(game);
		games = game.getMyGames();
		
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
		
		Window window = new Window("\nGame Selection Screen", skin);
        window.setFillParent(true);
        window.setModal(true);
        window.setMovable(false);
        stage.addActor(window);
        Table table = new Table();
        table.setFillParent(true);
        //table.bottom();
        window.addActor(table);
        
        gameData= new Table(skin);
		ScrollPane scroll = new ScrollPane(gameData, skin);
		gameData.add("All of your current games!!!").padRight(25).padLeft(25);
		table.add(scroll).expandY().padTop(20).padBottom(10);
		
		table.row();
		Button newGame = new TextButton("New Game", skin);
		table.add(newGame).bottom();
		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.showCreateAGameScreen();
			}
		});
		table.row();
		playGame = new TextButton("Play Game", skin);
		table.add(playGame).bottom();
		playGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.loadGame(selectedGame);
			}
		});
		table.row();
		Button back = new TextButton("Go Back", skin);
		table.add(back).bottom();
		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.showPreviousScreen();
			}
		});
		
	}
	@Override
	protected void preStageRenderHook(float delta){
		
		if(games.size()>0){
		gameData.clear();
		gameData.row();
		gameData.add("Select a game to play!").padRight(50).padLeft(50);
		int index = 1;
		for(int i = 0; i < games.size();i++){
	
			gameData.row();
			CheckBox box = new CheckBox("" ,skin);
			final int tmp = i;
			box.addListener(new ChangeListener(){

				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(selectedGame == null){
						selectedGame= games.get(tmp);
						
					}else{
					selectedGame = null;
					
					}
					
				}
				
			});
			String p1 = games.get(i).getP1();
			StringBuilder string = new StringBuilder();
			if(!p1.equals(Settings.getUsername())){
				string.append(p1).append(", ");
			}
			p1 = games.get(i).getP2();
			if(!p1.equals(Settings.getUsername())){
				string.append(p1).append(", ");
			}
			p1 = games.get(i).getP3();
			if(!p1.equals(Settings.getUsername())){
				string.append(p1).append(", ");
			}
			p1 = games.get(i).getP4();
			if(!p1.equals(Settings.getUsername())){
				string.append(p1).append("");
			}
			
			
			gameData.add(index+" : With " + string.toString());
			index++;
			gameData.add(box).padRight(50);
			}
		
		}else{
			gameData.row();
			gameData.add("You are not currently in any games!").padRight(25).padLeft(25);
			gameData.row();
			gameData.add("Please create a game below!").padRight(25).padLeft(25);
		}
		if(selectedGame == null){
			playGame.setVisible(false);
		}else{
			playGame.setVisible(true);
		}
		

	}

}
