package edu.oswego.franticphases.datasending;

import java.io.InputStream;
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

import edu.oswego.franticphases.gamelogic.Handler;

public class DataSender {
	
	public DataSender(){

	}

	
	public void createNewAccount(String _name, String _password, final WebCallback _theCallback){
		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("username", _name);
		parameters.put("password", _password);
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://localhost/webservice/register.php");
		//httpPost.setUrl(" ");
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		System.out.println("username: " +_name);
		 Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        		//String shttpResponse = httpResponse.getResultAsString();
		        		//System.out.println("The response is: "+shttpResponse);
		        		InputStream is = httpResponse.getResultAsStream();
		        		JsonReader jr = new JsonReader();
		        		JsonValue jsonVal = jr.parse(is);
		        		System.out.println("Json from create: "+jsonVal.toString());
		        		parseLoginData(jsonVal, _theCallback);
		        }
		 
		        public void failed(Throwable t) {
		        		System.out.println("DSFailed " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	public void login(String _name, String _password, final WebCallback _theCallback){
		HashMap<String, String> parameters = new HashMap<String,String>();
		parameters.put("username", _name);
		parameters.put("password", _password);
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://localhost/webservice/login.php");
		System.out.println("username: " +_name);
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        	InputStream is = httpResponse.getResultAsStream();
	        		JsonReader jr = new JsonReader();
	        		JsonValue jsonVal = jr.parse(is);
	        		System.out.println("Json from login: "+jsonVal.toString());
	        		parseLoginData(jsonVal, _theCallback);

		        }
		 
		        public void failed(Throwable t) {
		        		System.out.println("Failed " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	public void getAllUserNames(final WebCallback _theCallback, final Handler handler){
		//HashMap<String, String> parameters = new HashMap<String,String>();
		//parameters.put("username", _name);
		//parameters.put("password", _password);
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://localhost/webservice/usertest.php");
		//System.out.println("username: " +_name);
		//httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		        	InputStream is = httpResponse.getResultAsStream();
	        		JsonReader jr = new JsonReader();
	        		JsonValue jsonVal = jr.parse(is);
	        		System.out.println("Json from users: "+jsonVal.toString());
	        		parseUserData(jsonVal, _theCallback, handler);
	        		

		        }
		 
		        public void failed(Throwable t) {
		        		System.out.println("User test Failed " + t.getMessage());
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}
	
	
	private void parseLoginData(JsonValue theResult, WebCallback _theCallback){
		boolean result = theResult.getBoolean("success");
		String message = "";
		
		if (result){
			message = theResult.getString("message");
			//System.out.println(message);
		}
		
		_theCallback.setData(result, message);
	}
	
	private void parseUserData(JsonValue theResult, WebCallback _theCallback, Handler handler){
		//String[] usersArray = theResult.get("usernames").asStringArray();
		boolean result = theResult.getBoolean("success");
		String message = "";
		String users = "";
		
		if (result){
			System.out.println("Result is true");
			message = theResult.getString("message");
			JsonValue tmp = theResult.get("usernames");
			System.out.println(tmp.size);
			handler.sendUsersToGameScreen(tmp);
			for(int i = 0; i < tmp.size; i++){
				JsonValue jvtmp = tmp.get(i);
				System.out.println("ID: " + jvtmp.getString("id") +" Name: "+ jvtmp.getString("username"));
			}
			
		}
		
		_theCallback.setData(result, message);
		
	}
}
