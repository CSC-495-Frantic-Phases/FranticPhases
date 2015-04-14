package edu.oswego.franticphases.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface MapRenderable {
	public void drawBeforeBall(float delta, SpriteBatch batch);
	public void drawAfterBall(float delta, SpriteBatch batch);
}
