package edu.oswego.franticphases.gamelogic;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;

import edu.oswego.franticphases.datasending.GameSession;

public class Hand {

	private String handID;
	private ArrayList<Card> cards;   // The cards in the hand.
	private ArrayList<String> cardsAsStrings;
	boolean sortedByColor = false;

    public Hand() {
        cards = new ArrayList<Card>();
        
    }
    
	public Hand(JsonValue json){
		cardsAsStrings = new ArrayList<String>();
		cards = new ArrayList<Card>();
		for(int i = 0; i < json.size; i++){
			JsonValue tmp = json.get(i);
			handID = tmp.getString("handID");
			
			String cardID = tmp.getString("card01");
			cards.add(new Card(cardID));
			cardsAsStrings.add(cardID);
			cardID = tmp.getString("card02");
			cards.add(new Card(cardID));
			cardsAsStrings.add(cardID);
			cardID = tmp.getString("card03");
			cards.add(new Card(cardID));
			cardsAsStrings.add(cardID);
			cardID = tmp.getString("card04");
			cards.add(new Card(cardID));
			cardsAsStrings.add(cardID);
			cardID = tmp.getString("card05");
			cards.add(new Card(cardID));
			cardsAsStrings.add(cardID);
			cardID = tmp.getString("card06");
			cards.add(new Card(cardID));
			cardsAsStrings.add(cardID);
			cardID = tmp.getString("card07");
			cards.add(new Card(cardID));
			cardsAsStrings.add(cardID);
			cardID = tmp.getString("card08");
			cards.add(new Card(cardID));
			cardsAsStrings.add(cardID);
			cardID = tmp.getString("card09");
			cards.add(new Card(cardID));
			cardsAsStrings.add(cardID);
			cardID = tmp.getString("card10");
			cards.add(new Card(cardID));
			cardsAsStrings.add(cardID);
			
			Gdx.app.log("Hand", "Loaded handID: " + handID);
			
			
		}
	}

    /**
     * Remove all cards from the hand, leaving it empty.
     */
    public void clear() {
        cards.clear();
    }

    /**
     * Add a card to the hand.  It is added at the end of the current hand.
     * @param c the non-null card to be added.
     * @throws NullPointerException if the parameter c is null.
     */
    public void addCard(Card c) {
        if (c == null)
            throw new NullPointerException("Can't add a null card to a hand.");
        cards.add(c);
    }

    /**
     * Remove a card from the hand, if present.
     * @param c the card to be removed.  If c is null or if the card is not in 
     * the hand, then nothing is done.
     */
    public void removeCard(Card c) {
        cards.remove(c);
    }

    /**
     * Remove the card in a specified position from the hand.
     * @param position the position of the card that is to be removed, where
     * positions are starting from zero.
     * @throws IllegalArgumentException if the position does not exist in
     * the hand, that is if the position is less than 0 or greater than
     * or equal to the number of cards in the hand.
     */
    public void removeCard(int position) {
        if (position < 0 || position >= cards.size())
            throw new IllegalArgumentException("Position does not exist in hand: "
                    + position);
        cards.remove(position);
    }

    /**
     * Returns the number of cards in the hand.
     */
    public int getCardCount() {
        return cards.size();
    }

    /**
     * Gets the card in a specified position in the hand.  (Note that this card
     * is not removed from the hand!)
     * @param position the position of the card that is to be returned
     * @throws IllegalArgumentException if position does not exist in the hand
     */
    public Card getCard(int position) {
        if (position < 0 || position >= cards.size())
            throw new IllegalArgumentException("Position does not exist in hand: "
                    + position);
        return cards.get(position);
    }

    /**
     * Sorts the cards in the hand so that cards of the same suit are
     * grouped together, and within a suit the cards are sorted by value.
     * Note that aces are considered to have the lowest value, 1.
     */
    public void sortBySuit() {
        ArrayList<Card> newHand = new ArrayList<Card>();
        while (cards.size() > 0) {
            int pos = 0;  // Position of minimal card.
            Card c = cards.get(0);  // Minimal card.
            for (int i = 1; i < cards.size(); i++) {
                Card c1 = cards.get(i);
                if ( c1.getSuit() < c.getSuit() ||
                        (c1.getSuit() == c.getSuit() && c1.getValue() < c.getValue()) ) {
                    pos = i;
                    c = c1;
                }
            }
            cards.remove(pos);
            newHand.add(c);
        }
        cards = newHand;
        sortedByColor = true;
    }

    /**
     * Sorts the cards in the hand so that cards of the same value are
     * grouped together.  Cards with the same value are sorted by suit.
     * Note that aces are considered to have the lowest value, 1.
     */
    public void sortByValue() {
        ArrayList<Card> newHand = new ArrayList<Card>();
        while (cards.size() > 0) {
            int pos = 0;  // Position of minimal card.
            Card c = cards.get(0);  // Minimal card.
            for (int i = 1; i < cards.size(); i++) {
                Card c1 = cards.get(i);
                if ( c1.getValue() < c.getValue() ||
                        (c1.getValue() == c.getValue() && c1.getSuit() < c.getSuit()) ) {
                    pos = i;
                    c = c1;
                }
            }
            cards.remove(pos);
            newHand.add(c);
        }
        cards = newHand;
        sortedByColor = false;
    }
    
    public boolean isSortedByColor(){
    	return sortedByColor;
    }
	
	
	public String getHandID(){
		return handID;
	}

	public ArrayList<Card> getCards(){
		return cards;
	}
	
	public ArrayList<String> getCardsString(){
		return cardsAsStrings;
	}
	
}
