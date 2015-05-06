package edu.oswego.franticphases.gamelogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import edu.oswego.franticphases.objects.HandCardObject;

public class CardListener extends DragListener{

	
	private String cardID;
	private HandCardObject card;
	private OrthographicCamera camera;
	
	public CardListener(HandCardObject c, String cardID, OrthographicCamera cam){
		card = c;
		this.cardID = cardID;
		camera = cam;
	}

	@Override
	public void dragStart (InputEvent event, float x, float y, int pointer) {
	
	}
	@Override
	public void drag (InputEvent event, float x, float y, int pointer) {
		Vector3 touchPosition = new Vector3();
	    camera.unproject(touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0));
	    card.moveCard(touchPosition.x,touchPosition.y); 
	}
	@Override
	public void dragStop (InputEvent event, float x, float y, int pointer) {
		//Gdx.app.log("CardListener", "drag stop: "+ cardID +" "+ x + " : " + y);
		Vector3 touchPosition = new Vector3();
	    camera.unproject(touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0));
	    card.returnToStart();
		
	}
	
}
