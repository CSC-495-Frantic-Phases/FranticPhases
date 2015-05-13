package edu.oswego.franticphases.datasending;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;

import edu.oswego.franticphases.gamelogic.Card;
import edu.oswego.franticphases.gamelogic.CardGame;
import edu.oswego.franticphases.gamelogic.Hand;
import edu.oswego.franticphases.gamelogic.Player;
import edu.oswego.franticphases.screens.GameScreen;
import edu.oswego.franticphases.widgets.Hud;

public class GameHandler {
	
	private CardGame game;
	private String gameID;

	public GameHandler(String g) {
		gameID = g;
		
	}

	public void loadGame(WebCallback callBack) {
		this.loadGameData(callBack);
		
	}
	

	// public methods for the Game/Load Screen to call
	public void update() {
		//this.checkIsTurn();
		//this.updateFaceUpCard();
	}
	
	public ArrayList<Player> getPlayers(){
		ArrayList<Player> retArray = new ArrayList<Player>();
		for(Player p:game.getPlayers()){
			if(p.getID()!=game.getUserPlayerNum()){
				retArray.add(p);
			}
		}
		return retArray;
		
	}
	
	public String selectFaceUpCard(){
		String rVal = game.getFaceUpCard();
		game.setFaceUpCard("back");
		return rVal;
	}
	
	public String getDeckCard(){
		return game.getDeckCard();
	}
	
	public String getScore(){
		return game.getPlayerData(game.getUserPlayerNum()).getScore();
	}
	
	public String getPhaseNumber(){
		return game.getPlayerData(game.getUserPlayerNum()).getPhase();
	}
	
	public ArrayList<String> getCards(){
		return game.getHandCardsStrings();
	}
	public Hand getHand(){
		return game.getHand();
	}

	public boolean getIsTurn() {
		return game.isTurn();
	}

	public String getFaceUpCard() {
		if (game == null) {
			return "uhh";
		}
		return game.getFaceUpCard();
	}

	public void faceUpCardSelected() {
		//hud.setCard11(game.getFaceUpCard());
	}

	public void endTurn() {
		// update DB
		// discarded card first
		// then toggle turn
		// then update hand
		// updates phases down
		//updateDiscardedCard(hud.getDiscardedCard());

	}

	
	
	// methods for the data sender
	public void setGame(JsonValue json) {
		game = new CardGame(json);
			
	}

	public void setFaceUpCard(String card) {
		// called from updateFaceUpCard()
		game.setFaceUpCard(card);
	}

	public void setTurn(boolean t) {
		// called from checkTurn()
		game.setTurn(t);
	}
	
	
	// private methods to send data
	public void toggleNextPlayer(WebCallback callBack) {
		// called from end turn()
	
			DataSender aSender = new DataSender();
			aSender.toggleNextPlayer(game, callBack);
		
	}
	public void updateHand(WebCallback callBack) {
		// called from end turn()
		
			DataSender aSender = new DataSender();
			aSender.updateHand(game.getHand(), callBack);
		
	}
	
    private void loadGameData(WebCallback callBack) {
        DataSender aSender = new DataSender();
		aSender.loadGameData(gameID, callBack, this);
	}

	private void checkIsTurn(WebCallback callBack) {
		
	
			DataSender aSender = new DataSender();
			aSender.checkIfTurn(game, callBack, this);
		
	}

	private void updateDiscardedCard(WebCallback callBack, String card) {
		
			DataSender aSender = new DataSender();
			// next player would need face down card updated
			aSender.cardDiscarded(game.getGameID(), card, callBack, this);
			game.setFaceUpCard(card);
		
	}

	private void updateFaceUpCard(WebCallback callBack) {
		
			DataSender aSender = new DataSender();
			// next player would need face down card updated
			aSender.updateFaceUpCard(game.getGameID(), callBack, this);
		
	}

	private void updatePhasesDown() {

	}

}
