package edu.oswego.franticphases.widgets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import edu.oswego.franticphases.screens.GameSelectScreen;


public class Hud extends Window {
	private final Label usernameDisplay;

	
	public Hud(final GameSelectScreen screen, final Skin skin) {
		super("", skin);
		row().uniform().expandX();
		add("Welcome ").right().padLeft(10);
		usernameDisplay = new Label("", skin);
		add(usernameDisplay).left();
	}
	
	public void setName(String n) {
		usernameDisplay.setText(n.trim() + "!");
	}
	
}
