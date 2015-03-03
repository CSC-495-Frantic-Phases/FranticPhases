package edu.oswego.franticphases.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import edu.oswego.franticphases.FranticPhases;

public class HelpScreen extends AbstractScreen  {

	public HelpScreen(FranticPhases game) {
		super(game);
	}

	@Override
	public void show() {
		InputMultiplexer multiplexer = new InputMultiplexer(stage,
				new InputAdapter() {
					@Override
					public boolean keyDown(int keycode) {
						if (keycode == Keys.BACK) {
							game.showPreviousScreen();
							return true;
						}
						return super.keyDown(keycode);
					}
				});
			Gdx.input.setInputProcessor(multiplexer);



			Window table = new Window("\nHelp", skin);
			table.setFillParent(true);
			table.setModal(true);
			table.setMovable(false);
			stage.addActor(table);

			Table table2 = new Table(skin);
			ScrollPane scroll = new ScrollPane(table2, skin);

			table.add(scroll).expandY().padTop(40).padBottom(10);
			
			table2.row();
			table2.add("How To Play:").padRight(55).padLeft(55).padTop(30);
			table2.row();
			table2.add("TODO: WRITE RULES").padBottom(30);
			table2.row();
			

			table.row().padBottom(10);

			Button back = new TextButton("Go Back", skin);
			table.add(back).bottom();
			back.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					game.showPreviousScreen();
				}
			});
		
	}

}
