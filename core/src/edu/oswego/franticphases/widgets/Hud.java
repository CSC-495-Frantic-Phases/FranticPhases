package edu.oswego.franticphases.widgets;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import edu.oswego.franticphases.gamelogic.Hand;
import edu.oswego.franticphases.screens.GameScreen;
import edu.oswego.franticphases.screens.GameSelectScreen;
import edu.oswego.franticphases.gamelogic.Card;


public class Hud extends Window {


	private ArrayList<Card> cards;
	private final GameScreen screen;
	

	
	public Hud(final GameScreen screen, final Skin skin) {
		super("Hand", skin);
		this.screen = screen;
		row().uniform().expandX();
		//add("Hand ").right().padLeft(10);


		
        Button endTurnButton = new TextButton("Discard", skin);
        add(endTurnButton).pad(25);
        endTurnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            	System.out.println("pushed");
            	screen.discard();
            }
        });
		
		

	
	}
	
	public void setHand (ArrayList<Card> c){
		cards = c;
	}
	public void addCard(Card newCard){
		cards.add(newCard);
	}
	public void removeCard(Card toBeRemoved){
		cards.remove(toBeRemoved);
	}
	
}
