package com.blackjacksim;


public class Card{
    private String suit,rank;
    private int weight;

    public Card(String suit, String rank,int weight) {
        this.suit = suit;
        this.rank = rank;
        this.weight = weight;
    }

    public String getRank() {
        return rank;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return rank+ " of " + suit;
    }
}
