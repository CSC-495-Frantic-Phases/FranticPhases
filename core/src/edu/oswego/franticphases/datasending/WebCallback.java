package edu.oswego.franticphases.datasending;

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
	
	public void setData(boolean _result, String _message){
		if(debug){
			System.out.println("Data set in CB");
		}
		isRecieved = true;
		success = _result;
		message = _message;
	}

}
