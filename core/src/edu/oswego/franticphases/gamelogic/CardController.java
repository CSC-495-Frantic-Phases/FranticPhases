package edu.oswego.franticphases.gamelogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import edu.oswego.franticphases.objects.HandCardObject;

public class CardController extends DragListener {

	public CardController(){
		
	}
	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer){
		//Gdx.app.log("CardController", "touch registered");
		
		HandCardObject c = (HandCardObject) event.getRelatedActor();
		if(c != null){
			Gdx.app.log("CardController", "touch registered");
			c.getBody().setTransform(x, y, 0);
			
			
		}
		
	}
	
	
}
