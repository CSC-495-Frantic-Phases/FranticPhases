package edu.oswego.franticphases.gamelogic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public interface PhaseRenderer extends Disposable {

	public abstract OrthographicCamera getCamera();

	public abstract Phase getPhase();

	public abstract void render(float delta, SpriteBatch batch, BitmapFont font);

	public void updateCamera();
}