package edu.oswego.franticphases.gamelogic;

import com.badlogic.gdx.Gdx;

public class Card {
   
   public final static int YELLOW = 0;   // Codes for the 4 suits
   public final static int BLUE = 1;
   public final static int RED = 2;
   public final static int GREEN = 3;
   
   public final static int WILD = 13;//   Cards 2 through 12 have their
   public final static int SKIP = 14;//   numerical values for their codes.
   
   
   private final int suit; 
   private final int value;
   
   
   /**
    * Creates a card with a specified suit and value.
    * @param theValue the value of the new card.  For a regular card (non-joker),
    * the value must be in the range 1 through 12.
    * You can use the constants Card.WILD, and Card.SKIP.  
    * @param theSuit the color of the new card.  This must be one of the values
    * Card.YELLOW, Card.GREEN, Card.BLUE, Card.RED.
    * @throws IllegalArgumentException if the parameter values are not in the
    * permissible ranges
    */
   public Card(int theValue, int theSuit) {
	   //Gdx.app.log("NEW CARD", "value:" + theValue +", suit: "+theSuit);
      if (theSuit != YELLOW && theSuit != GREEN && theSuit != BLUE && 
            theSuit != RED)
         throw new IllegalArgumentException("Illegal playing card suit");
      if ((theValue < 1 || theValue > 14))
         throw new IllegalArgumentException("Illegal playing card value");
      value = theValue;
      suit = theSuit;
   }
   
   public Card(String cardID) {
	  // Gdx.app.log("NEW CARD", "id:" + cardID);
	   
	  if(cardID.contains("green")){
		  suit = GREEN;
		  cardID = cardID.replace("green", "");
	  }else if(cardID.contains("yellow")){
		  suit = YELLOW;
		  cardID = cardID.replace("yellow", "");
	  }else if(cardID.contains("red")){
		  suit = RED;
		  cardID = cardID.replace("red", "");
	  }else if(cardID.contains("blue")){
		  suit = BLUE;
		  cardID = cardID.replace("blue", "");
	  }else{
		  suit = -1;
		  
	  }
	  
	  if(cardID.equals("W")){
		  value = 13;
	  }else if(cardID.equals("S")){
		  value = 14;
	  }else{
		  value = Integer.parseInt(cardID);
	  }
	  
//      if (theSuit != YELLOW && theSuit != GREEN && theSuit != BLUE && 
//            theSuit != RED)
//         throw new IllegalArgumentException("Illegal playing card suit");
//      if ((theValue < 1 || theValue > 14))
//         throw new IllegalArgumentException("Illegal playing card value");
      
      
   }

   /**
    * Returns the suit of this card.
    * @returns the suit
    */
   public int getSuit() {
      return suit;
   }
   
   /**
    * Returns the value of this card.
    * @return the value, which is one of the numbers 1 through 14
    */
   public int getValue() {
      return value;
   }
   
   /**
    * Returns a String representation of the card's suit.
    */
   public String getSuitAsString() {
      switch ( suit ) {
      case YELLOW:   return "yellow";
      case BLUE:   return "blue";
      case GREEN: return "green";
      default:    return "red";
      }
   }
   
   /**
    * Returns a String representation of the card's value.
    * @return for a regular card, one of the strings "Ace", "2",
    * "3", ..., "10", "Jack", "Queen", or "King".  For a Joker, the 
    * string is always numerical.
    */
   public String getValueAsString() {
      
         switch ( value ) {
         case 1:   return "1";
         case 2:   return "2";
         case 3:   return "3";
         case 4:   return "4";
         case 5:   return "5";
         case 6:   return "6";
         case 7:   return "7";
         case 8:   return "8";
         case 9:   return "9";
         case 10:  return "10";
         case 11:  return "11";
         case 12:  return "12";
         case 13:  return "W";
         default:  return "S";
         
      }
   }
   
   /**
    * Returns a string representation of this card, including both
    * its suit and its value 
    */
   public String toString() {
         return getValueAsString() + " of " + getSuitAsString();
   }
   
   public String getCardID(){
	   return getSuitAsString() + getValueAsString();
   }
   

}