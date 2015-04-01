package edu.oswego.franticphases;

public class User {
	private String username;
	private String userID;
	
	public User(String user, String id){
		username = user;
		userID = id;
	}
	
	public String getUserName(){
		return username;
	}
	
	public String getUserID(){
		return userID;
	}
}
