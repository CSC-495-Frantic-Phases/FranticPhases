package edu.oswego.franticphases.gamelogic;

public class Turn {
	
	//this is where we can check the rules about phases
	//currently just checks about picking up a card

	private boolean canPickup = true;
	private boolean canDiscard = false;

	
	
	public Turn(){
		
	}
	
	public boolean canPickUp(){
		return canPickup;
	}
	
	public void pickedUp(){
		canPickup = false;
		canDiscard = true;
	}
	public boolean canDiscard(){
		return canDiscard;
	}
	
	

	
}
