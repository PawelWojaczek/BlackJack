package com.blackjacksim;

import java.util.ArrayList;
import java.util.Random;

public class Deck{
    private String[] suits = {"Spades", "Hearts", "Diamonds","Clubs"};
    private String[] ranks = {"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};
    private int[] weights = {2,3,4,5,6,7,8,9,10,10,10,10,11};

  /*  private enum cards {
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(10),
        QUEEN(10),
        KING(10),
        ACE(11);

        private int value;

        cards(int value){
            this.value=value;
        }
        public int getValue(){
            return value;
        }
    }-*/

    ArrayList<Card> deck= new ArrayList<>();

    public void constructDeck() {
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < ranks.length; j++) {
                deck.add(new Card(suits[i], ranks[j], weights[j]));
            }
        }
    }

    public Card getRandomCard(){
        Random generator = new Random();
        int num = generator.nextInt(deck.size());
        Card card = deck.get(num);
        deck.remove(num);
        return card;
    }

}
