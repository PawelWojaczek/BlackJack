package com.blackjacksim;

import java.security.InvalidParameterException;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private int[] weights= {2,3,4,5,6,7,8,9,10,10,10,11};
    boolean phaseEnd = false;
    public Game() {}

    private int getCard( Player player){
        Random generator = new Random();
        int i=generator.nextInt(9)+2;
        System.out.println("Got a " + i);
        player.addPoints(i);
        return i;
    }

    public void playGame(Player player){
        System.out.println("Welcome to BlackJack game. You have 500 coins that you can bet on blackjack games.\n If you win, you get your bet doubled.\n If you get BlackJack, you win 1,5 the amount you bet.");
        bet(player);
        getCard(player);
        getCard(player);
        while( !defWin(player) && !phaseEnd){
            playPhase(player);
            System.out.println("Points:"+ player.getPoints());
        }

    }
    private void playPhase(Player player){
        if(player.getPoints()<=21)
        {
            getOptions(player);

        }
        else phaseEnd=true;
}


    private void getOptions(Player player){
        System.out.println("It is your turn. You have "+ player.getPoints()+" points.\n Enter 'H' to Hit, or 'S' to stand");
        Scanner scan=new Scanner(System.in);
        char c=scan.next().charAt(0);
        switch(c)
        {
            case 'H':
                getCard(player);
                break;
            case 'S':
                phaseEnd=true;
                break;
            default:
                throw new InvalidParameterException("Wrong character passed. ");
        }
    }

    private boolean defWin(Player player){
        if(player.getPoints()==21){
            System.out.println("You got blackjack in first 2 cards! You win " + player.getBetAmount()*1.5+ " coins.");
            player.addCoins(player.getBetAmount());
            return true;
        }
        return false;
    }

    private void bet(Player player){
        System.out.println("How much coins do you want to bet on this game?");
        Scanner scan= new Scanner(System.in);
        int bet = scan.nextInt();
        if(player.getPoints()<=bet) player.setBetAmount(bet);
        else throw new InvalidParameterException("Invalid bet amount.");
    }
}

