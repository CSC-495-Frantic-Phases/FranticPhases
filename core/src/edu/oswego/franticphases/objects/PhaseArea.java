package edu.oswego.franticphases.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;

import edu.oswego.franticphases.collisionListener.CardCollisionListener;
import edu.oswego.franticphases.gamelogic.DeckListener;
import edu.oswego.franticphases.gamelogic.UnitScale;
import edu.oswego.franticphases.graphics.GraphicComponent;
import edu.oswego.franticphases.graphics.SpriteGraphic;

public class PhaseArea extends AbstractWorldObject implements CardCollisionListener, Disposable, Audible, MapRenderable {
	public static final float FRICTION = 0.0f;
    public static final float DENSITY = 0.0f;
    public static final float RESTITUTION = 0.0f;
    public static final BodyType BODY_TYPE = BodyType.StaticBody;
	private SpriteGraphic  graphic;
	private boolean visible = true;
	private final TextureAtlas atlas;
	private final String soundFile = "data/soundfx/boing1.mp3";
	private final String atlasFile = "data/WorldObjects/cards.txt";
	private final UnitScale scale;

	//private final Sound sound;
	
	public PhaseArea(Body body,  UnitScale scale , AssetManager assetManager, float w, float h) {
		super(body);
		this.scale = scale;
		this.graphic = null;
		atlas = assetManager.get(atlasFile, TextureAtlas.class);
		
	}
	public void setGraphic(String cardID){
		Sprite sprite = atlas.createSprite(cardID);
		sprite.setScale(0.35f);
		//Gdx.app.log("HandCardObject", "Loaded card: " + cardID);
		
		graphic = new SpriteGraphic(getMapX(), getMapY(),sprite);
		
	}
	
	public void setListener(ClickListener clickListener, Stage stage){
		System.out.println("cl set");
		graphic.addListener(clickListener);
		stage.addActor(graphic);
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

	@Override
	public void drawBeforeBall(float delta, SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawAfterBall(float delta, SpriteBatch batch) {
		graphic.setPosition(getMapX(), getMapY());
		graphic.draw(delta, batch);
		
	}

	@Override
	public void handleBeginCollision(Contact contact, HandCardObject card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEndCollision(Contact contact, HandCardObject card) {
		// TODO Auto-generated method stub
		
	}
}
