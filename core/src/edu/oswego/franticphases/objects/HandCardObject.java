package edu.oswego.franticphases.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Disposable;

import edu.oswego.franticphases.gamelogic.UnitScale;
import edu.oswego.franticphases.graphics.GraphicComponent;
import edu.oswego.franticphases.graphics.SpriteGraphic;


public class HandCardObject extends AbstractWorldObject implements Disposable, Audible {

	public static final float FRICTION = 0.0f;
    public static final float DENSITY = 0.0f;
    public static final float RESTITUTION = 0.0f;
    public static final BodyType BODY_TYPE = BodyType.StaticBody;
	private GraphicComponent graphic;
	private boolean visible = true;
	private final TextureAtlas atlas;
	private final String soundFile = "data/soundfx/boing1.mp3";
	private final String atlasFile = "data/WorldObjects/worldObjects.txt";
	private final UnitScale scale;
	private float width;
	private float height;
	//private final Sound sound;
	
	public HandCardObject(Body body, UnitScale scale , AssetManager assetManager, float w, float h) {
		super(body);
		this.scale = scale;
		this.graphic = null;
		this.width = w;
		this.height = h;
		atlas = assetManager.get(atlasFile, TextureAtlas.class);
		//String soundFile = "data/soundfx/boing1.mp3";
//		if (!assetManager.isLoaded(soundFile)) {
//			assetManager.load(soundFile, Sound.class);
//			assetManager.finishLoading();
//		}
//		sound = assetManager.get(soundFile, Sound.class);
		
	}
	
	public void draw(float delta, SpriteBatch batch) {
		if (visible && graphic !=null) {
			
			graphic.setPosition(scale.metersToPixels(body.getPosition().x),
					scale.metersToPixels(body.getPosition().y));
			graphic.draw(delta, batch);
		}
	}
	
	public void setGraphic(String cardID){
		Sprite sprite = atlas.createSprite(cardID);
		//sprite.setSize(width, height);
		Gdx.app.log("HandCardObject", "height: " + sprite.getHeight()+" width: " +sprite.getWidth());
		
		graphic = new SpriteGraphic(sprite);
		
	}
	
	public float getMapX() {
		return scale.metersToPixels(body.getPosition().x);
	}

	public float getMapY() {
		return scale.metersToPixels(body.getPosition().y);
	}

	@Override
	public void setPlaySound(boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playSound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endSound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		graphic.dispose();
		
	}

}
