package edu.oswego.franticphases.gamelogic;

import com.badlogic.gdx.utils.JsonValue;

import edu.oswego.franticphases.GameSession;
import edu.oswego.franticphases.datasending.DataSender;
import edu.oswego.franticphases.datasending.WebCallback;
import edu.oswego.franticphases.widgets.Hud;

public class GameHandler {
	private WebCallback callBack;
	private Hand hand;
	private Game game;
	private Hud hud;
	private boolean turn = false;
	private String gameID;
	private boolean discardUpdated = false;
	private boolean handUpdated = false;

	public GameHandler(String game, Hud h) {
		gameID = game;
		hud = h;
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
		return turn;
	}

	public String getFaceUpCard() {
		if (game == null) {
			return "uhh";
		}
		return game.getFaceUpCard();
	}

	public void faceUpCardSelected() {
		hud.setCard11(game.getFaceUpCard());
	}

	public void endTurn() {
		// update DB
		// discarded card first
		// then toggle turn
		// then update hand
		// updates phases down
		updateDiscardedCard(hud.getDiscardedCard());

	}

	// methods for the data sender
	public void setGame(JsonValue json) {
		game = new Game(json);
		hand = new Hand(json.get("hand"));
		hud.setHand(hand);
		this.checkIsTurn();
	}

	public void setFaceUpCard(String card) {
		// called from updateFaceUpCard()
		game.setFaceUpCard(card);
	}

	public void setTurn(boolean t) {
		// called from checkTurn()
		turn = t;
	}

	public void toggleNextPlayer() {
		// called from end turn()
		if (callBack.getRecieved()) {
			callBack = new WebCallback();
			DataSender aSender = new DataSender();
			aSender.toggleNextPlayer(gameID, game.getUserPlayerNum(), callBack);
		}
	}
	public void updateHand() {
		// called from end turn()
		if (callBack.getRecieved()) {
			callBack = new WebCallback();
			DataSender aSender = new DataSender();
			aSender.updateHand(hand, callBack);
		}
	}


	
	// private methods to send data
	private void loadGameData() {

		callBack = new WebCallback();
		DataSender aSender = new DataSender();
		aSender.loadGameData(gameID, callBack, this);
	}

	private void checkIsTurn() {
		if (callBack.getRecieved()) {
			callBack = new WebCallback();
			DataSender aSender = new DataSender();
			aSender.checkIfTurn(gameID, game.getUserPlayerNum(), callBack, this);
		}
	}

	private void updateDiscardedCard(String card) {
		if (callBack.getRecieved()) {
			callBack = new WebCallback();
			DataSender aSender = new DataSender();
			// next player would need face down card updated
			aSender.cardDiscarded(gameID, card, callBack, this);
			game.setFaceUpCard(card);
		}
	}

	private void updateFaceUpCard() {
		if (callBack.getRecieved()) {
			callBack = new WebCallback();
			DataSender aSender = new DataSender();
			// next player would need face down card updated
			aSender.updateFaceUpCard(gameID, callBack, this);
		}
	}

	private void updatePhasesDown() {

	}

}
