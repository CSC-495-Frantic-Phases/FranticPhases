package edu.oswego.franticphases.widgets;

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


public class Hud extends Window {
	private final Label scoreDisplay;
	private final Label phaseDisplay;
	private final Label card1Display;
	private final Label card2Display;
	private final Label card3Display;
	private final Label card4Display;
	private final Label card5Display;
	private final Label card6Display;
	private final Label card7Display;
	private final Label card8Display;
	private final Label card9Display;
	private final Label card10Display;
	private  Label card11Display;
	private String discard;
	private Hand hand;
	private final GameScreen screen;
	

	
	public Hud(final GameScreen screen, final Skin skin) {
		super("Hand", skin);
		this.screen = screen;
		row().uniform().expandX();
		//add("Hand ").right().padLeft(10);

		scoreDisplay = new Label("Score", skin);
		phaseDisplay = new Label("Phase", skin);
		card1Display = new Label("1", skin);
		card2Display = new Label("2", skin);
		card3Display = new Label("3", skin);
		card4Display = new Label("4", skin);
		card5Display = new Label("5", skin);
		card6Display = new Label("6", skin);
		card7Display = new Label("7", skin);
		card8Display = new Label("8", skin);
		card9Display = new Label("9", skin);
		card10Display = new Label("10", skin);
		card11Display = new Label("11", skin);
		card11Display.setVisible(false);
		//add(scoreDisplay).left();
		//add(phaseDisplay).left();
		add(card1Display).pad(10);
		add(card2Display).pad(10);
		add(card3Display).pad(10);
		add(card4Display).pad(10);
		
		add(card5Display).pad(10);
		
		add(card6Display).pad(10);
		
        Button endTurnButton = new TextButton("Discard", skin);
        add(endTurnButton).pad(25);
        endTurnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	updateHand();
            	System.out.println("pushed");
            	screen.discard();
            }
        });
		
		
		row();
		add(card7Display).pad(10);
		
		add(card8Display).pad(10);
		add(card9Display).pad(10);
		add(card10Display).pad(10);
		add(card11Display).pad(10);
	
	}
	
	private void updateHand(){
		//change the cards in hand here
		//set discard to correct value selected
		System.out.println("discarded card 1");
		hand.setCard1("work?");
		this.setCard1("work?");
		
	}
	
	public String getDiscardedCard(){
		discard = "discarded";
		return discard;
	}
	
	public void setCard11(String c){
		card11Display.setText(c.trim());
		card11Display.setVisible(true);
	}


	
	
	
	public void setHand (Hand h){
		hand = h;
		setCard1(hand.getCard1());
		setCard2(hand.getCard2());
		setCard3(hand.getCard3());
		setCard4(hand.getCard4());
		setCard5(hand.getCard5());
		setCard6(hand.getCard6());
		setCard7(hand.getCard7());
		setCard8(hand.getCard8());
		setCard9(hand.getCard9());
		setCard10(hand.getCard10());
	}
	
	public void setScore(String n) {
		scoreDisplay.setText(n.trim());
	}
	
	public void setPhase(String p){
		phaseDisplay.setText(p.trim());
	}
	
	private void setCard1(String c){
		card1Display.setText(c.trim());
	}
	private void setCard2(String c){
		card2Display.setText(c.trim());
	}
	private void setCard3(String c){
		card3Display.setText(c.trim());
	}
	private void setCard4(String c){
		card4Display.setText(c.trim());
	}
	private void setCard5(String c){
		card5Display.setText(c.trim());
	}
	private void setCard6(String c){
		card6Display.setText(c.trim());
	}
	private void setCard7(String c){
		card7Display.setText(c.trim());
	}
	private void setCard8(String c){
		card8Display.setText(c.trim());
	}
	private void setCard9(String c){
		card9Display.setText(c.trim());
	}
	private void setCard10(String c){
		card10Display.setText(c.trim());
	}
	
}
