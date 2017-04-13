package com.blackjacksim;

public class Player {
    private String name;
    private int points;
    private String cards;
    private int balance;
    private int betAmount;


    public Player(String name) {
        this.name = name;
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

    public void addCoins(int amount){
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
}
