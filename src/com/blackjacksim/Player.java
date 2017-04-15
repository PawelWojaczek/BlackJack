package com.blackjacksim;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {
    private int points;
    private double balance;
    private int betAmount;
    private String name;
    private String hand;
    private boolean busted = false;
    private ArrayList<Deck> cards =new ArrayList<>();


    public Player(String name) {
        this.name=name;
        this.points = 0;
        this.balance=500;
        this.betAmount=0;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int amount){
        points+=amount;
    }

    public void addCoins(double amount){
        balance+=amount;
    }

    public void removeCoins(int amount){
        balance-=amount;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public double getBalance(){
        return balance;
    }

    public void clearPoints(){
        this.points=0;
    }

    public void clearCards(){
        cards.clear();
    }

    public void addCard(Deck card){
            cards.add(card);
            hand+=card.rank+"of"+card.suit+",";
    }

    public String getName() {
        return name;
    }

    public void setBust(boolean value){
        this.busted=value;
    }
    public boolean isBusted(){
        return this.busted;
    }

    public String getCards() {
        return this.getName()+"'s hand: "+cards;
    }
}
