package edu.oswego.franticphases.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class SpriteGraphic extends Actor implements GraphicComponent  {
	private final Sprite sprite;

	public SpriteGraphic(Sprite sprite) {
		this.sprite = sprite;
		setBounds(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());
        setTouchable(Touchable.enabled);

//        this.addListener(new InputListener(){
//        	@Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//            	Gdx.app.log("sdfcs", "touch dragged");
//                return true;
//            }
//        });
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
