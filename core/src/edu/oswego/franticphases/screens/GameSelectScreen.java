package edu.oswego.franticphases.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import edu.oswego.franticphases.FranticPhases;
import edu.oswego.franticphases.datasending.DataSender;
import edu.oswego.franticphases.datasending.WebCallback;
import edu.oswego.franticphases.gamelogic.Handler;
import edu.oswego.franticphases.settings.Settings;
import edu.oswego.franticphases.widgets.Hud;

public class GameSelectScreen extends AbstractScreen  {
	InputMultiplexer inputMux = new InputMultiplexer();
	private final Hud hud;
	private WebCallback callBack;
	private Handler handler;
	public Table userData;
	
	public GameSelectScreen(FranticPhases game) {
		super(game);
		hud = new Hud(this, skin);
		hud.setName(Settings.getUsername());
		//handler = new Handler(this);
		getCurrentGames();
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
		hud.setPosition(0, stage.getHeight());
		hud.setHeight(32);
		hud.setWidth(stage.getWidth());
		stage.addActor(hud);
		
		Window window = new Window("\nGame Selection Screen", skin);
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
		userData.add("Click the button to get current users").padRight(25).padLeft(25);

		table.add(scroll).expandY().padTop(20).padBottom(10);
        table.row();
        Button play = new TextButton("get users", skin);
        table.add(play).width(100).pad(10).colspan(4);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	callBack = new WebCallback();
    			DataSender aSender = new DataSender();
    			aSender.getAllUserNames(callBack, handler);
            }
        });
		
		
		
		
		
		Button back = new TextButton("Go Back", skin);
		table.add(back).bottom();
		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.showPreviousScreen();
			}
		});
		
	}
	
	private void getCurrentGames(){
		callBack = new WebCallback();
		DataSender aSender = new DataSender();
		aSender.getGames(Settings.getUserID(), callBack, handler);
		
	}

}
