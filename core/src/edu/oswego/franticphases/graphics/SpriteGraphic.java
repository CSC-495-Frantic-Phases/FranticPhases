package edu.oswego.franticphases.graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class SpriteGraphic extends Actor implements GraphicComponent  {
	private final Sprite sprite;

	public SpriteGraphic(float x, float y, Sprite sprite) {
		this.sprite = sprite;
		setBounds(x -(sprite.getOriginX()/2)+15, y - (sprite.getOriginY()/2)+20,(sprite.getWidth()*0.35f),(sprite.getHeight()*0.35f));
		setTouchable(Touchable.enabled);
	}

	@Override
	public void start() { }

	@Override
	public void setPosition(float x, float y) {
		sprite.setPosition(x - sprite.getOriginX(), y - sprite.getOriginY());
		
	}
    @Override
    protected void positionChanged() {
        sprite.setPosition(getX(),getY());
        super.positionChanged();
    }

	@Override
	public void setRotation(float degrees) {
		sprite.setRotation(degrees);
	}

	@Override
	public void draw(float delta, SpriteBatch batch) {
		sprite.draw(batch);
	}
    @Override
    public void act(float delta) {
        super.act(delta);
    }

	@Override
	public void setSize(float width, float height) {
		sprite.setSize(width, height);
	}

	@Override
	public void dispose() { }

	@Override
	public boolean isFinished() {
		return true;
	}
}
