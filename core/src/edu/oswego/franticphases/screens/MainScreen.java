package edu.oswego.franticphases.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import edu.oswego.franticphases.FranticPhases;
import edu.oswego.franticphases.datasending.DataSender;
import edu.oswego.franticphases.datasending.WebCallback;
import edu.oswego.franticphases.dialogs.LoginDialog;
import edu.oswego.franticphases.settings.Settings;

public class MainScreen extends AbstractScreen {

	private WebCallback autoLoginCallBack;

	public MainScreen(final FranticPhases game) {
		super(game);

	}

	@Override
	public void show() {
		InputMultiplexer multiplexer = new InputMultiplexer(stage,
				new com.badlogic.gdx.InputAdapter() {
					@Override
					public boolean keyDown(int keycode) {
						if (keycode == Keys.BACK) {
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
		//table.bottom();
		window.addActor(table);
		table.row();
		Button play = new TextButton("Play", skin);
		table.add(play).width(100).pad(10).colspan(4);
		play.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
					game.showGameSelectionScreen();

			}
		});
		table.row();
		Button help = new TextButton("Help", skin);
		table.add(help).width(100).pad(10).colspan(4);
		help.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.showHelpScreen();
			}
		});
		table.row();
		Button settings = new TextButton("Settings", skin);
		table.add(settings).width(100).pad(10).padBottom(20).colspan(4);
		settings.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.showSettingsScreen();
			}
		});

	}

}
