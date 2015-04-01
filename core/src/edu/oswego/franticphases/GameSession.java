package edu.oswego.franticphases;


public class GameSession {
	private String gameID;
	private String player1;
	private String player2;
	private String player3;
	private String player4;
	
	
	public GameSession(String game){
		gameID = game; 
	}
	
	public void addPlayer(int num, String name){
		if(num==0){
			player1 = name;
		}else if(num ==1){
			player2 = name;
		}else if(num==2){
			player3 = name;
		}else if(num==3){
			player4 = name;
		}
	}
	
	public String getP1(){
		return player1;
	}
	public String getP2(){
		return player2;
	}
	public String getP3(){
		return player3;
	}
	public String getP4(){
		return player4;
	}
	public String getGameID(){
		return gameID;
	}
	
}
