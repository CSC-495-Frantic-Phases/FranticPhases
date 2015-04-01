package edu.oswego.franticphases.datasending;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import sun.awt.windows.ThemeReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import edu.oswego.franticphases.GameSession;
import edu.oswego.franticphases.User;
import edu.oswego.franticphases.gamelogic.GameHandler;
import edu.oswego.franticphases.gamelogic.Hand;
import edu.oswego.franticphases.gamelogic.Handler;
import edu.oswego.franticphases.settings.Settings;

public class DataSender {
	boolean debug = true;
	public DataSender(){

	}

	
	public void createNewAccount(String _name, String _password, final WebCallback _theCallback){
		if(debug){
			System.out.println("CREATE NEW ACCOUNT");
		}
		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("username", _name);
		parameters.put("password", _password);
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		//httpPost.setUrl("http://localhost:1234/webservice/register.php");
		httpPost.setUrl("http://moxie.cs.oswego.edu/~maestri/register.php");
		//httpPost.setUrl(" ");
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		//System.out.println("username: " +_name);
		 Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        		

		        	InputStream is = httpResponse.getResultAsStream();
		        		JsonReader jr = new JsonReader();
		        		JsonValue jsonVal = jr.parse(is);
		        		parseLoginData(jsonVal, _theCallback);
		        }
		 
		        public void failed(Throwable t) {
		        		System.out.println("Create New Account Failed " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	public void login(String _name, String _password, final WebCallback _theCallback){
		if(debug){
			System.out.println("LOGIN");
		}
		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("username", _name);
		parameters.put("password", _password);
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		//httpPost.setUrl("http://localhost:1234/webservice/login.php");
		httpPost.setUrl("http://moxie.cs.oswego.edu/~maestri/login.php");
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        	InputStream is = httpResponse.getResultAsStream();
	        		JsonReader jr = new JsonReader();
	        		JsonValue jsonVal = jr.parse(is);
	        		//System.out.println("Json from login: "+jsonVal.toString());
	        		parseLoginData(jsonVal, _theCallback);

		        }
		 
		        public void failed(Throwable t) {
		        		System.out.println("Failed in login " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	public void getGames(String id, final WebCallback _theCallback, final Handler handler){
		if(debug){
			System.out.println("GETTING GAME SESSIONS");
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
		        		System.out.println("Failed in get games " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	public void getUsers(final WebCallback _theCallback, final Handler handler){
		if(debug){
			System.out.println("getting users");
		}
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://moxie.cs.oswego.edu/~maestri/getUsers.php");
		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        	InputStream is = httpResponse.getResultAsStream();
	        		JsonReader jr = new JsonReader();
	        		JsonValue jsonVal = jr.parse(is);
	        		//System.out.println("Json from get users: "+jsonVal.toString());
	        		parseUserData(jsonVal, _theCallback, handler);

		        }
		 
		        public void failed(Throwable t) {
		        		System.out.println("Failed in get users " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	
	public void createGame(ArrayList<User> players, final WebCallback _theCallback, final Handler handler){
		if(debug){
			System.out.println("CREATING GAME");
		}
		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("player1", players.get(0).getUserID());
		parameters.put("player2", players.get(1).getUserID());
		parameters.put("player3", players.get(2).getUserID());
		parameters.put("player4", players.get(3).getUserID());
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://moxie.cs.oswego.edu/~maestri/createGame.php");
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        	InputStream is = httpResponse.getResultAsStream();
	        		JsonReader jr = new JsonReader();
	        		JsonValue jsonVal = jr.parse(is);
	        		parseCreateGameData(jsonVal, _theCallback, handler);

		        }
		 
		        public void failed(Throwable t) {
		        		System.out.println("Failed in create game " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	

	public void loadGameData(String gameID, final WebCallback _theCallback, final GameHandler handler){
		if(debug){
			System.out.println("GETTING GAME");
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
		        		System.out.println("Failed in load game data " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	public void checkIfTurn(String gameID, String playerNum, final WebCallback _theCallback, final GameHandler handler){

		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("gameID", gameID);
		parameters.put("playerNum", playerNum);
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
		        		System.out.println("Failed in check if turn" + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	public void toggleNextPlayer(String gameID, String playerNum, final WebCallback _theCallback){
		if(debug){
			System.out.println("TOGGLE NEXT PLAYER");
		}
		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("gameID", gameID);
		parameters.put("playerNum", playerNum);
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
		        		System.out.println("Failed in toggle next player " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	public void cardDiscarded(String gameID, String card, final WebCallback _theCallback, final GameHandler handler){
		if(debug){
			System.out.println("DISCARD");
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
		        		System.out.println("Failed in discard card " + t.getMessage());
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
		        		System.out.println("Failed in update faceup card" + t.getMessage());
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
		parameters.put("card01", hand.getCard1());
		parameters.put("card02", hand.getCard2());
		parameters.put("card03", hand.getCard3());
		parameters.put("card04", hand.getCard4());
		parameters.put("card05", hand.getCard5());
		parameters.put("card06", hand.getCard6());
		parameters.put("card07", hand.getCard7());
		parameters.put("card08", hand.getCard8());
		parameters.put("card09", hand.getCard9());
		parameters.put("card10", hand.getCard10());
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
		        		System.out.println("Failed in update Hand" + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	private void parseAckData(JsonValue theResult, WebCallback _theCallback){
		System.out.println(theResult.toString());
		boolean result = theResult.getBoolean("success");
		String message = "";
		
		if (result){
			message = theResult.getString("message");
		}
		
		_theCallback.setData(result, message);
	}

	private void parseFaceUpData(JsonValue theResult, WebCallback _theCallback, GameHandler handler){
		System.out.println(theResult.toString());
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
		System.out.println(theResult.toString());
		boolean result = theResult.getBoolean("success");
		String message = "";
		if (result){
			message = theResult.getString("message");
			handler.setGame(theResult);
		}	
		_theCallback.setData(result, message);
	}
	private void parseTurnData(JsonValue theResult, WebCallback _theCallback, GameHandler handler){
		System.out.println(theResult.toString());
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
		//System.out.println(theResult.toString());
		boolean result = theResult.getBoolean("success");
		String message = "";
		String id = "";
		
		if (result){
			message = theResult.getString("message");
			id = theResult.getString("userID");
		}
		
		_theCallback.setData(result, message, id);
	}
	
	private void parseCreateGameData(JsonValue theResult, WebCallback _theCallback, Handler handler){
		//System.out.println(theResult.toString());
		boolean result = theResult.getBoolean("success");
		String message = "";
		
		if (result){
			message = theResult.getString("message");
			String gameID = theResult.getString("gameID");
			handler.setNewGame(gameID);
		}
		
		_theCallback.setData(result, message);
		
	}
	private void parseGameSessionData(JsonValue theResult, WebCallback _theCallback, Handler handler){
		//System.out.println(theResult.toString());
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
		//System.out.println(theResult.toString());
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
