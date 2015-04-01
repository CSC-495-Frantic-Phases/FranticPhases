package edu.oswego.franticphases.gamelogic;

import com.badlogic.gdx.utils.JsonValue;

import edu.oswego.franticphases.GameSession;

public class Hand {

	private String handID;
	private String card1, card2, card3, card4, card5,
			card6, card7, card8, card9, card10;
	private String card11;
	
	
	public Hand(JsonValue json){
		for(int i = 0; i < json.size; i++){
			JsonValue tmp = json.get(i);
			handID = tmp.getString("handID");
			card1 = tmp.getString("card01");
			card2 = tmp.getString("card02");
			card3 = tmp.getString("card03");
			card4 = tmp.getString("card04");
			card5 = tmp.getString("card05");
			card6 = tmp.getString("card06");
			card7 = tmp.getString("card07");
			card8 = tmp.getString("card08");
			card9 = tmp.getString("card09");
			card10 = tmp.getString("card10");
			
		}
	}
	
	public void pickedUpCard(String card){
		card11 = card;
	}
	
	
	public String getHandID(){
		return handID;
	}

	public String getCard1(){
		return card1;
	}
	public void setCard1(String s){
		card1 = s;
	}
	public String getCard2(){
		return card2;
	}
	public String getCard3(){
		return card3;
	}
	public String getCard4(){
		return card4;
	}
	public String getCard5(){
		return card5;
	}
	public String getCard6(){
		return card6;
	}
	public String getCard7(){
		return card7;
	}
	public String getCard8(){
		return card8;
	}
	public String getCard9(){
		return card9;
	}
	public String getCard10(){
		return card10;
	}

	
	
	
}
