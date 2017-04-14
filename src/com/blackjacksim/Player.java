package com.blackjacksim;

public class Player {
    private int points;
    private double balance;
    private int betAmount;
    private String name;
    private String cards;
    private boolean busted = false;


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
        this.cards="";
    }

    public void addCard(String card){
        if(cards=="")this.cards+=card;
        else this.cards+=","+card;
    }

    public String getName() {
        return name;
    }

    public String getHand(){
        return this.getName()+"'s hand: "+ cards;
    }

    public void setBust(boolean value){
        this.busted=value;
    }
    public boolean isBusted(){
        return this.busted;
    }
}
