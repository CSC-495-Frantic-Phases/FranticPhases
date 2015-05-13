package edu.oswego.franticphases.widgets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import edu.oswego.franticphases.gamelogic.Player;
import edu.oswego.franticphases.screens.GameScreen;

public class PlayerDisplay extends Window{
	GameScreen screen;
	Label player1N;
	Label player1S;
	Label player1P;
	Label player2N;
	Label player2S;
	Label player2P;
	Label player3N;
	Label player3P;
	Label player3S;
	
	public PlayerDisplay(final GameScreen screen, final Skin skin, AssetManager assetManager) {
		super("", skin);
		this.screen = screen;
		row().uniform().expandX();
		//add("Hand ").right().padLeft(10);

		row().uniform().expandX();
		player1N = new Label("", skin);
		add(player1N).left();
		
		player2N = new Label("", skin);
		add(player2N).center();
		
		player3N = new Label("", skin);
		add(player3N).right();
		
		
		row().uniform().expandX();
		player1P = new Label("", skin);
		add(player1P).left();
		
		player2P = new Label("", skin);
		add(player2P).center();
		
		player3P = new Label("", skin);
		add(player3P).right();

		
		row().uniform().expandX();
		player1S = new Label("", skin);
		add(player1S).left();
		
		player2S = new Label("", skin);
		add(player2S).center();
		
		player3S = new Label("", skin);
		add(player3S).right();

		

	}
	
	public void setPlayer1Data(Player p) {
		player1N.setText(p.getUserName());
		player1S.setText(p.getScore());
		player1P.setText(p.getPhase());
	}
	public void setPlayer2Data(Player p) {
		player2N.setText(p.getUserName());
		player2S.setText(p.getScore());
		player2P.setText(p.getPhase());
	}
	public void setPlayer3Data(Player p) {
		player3N.setText(p.getUserName());
		player3S.setText(p.getScore());
		player3P.setText(p.getPhase());
	}

}
