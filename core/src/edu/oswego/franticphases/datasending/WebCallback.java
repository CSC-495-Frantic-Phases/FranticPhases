package edu.oswego.franticphases.datasending;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;

import edu.oswego.franticphases.settings.Settings;

public class WebCallback {
	
	private boolean isRecieved = false;
	private boolean debug = true;
	private boolean success;
	
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
	

	
	private void setUserID(String id){
		Settings.setUserID(id);
	}
	
	public void setData(boolean _result, String _message, String id){
		if(debug){
			Gdx.app.log("Callback", _message);
		}
		isRecieved = true;
		success = _result;
		message = _message;
		this.setUserID(id);
	}
	
	public void setData(boolean _result, String _message){
		if(debug){
			Gdx.app.log("Callback", _message);
		}
		isRecieved = true;
		success = _result;
		message = _message;
	}
	


}
