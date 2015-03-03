package edu.oswego.franticphases.datasending;

import com.badlogic.gdx.utils.JsonValue;

import edu.oswego.franticphases.settings.Settings;

public class WebCallback {
	
	private boolean isRecieved = false;
	private boolean debug = false;
	private boolean success;
	private JsonValue json;
	
	private String message;
	
	public WebCallback(){
		
	}
	
	public boolean getRecieved(){
		return isRecieved;
	}
	
	public boolean getResult(){
		return success;
	}
	
	public String getMessage(){
		return message;
	}
	
	public JsonValue getJson(){
		return json;
	}
	
	private void setUserID(String id){
		Settings.setUserID(id);
	}
	
	public void setData(boolean _result, String _message, String id){
		if(debug){
			System.out.println("Data set in CB");
		}
		isRecieved = true;
		success = _result;
		message = _message;
		this.setUserID(id);
	}
	
	public void setData(boolean _result, String _message){
		if(debug){
			System.out.println("Data set in CB");
		}
		isRecieved = true;
		success = _result;
		message = _message;
	}
	
	public void setData(boolean _result, String _message, JsonValue js){
		if(debug){
			System.out.println("Data set in CB");
		}
		isRecieved = true;
		success = _result;
		message = _message;
		json = js;
	}

}
