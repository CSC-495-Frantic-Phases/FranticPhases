package edu.oswego.franticphases.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Disposable;

import edu.oswego.franticphases.gamelogic.CardListener;
import edu.oswego.franticphases.gamelogic.UnitScale;
import edu.oswego.franticphases.graphics.GraphicComponent;
import edu.oswego.franticphases.graphics.SpriteGraphic;


public class HandCardObject extends AbstractWorldObject implements Disposable, Audible {

	public static final float FRICTION = 0.0f;
    public static final float DENSITY = 0.0f;
    public static final float RESTITUTION = 0.0f;
    public static final BodyType BODY_TYPE = BodyType.StaticBody;
	private SpriteGraphic graphic;
	private boolean visible = true;
	private final TextureAtlas atlas;
	private final String soundFile = "data/soundfx/boing1.mp3";
	private final String atlasFile = "data/WorldObjects/cards.txt";
	private final UnitScale scale;
	private float width;
	private float height;
	private Vector2 startV;
	private float startX;
	private float startY;
	//private final Sound sound;
	
	public HandCardObject(Body body, UnitScale scale , AssetManager assetManager, float w, float h) {
		super(body);
		this.scale = scale;
		this.graphic = null;
		this.width = w;
		this.height = h;
		atlas = assetManager.get(atlasFile, TextureAtlas.class);

	}
	
	public void draw(float delta, SpriteBatch batch) {
		if (visible && graphic !=null) {
			graphic.setPosition(getMapX(), getMapY());
			graphic.draw(delta, batch);
		}
	}
	
	
	public void setGraphic(String cardID, Stage stage, OrthographicCamera cam){
		Sprite sprite = atlas.createSprite(cardID);
		sprite.setScale(0.35f);
		sprite.setPosition(getMapX(), getMapY());
		
		graphic = new SpriteGraphic(getMapX(), getMapY(), sprite);
		stage.addActor(graphic);
		graphic.addListener(new CardListener(this,cardID, cam));
		startX = body.getPosition().x;
		startY = body.getPosition().y;
		//Gdx.app.log("Card", "start" + body.getPosition().x + " : " + body.getPosition().y);
	}
	
	public void moveCard(float x, float y){
		body.setTransform(scale.pixelsToMeters(x), scale.pixelsToMeters(y), 0);
	  
	}
	
	public void updateBounds(float x, float y){
		graphic.setBounds(scale.pixelsToMeters(x), scale.pixelsToMeters(y),(graphic.getWidth()*0.35f),(graphic.getHeight()*0.35f));
		  
	}
	
	public void returnToStart(){
		//Gdx.app.log("Card", "returned to hand ");
		body.setTransform(startX, startY, 0);
		//Vector2 directionBody = body.getPosition();

	    //Vector2 directionTouch = new Vector2(startX, startY); 

	   // directionTouch.nor();

	   // float speed = 3;
	  //  body.setLinearVelocity(directionTouch.scl(speed));
	  //  body.applyLinearImpulse(directionTouch, point, wake);
		
		//Gdx.app.log("Card", "" + body.getPosition().x + " : " + body.getPosition().y);
		  
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
