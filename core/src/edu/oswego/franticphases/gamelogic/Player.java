package edu.oswego.franticphases.gamelogic;

import edu.oswego.franticphases.settings.Settings;

public class Player {

	String userName;
	String score;
	String phase;
	String playerNum;
	String id;
	
	
	public Player(String name, String s, String p, String num){
		userName = name;
		score = s;
		phase = p;
		playerNum = num;
	}
	public Player(String name, String i){
		userName = name;
		id = i;
	}
	
	public String getID(){
		return id;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public String getScore(){
		return score;
	}
	
	public String getPhase(){
		return phase;
	}
	
	public String getPlayerNum(){
		return playerNum;
	}
	
	
}
