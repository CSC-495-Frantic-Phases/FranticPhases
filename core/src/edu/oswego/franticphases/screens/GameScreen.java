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
import edu.oswego.franticphases.Settings;
import edu.oswego.franticphases.datasending.DataSender;
import edu.oswego.franticphases.datasending.WebCallback;
import edu.oswego.franticphases.gamelogic.Handler;




public class GameScreen extends AbstractScreen  {
	InputMultiplexer inputMux = new InputMultiplexer();
	private WebCallback callBack;
	private Handler handler;
	public Table userData;
	public GameScreen(FranticPhases game) {
		super(game);
		handler = new Handler(this);
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
		
		Window window = new Window("\nGame Screen", skin);
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
        table.row();
        table.bottom();
        Button back = new TextButton("Back", skin);
        table.add(back).width(100).pad(10).padBottom(20).colspan(4);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	game.showMainScreen();
            }
        });
		
	}
	
	
	@Override
	public void dispose() {
		super.dispose();

	}

}
