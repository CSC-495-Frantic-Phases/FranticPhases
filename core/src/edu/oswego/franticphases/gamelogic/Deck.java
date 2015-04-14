package edu.oswego.franticphases.gamelogic;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

public class Deck {
	private String deckID;
    private ArrayList<Card> deck;
    private int numCards;
    private int cardsUsed;


    /**
     * The deck contains 108 cards initially the cards
     * are in a sorted order.  The shuffle() method can be called to
     * randomize the order.
     */
    public Deck() {
       deck = new ArrayList<Card>();
       
        numCards = 0; // How many cards have been created so far.
        for ( int suit = 0; suit < 4; suit++ ) {
        	
            for ( int value = 1; value <= 14; value++ ) {
                deck.add(new Card(value,suit));
                numCards++;
                if(value != 14){
                	deck.add(new Card(value,suit));
                	numCards++;
                }
            }
        }
        cardsUsed = 0;
    }
    
    public Deck(String id){
    	deckID= id;
    }
    
    public String getID(){
    	return deckID;
    }
 
    public void shuffle() {
        for ( int i = numCards-1; i > 0; i-- ) {
            int rand = (int)(Math.random()*(i+1));
            Card temp = deck.get(i);
            deck.set(i, deck.get(rand));
            deck.set(rand,temp);
        }
        cardsUsed = 0;
    }

    public int cardsLeft() {
        return deck.size() - cardsUsed;
    }

    public Card dealCard() {
        if (cardsUsed == deck.size())
            throw new IllegalStateException("No cards are left in the deck.");
        cardsUsed++;
        return deck.get(cardsUsed - 1);
    }
    
    public String getCards(){
    	StringBuilder bString = new StringBuilder();
    	
    	for(int i = 0; i < deck.size();i++){
    		bString.append(deck.get(i).getSuitAsString()).append(deck.get(i).getValueAsString());
    		if(i<deck.size()-1){
    			bString.append(", ");
    		}
    	}
    	return bString.toString();
    }

}
