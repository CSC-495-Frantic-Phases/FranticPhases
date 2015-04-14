package edu.oswego.franticphases.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import edu.oswego.franticphases.FranticPhases;
import edu.oswego.franticphases.datasending.DataSender;
import edu.oswego.franticphases.datasending.Handler;
import edu.oswego.franticphases.datasending.WebCallback;
import edu.oswego.franticphases.gamelogic.CardGame;
import edu.oswego.franticphases.gamelogic.Hand;
import edu.oswego.franticphases.gamelogic.Player;
import edu.oswego.franticphases.settings.Settings;

public class CreateAGameScreen extends AbstractScreen{
	InputMultiplexer inputMux = new InputMultiplexer();
	private WebCallback callBack;
	private Handler handler;
	boolean debug = true;
	boolean hasUsers = false;
	CardGame newGame;
	Table userData;
	ArrayList<Player> users;
	Button go;
	
	
	
	public CreateAGameScreen(FranticPhases game, Handler hand) {
		super(game);
		handler = hand;
		this.getAllUsers();
		newGame = new CardGame("maybe messed up");
		//selectedUsers = new ArrayList<Player>();
		Player me = new Player(Settings.getUsername(), Settings.getUserID());
		//selectedUsers.add(me);
		newGame.addPlayer(me);
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
		
		Window window = new Window("\nCreate A Game", skin);
        window.setFillParent(true);
        window.setModal(true);
        window.setMovable(false);
        stage.addActor(window);
        Table table = new Table();
        table.setFillParent(true);
        //table.bottom();
        window.addActor(table);
        
        userData= new Table(skin);
        
		ScrollPane scroll = new ScrollPane(userData, skin);
		//SplitPane splitpane = new SplitPane(scroll, selectedData, false, skin);
		table.add(scroll).expandY().padTop(25).padBottom(10);

		
		table.row();
		Button back = new TextButton("Go Back", skin);
		table.add(back).bottom();
		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.showPreviousScreen();
			}
		});
		table.row();
		go = new TextButton("Create Game", skin);
		go.setVisible(false);
		table.add(go).bottom();
		go.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				newGame.createDeck();
				
				callBack = new WebCallback();
				DataSender aSender = new DataSender();
				aSender.createGame(newGame, callBack, handler);		

        		
				System.out.println("LOADING");
				
		}
		});
		
		
	}
	@Override
	protected void preStageRenderHook(float delta){
		if(handler.isUsersUpdated()){
		users = handler.getUsers();
		userData.clear();
		userData.row();
		userData.add("Select friends to play with!").padRight(50).padLeft(50);
		int index = 1;
		for(int i = 0; i < users.size();i++){
			if(!users.get(i).getID().equals(Settings.getUserID())){
			userData.row();
			CheckBox box = new CheckBox("" ,skin);
			final int tmp = i;
			box.addListener(new ChangeListener(){

				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(newGame.getPlayers().contains(users.get(tmp))){
						newGame.removePlayer(users.get(tmp));
					}else{
						newGame.addPlayer(users.get(tmp));
					}
					
				}
				
			});
			userData.add(index+" : " + users.get(i).getUserName());
			index++;
			userData.add(box).padRight(50);
			}
		}
		}
		if(newGame.getNumPlayers()==4){
			go.setVisible(true);
		}else{
			go.setVisible(false);
		}
		if(handler.isNewGameUpdated()){
			game.setGame(new CardGame(handler.getNewGameID()));
			game.showGameScreen();
		}
	}

	private void getAllUsers(){
		callBack = new WebCallback();
		DataSender aSender = new DataSender();
		aSender.getUsers( callBack, handler);
		hasUsers = callBack.getResult();
	}	
	
}
