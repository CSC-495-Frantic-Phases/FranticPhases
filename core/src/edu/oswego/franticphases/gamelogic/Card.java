package edu.oswego.franticphases.gamelogic;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;

public class Card extends AbstractWorldObject implements Disposable{
	private final GraphicComponent graphic;
	private boolean visible = true;

	private final UnitScale scale;
	private boolean playSound;
	
	String color;
	int value;
	String type;
	
	
	public Card(Body body, GraphicComponent graphic, UnitScale scale){
		super(body);
		this.scale = scale;

		this.graphic = graphic;
	}
	public void draw(float delta, SpriteBatch batch) {
		if (visible) {
			graphic.setPosition(getMapX(), getMapY());
			graphic.draw(delta, batch);
		}
	}
	public float getRadius() {
		return scale.metersToPixels(
				body.getFixtureList().get(0).getShape().getRadius());
	}

	public float getMapX() {
		return scale.metersToPixels(body.getPosition().x);
	}

	public float getMapY() {
		return scale.metersToPixels(body.getPosition().y);
	}

	public void hide() {
		visible = false;
	}

	public void show() {
		visible = true;
	}

	@Override
	public void dispose() {
		graphic.dispose();
	}
	
	
}
