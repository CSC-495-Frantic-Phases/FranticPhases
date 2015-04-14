package edu.oswego.franticphases.gamelogic;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;

import edu.oswego.franticphases.settings.Settings;

public class CardGame {

	private ArrayList<Player> players;
	private int numOfPlayers = 0;
	private boolean isTurn = false;
	private Hand myHand;
	private Deck gameDeck;
	private String gameID;
	private String deckID;
	private String faceUpCard;
	//private String faceDownCard;
	private String userPlayerNum;
	
	public CardGame(JsonValue json){
		players = new ArrayList<Player>();
		gameID = json.getString("gameID");
		deckID = json.getString("deckID");
		faceUpCard = json.getString("faceUpCard");
		myHand = new Hand(json.get("hand"));
		JsonValue tmp = json.get("players");
		for(int i = 0; i < tmp.size; i++){
			JsonValue pl = tmp.get(i);
			String name = pl.getString("userName");
			
			String score = pl.getString("score");
			String phase = pl.getString("phase");
			String playerNum = pl.getString("playerNum");
			if(name.equals(Settings.getUsername())){
				userPlayerNum = playerNum;
			}
			Player player = new Player(name, score, phase, playerNum);
			players.add(player);
		}
	}
	
	public CardGame(String gameid){
		players = new ArrayList<Player>();
		gameID = gameid;
	}
	
	public void setGameID(String id){
		gameID = id;
	}
	
	public void createDeck(){
		
		gameDeck = new Deck();
		gameDeck.shuffle();
		gameDeck.shuffle();
		Gdx.app.log("Card Game", "Deck created and shuffled");
	}
	
	public String getIntCardsLeft(){
		  return String.valueOf(gameDeck.cardsLeft());
	}
	
	public String getCardsInDeckAsString(){
		return gameDeck.getCards();
	}
	
	public ArrayList<Hand> dealHands(){
		if(gameDeck != null){
		ArrayList<Hand> hands = new ArrayList<Hand>();
		for(int i = 0; i < numOfPlayers;i++){
			hands.add(new Hand());
		}
		for(int i = 0; i <10;i++){
			for(int j = 0; j<numOfPlayers;j++){
				hands.get(j).addCard(gameDeck.dealCard());
			}	
		}
		Card faceup = gameDeck.dealCard();
		faceUpCard =  faceup.getSuitAsString() + faceup.getValueAsString();
		
		return hands;
		}else{
			return null;
		}
		
	}
	
	
	public void addPlayer(Player p){
		if(numOfPlayers<4){
		players.add(p);
		numOfPlayers++;
		}
	}
	
	public void removePlayer(Player p){
		players.remove(p);
	}
	
	public int getNumPlayers(){
		return numOfPlayers;
	}
	
	public boolean isTurn(){
		return isTurn;
	}
	
	public void setTurn(boolean t){
		isTurn = t;
	}
	
	public ArrayList<Card> getHandCards(){
		return myHand.getCards();
	}
	
	public Hand getHand(){
		return myHand;
	}
	
	public String getGameID(){
		return gameID;
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public String getUserPlayerNum(){
		return userPlayerNum;
	}
	
	public String getDeckID(){
		return deckID;
	}
	
	public String getFaceUpCard(){
		return faceUpCard;
	}
	
	public void setFaceUpCard(String card){
		faceUpCard = card;
	}
	
}
