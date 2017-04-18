package com.blackjacksim;

import java.util.ArrayList;
import java.util.Random;

public class Deck{
    private static final String[] suits = {"Spades", "Hearts", "Diamonds","Clubs"};

  private static enum cards {
        Two(2),
        Three(3),
        Four(4),
        Five(5),
        Six(6),
        Seven(7),
        Eight(8),
        Nine(9),
        Ten(10),
        Jack(10),
        Queen(10),
        King(10),
        Ace(11);

        private int value;

        cards(int value){
            this.value=value;
        }
        public int getValue(){
            return value;
        }
    }

    ArrayList<Card> deck= new ArrayList<>();

    public void constructDeck() {
        for(cards card : cards.values())
        {
            for(String s : suits) deck.add(new Card(s,card.name(),card.getValue()));
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
