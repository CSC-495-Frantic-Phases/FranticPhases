package edu.oswego.franticphases.datasending;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import edu.oswego.franticphases.gamelogic.Card;
import edu.oswego.franticphases.gamelogic.CardGame;
import edu.oswego.franticphases.gamelogic.Hand;
import edu.oswego.franticphases.gamelogic.Player;
import edu.oswego.franticphases.settings.Settings;

public class DataSender {
	boolean debug = true;
	public DataSender(){

	}

	
	public void createNewAccount(String _name, String _password, final WebCallback _theCallback){
		if(debug){
			Gdx.app.log("DATA SENDER","CREATE NEW ACCOUNT");
		}
		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("username", _name);
		parameters.put("password", _password);
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://moxie.cs.oswego.edu/~maestri/register.php");
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		 Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        	InputStream is = httpResponse.getResultAsStream();
		        		JsonReader jr = new JsonReader();
		        		JsonValue jsonVal = jr.parse(is);
		        		parseLoginData(jsonVal, _theCallback);
		        }
		 
		        public void failed(Throwable t) {
		        	Gdx.app.log("DATA SENDER","Create New Account Failed " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	public void login(String _name, String _password, final WebCallback _theCallback){
		if(debug){
			Gdx.app.log("DATA SENDER","LOGIN");
		}
		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("username", _name);
		parameters.put("password", _password);
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://moxie.cs.oswego.edu/~maestri/login.php");
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        	InputStream is = httpResponse.getResultAsStream();
	        		JsonReader jr = new JsonReader();
	        		JsonValue jsonVal = jr.parse(is);
	        		parseLoginData(jsonVal, _theCallback);
		        }
		 
		        public void failed(Throwable t) {
		        	Gdx.app.log("DATA SENDER","Failed in login " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	public void getGames(String id, final WebCallback _theCallback, final Handler handler){
		if(debug){
			Gdx.app.log("DATA SENDER", "GETTING GAME SESSIONS");
		}
		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("userID", id);
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://moxie.cs.oswego.edu/~maestri/getGameSessions.php");
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        	InputStream is = httpResponse.getResultAsStream();
	        		JsonReader jr = new JsonReader();
	        		JsonValue jsonVal = jr.parse(is);
	        		parseGameSessionData(jsonVal, _theCallback, handler);

		        }
		 
		        public void failed(Throwable t) {
		        	Gdx.app.log("DATA SENDER","Failed in get games " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	public void getUsers(final WebCallback _theCallback, final Handler handler){
		if(debug){
			Gdx.app.log("DATA SENDER","getting users");
		}
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://moxie.cs.oswego.edu/~maestri/getUsers.php");
		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        	InputStream is = httpResponse.getResultAsStream();
	        		JsonReader jr = new JsonReader();
	        		JsonValue jsonVal = jr.parse(is);
	        		parseUserData(jsonVal, _theCallback, handler);

		        }
		 
		        public void failed(Throwable t) {
		        	Gdx.app.log("DATA SENDER","Failed in get users " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	
	public void createGame(final CardGame game, final WebCallback _theCallback, final Handler handler){
		if(debug){
			Gdx.app.log("DATA SENDER","CREATING GAME");
		}
		ArrayList<Hand> hands = game.dealHands();
		ArrayList<Player> players = game.getPlayers();
		
		if (hands.size() != players.size()) {
			Gdx.app.log("DATA SENDER",
					"number of hands != number of players");
		}
		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("numCardsUsed", game.getIntCardsLeft());
		parameters.put("cards", game.getCardsInDeckAsString());
		parameters.put("numPlayers", String.valueOf(game.getNumPlayers()));
		parameters.put("faceUp", game.getFaceUpCard());

		for (int j = 0; j < game.getNumPlayers(); j++) {
			int turn;
			if(j == 0){
				turn = 1;
			}else{
				turn = 0;
			}
			parameters.put("userID"+String.valueOf(j+1), players.get(j).getID());
			parameters.put("number"+String.valueOf(j+1), String.valueOf(j + 1));
			parameters.put("turn"+String.valueOf(j+1), String.valueOf(turn));
			ArrayList<Card> cards = hands.get(j).getCards();
			for (int i = 0; i < cards.size(); i++) {
				Card c = cards.get(i);
				parameters.put("c"+String.valueOf(j+1) + String.valueOf(i+1),
						c.getSuitAsString() + c.getValueAsString());
			}
		}
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://moxie.cs.oswego.edu/~maestri/createGame.php");
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        	InputStream is = httpResponse.getResultAsStream();
	        		JsonReader jr = new JsonReader();
	        		JsonValue jsonVal = jr.parse(is);
	        		parseCreateGameData(game, jsonVal, _theCallback, handler);
	        		
		        }
		 
		        public void failed(Throwable t) {
		        	Gdx.app.log("DATA SENDER","Failed in create game " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	
	private void parseCreateGameData(CardGame game, JsonValue theResult, WebCallback _theCallback, Handler handler){
		Gdx.app.log("DATA SENDER",theResult.toString());
		boolean result = theResult.getBoolean("success");
		String message = "";
		
		if (result){
			message = theResult.getString("message");
			String gameID = theResult.getString("gameID");
			game.setGameID(gameID);
			handler.setNewGame(gameID);
		}
		
		_theCallback.setData(result, message);
		
	}
	

	public void loadGameData(String gameID, final WebCallback _theCallback, final GameHandler handler){
		if(debug){
			Gdx.app.log("DATA SENDER","GETTING GAME"+gameID);
		}
		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("gameID", gameID);
		parameters.put("userID", Settings.getUserID());
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://moxie.cs.oswego.edu/~maestri/loadGame.php");
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        	InputStream is = httpResponse.getResultAsStream();
	        		JsonReader jr = new JsonReader();
	        		JsonValue jsonVal = jr.parse(is);
	        		parseGameData(jsonVal, _theCallback, handler);

		        }
		 
		        public void failed(Throwable t) {
		        	Gdx.app.log("DATA SENDER","Failed in load game data " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	public void checkIfTurn(CardGame game, final WebCallback _theCallback, final GameHandler handler){

		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("gameID", game.getGameID());
		parameters.put("playerNum", game.getUserPlayerNum());
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://moxie.cs.oswego.edu/~maestri/checkTurn.php");
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        	InputStream is = httpResponse.getResultAsStream();
	        		JsonReader jr = new JsonReader();
	        		JsonValue jsonVal = jr.parse(is);
	        		parseTurnData(jsonVal, _theCallback, handler);

		        }
		 
		        public void failed(Throwable t) {
		        	Gdx.app.log("DATA SENDER","Failed in check if turn" + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	public void toggleNextPlayer(CardGame game, final WebCallback _theCallback){
		if(debug){
			Gdx.app.log("DATA SENDER","TOGGLE NEXT PLAYER");
		}
		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("gameID", game.getGameID());
		parameters.put("playerNum", game.getUserPlayerNum());
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://moxie.cs.oswego.edu/~maestri/toggleNext.php");
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        	InputStream is = httpResponse.getResultAsStream();
	        		JsonReader jr = new JsonReader();
	        		JsonValue jsonVal = jr.parse(is);
	        		parseAckData(jsonVal, _theCallback);

		        }
		 
		        public void failed(Throwable t) {
		        	Gdx.app.log("DATA SENDER","Failed in toggle next player " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	public void cardDiscarded(String gameID, String card, final WebCallback _theCallback, final GameHandler handler){
		if(debug){
			Gdx.app.log("DATA SENDER","DISCARD");
		}
		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("gameID", gameID);
		parameters.put("card", card);
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://moxie.cs.oswego.edu/~maestri/discardCard.php");
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        	InputStream is = httpResponse.getResultAsStream();
	        		JsonReader jr = new JsonReader();
	        		JsonValue jsonVal = jr.parse(is);
	        		parseAckData(jsonVal, _theCallback);
	        		handler.toggleNextPlayer();

		        }
		 
		        public void failed(Throwable t) {
		        	Gdx.app.log("DATA SENDER","Failed in discard card " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	public void updateFaceUpCard(String gameID,  final WebCallback _theCallback, final GameHandler handler){
		
		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("gameID", gameID);
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://moxie.cs.oswego.edu/~maestri/checkFaceUpCard.php");
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        	InputStream is = httpResponse.getResultAsStream();
	        		JsonReader jr = new JsonReader();
	        		JsonValue jsonVal = jr.parse(is);
	        		parseFaceUpData(jsonVal, _theCallback, handler);

		        }
		 
		        public void failed(Throwable t) {
		        	Gdx.app.log("DATA SENDER","Failed in update faceup card" + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	public void updateHand(Hand hand,  final WebCallback _theCallback){
		
		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("handID", hand.getHandID());
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://moxie.cs.oswego.edu/~maestri/updateHand.php");
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        	InputStream is = httpResponse.getResultAsStream();
	        		JsonReader jr = new JsonReader();
	        		JsonValue jsonVal = jr.parse(is);
	        		parseAckData(jsonVal, _theCallback);
		        }
		 
		        public void failed(Throwable t) {
		        	Gdx.app.log("DATA SENDER","Failed in update Hand" + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	private void parseAckData(JsonValue theResult, WebCallback _theCallback){
		Gdx.app.log("DATA SENDER",theResult.toString());
		boolean result = theResult.getBoolean("success");
		String message = "";
		
		if (result){
			message = theResult.getString("message");
		}
		
		_theCallback.setData(result, message);
	}

	private void parseFaceUpData(JsonValue theResult, WebCallback _theCallback, GameHandler handler){
		Gdx.app.log("DATA SENDER",theResult.toString());
		boolean result = theResult.getBoolean("success");
		String message = "";
		if (result){
			message = theResult.getString("message");
			String card = theResult.getString("card");
			handler.setFaceUpCard(card);

		}	
		_theCallback.setData(result, message);
	}
	
	private void parseGameData(JsonValue theResult, WebCallback _theCallback, GameHandler handler){
		Gdx.app.log("DATA SENDER",theResult.toString());
		boolean result = theResult.getBoolean("success");
		String message = "";
		if (result){
			message = theResult.getString("message");
			handler.setGame(theResult);
		}	
		_theCallback.setData(result, message);
	}
	private void parseTurnData(JsonValue theResult, WebCallback _theCallback, GameHandler handler){
		Gdx.app.log("DATA SENDER",theResult.toString());
		boolean result = theResult.getBoolean("success");
		String message = "";
		if (result){
			message = theResult.getString("message");
			String well = theResult.getString("isTurn");
			boolean turn;
			//System.out.println("TURN: "+well);
			
			if(well.equals("1")){
				turn = true;
			}else{
				turn = false;
			}
			handler.setTurn(turn);
		}	
		_theCallback.setData(result, message);
	}
	
	
	
	private void parseLoginData(JsonValue theResult, WebCallback _theCallback){
		//Gdx.app.log("DATA SENDER",theResult.toString());
		boolean result = theResult.getBoolean("success");
		String message = "";
		String id = "";
		
		if (result){
			message = theResult.getString("message");
			id = theResult.getString("userID");
		}
		
		_theCallback.setData(result, message, id);
	}
	

	
	
	private void parseGameSessionData(JsonValue theResult, WebCallback _theCallback, Handler handler){
		//Gdx.app.log("DATA SENDER",theResult.toString());
		boolean result = theResult.getBoolean("success");
		String message = "";
		
		if (result){
			message = theResult.getString("message");
			JsonValue tmp = theResult.get("games");
			//System.out.println(tmp.toString());
			handler.setGameSessionArray(tmp);

		}else{
		handler.setGameSessionArray(null);
		
		}
		_theCallback.setData(result, message);
	}
	
	private void parseUserData(JsonValue theResult, WebCallback _theCallback, Handler handler){
		//Gdx.app.log("DATA SENDER",theResult.toString());
		boolean result = theResult.getBoolean("success");
		String message = "no";
		
		if (result){
			message = theResult.getString("message");
			JsonValue tmp = theResult.get("usernames");
			handler.setUserListArray(tmp);
		}
		_theCallback.setData(result, message);
	}
	

}
