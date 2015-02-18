package edu.oswego.franticphases.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.nextpeer.libgdx.NextpeerPlugin;

import edu.oswego.franticphases.FranticPhases;



public class MainScreen extends AbstractScreen  {

	
    public MainScreen(final FranticPhases game) {
        super(game);

    }
	@Override
	public void show() {
		InputMultiplexer multiplexer = new InputMultiplexer(stage,
				new com.badlogic.gdx.InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				if(keycode == Keys.BACK){
					Gdx.app.exit();
					return true;
				}
				return super.keyDown(keycode);
			}
		});
        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setCatchBackKey(true);
        
		Window window = new Window("\nFrantic Phases", skin);
	        window.setFillParent(true);
	        window.setModal(true);
	        window.setMovable(false);
	        stage.addActor(window);
	        Table table = new Table();
	        table.setFillParent(true);
	        table.bottom();
	        window.addActor(table);
	        
	        Button play = new TextButton("Play", skin);
	        table.add(play).width(100).pad(10).padBottom(20).colspan(4);
	        play.addListener(new ChangeListener() {
	            @Override
	            public void changed(ChangeEvent event, Actor actor) {
	            	if (NextpeerPlugin.isAvailable()) {
	            		 
	                    NextpeerPlugin.launch();
	                }
	                // Else, we don't have tournament mode, run the game normally
	                else {
	                	game.showGameScreen();
	                }
	            	//game.showGameScreen();
	            }
	        });
		
	}

}
