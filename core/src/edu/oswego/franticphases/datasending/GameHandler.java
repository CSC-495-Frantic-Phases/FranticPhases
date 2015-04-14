package edu.oswego.franticphases.datasending;

import com.badlogic.gdx.utils.JsonValue;

import edu.oswego.franticphases.gamelogic.CardGame;
import edu.oswego.franticphases.screens.GameScreen;
import edu.oswego.franticphases.widgets.Hud;

public class GameHandler {
	private WebCallback callBack;
	private CardGame game;
	GameScreen screen;

	public GameHandler(GameScreen s, CardGame g) {
		game = g;
		screen = s;
	}

	public void loadGame() {
		this.loadGameData();
	}

	// public methods for the Game Screen to call
	public void update() {
		this.checkIsTurn();
		this.updateFaceUpCard();
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
		//screen.setHand(game.getHandCards());
		this.checkIsTurn();
	}

	public void setFaceUpCard(String card) {
		// called from updateFaceUpCard()
		game.setFaceUpCard(card);
	}

	public void setTurn(boolean t) {
		// called from checkTurn()
		game.setTurn(t);
	}

	public void toggleNextPlayer() {
		// called from end turn()
		if (callBack.getRecieved()) {
			callBack = new WebCallback();
			DataSender aSender = new DataSender();
			aSender.toggleNextPlayer(game, callBack);
		}
	}
	public void updateHand() {
		// called from end turn()
		if (callBack.getRecieved()) {
			callBack = new WebCallback();
			DataSender aSender = new DataSender();
			aSender.updateHand(game.getHand(), callBack);
		}
	}


	
	// private methods to send data
	private void loadGameData() {

		callBack = new WebCallback();
		DataSender aSender = new DataSender();
		aSender.loadGameData(game.getGameID(), callBack, this);
	}

	private void checkIsTurn() {
		if (callBack.getRecieved()) {
			callBack = new WebCallback();
			DataSender aSender = new DataSender();
			aSender.checkIfTurn(game, callBack, this);
		}
	}

	private void updateDiscardedCard(String card) {
		if (callBack.getRecieved()) {
			callBack = new WebCallback();
			DataSender aSender = new DataSender();
			// next player would need face down card updated
			aSender.cardDiscarded(game.getGameID(), card, callBack, this);
			game.setFaceUpCard(card);
		}
	}

	private void updateFaceUpCard() {
		if (callBack.getRecieved()) {
			callBack = new WebCallback();
			DataSender aSender = new DataSender();
			// next player would need face down card updated
			aSender.updateFaceUpCard(game.getGameID(), callBack, this);
		}
	}

	private void updatePhasesDown() {

	}

}
