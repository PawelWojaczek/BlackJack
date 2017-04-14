package com.blackjacksim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Deck {
    private String[] suits = {"Spades", "Hearts", "Diamonds","Clubs"};
    private String[] ranks = {"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};
    private int[] weights = {2,3,4,5,6,7,8,9,10,10,10,10,11};
    String suit,rank;
    int weight;
    public Deck(){}
    public Deck(String suit, String rank,int weight) {
        this.suit = suit;
        this.rank = rank;
        this.weight = weight;
    }

    ArrayList<Deck> deck= new ArrayList<>();

    public void constructDeck() {
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < ranks.length; j++) {
                deck.add(new Deck(suits[i], ranks[j], weights[j]));
            }
        }
    }

    public Deck getCard(){
        Random generator = new Random();
        int num = generator.nextInt(deck.size());
        Deck card = deck.get(num);
        deck.remove(num);
        return card;
    }

    @Override
    public String toString() {
        return rank+ " of " + suit;
    }
}
