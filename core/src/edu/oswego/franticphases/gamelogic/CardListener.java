package edu.oswego.franticphases.gamelogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public class CardListener extends DragListener{

	
	
	public CardListener(){
		
	}
	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer){
		Gdx.app.log("CardListener", "touch dragged");
		
	}
	@Override
	public void dragStop(InputEvent event,float x,float y,int pointer){
		Gdx.app.log("CardListener", "drag stop");
		
	}

	@Override
  public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
  	Gdx.app.log("sdfcs", "touch dragged");
      return true;
  }
	
}
