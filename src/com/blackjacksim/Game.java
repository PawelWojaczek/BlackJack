package com.blackjacksim;

import java.security.InvalidParameterException;
import java.util.Scanner;

public class Game {

    boolean phaseEnd = false;
    private Deck cards = new Deck();
    private Player player = new Player("Player");
    private Player dealer = new Player("Dealer");

    private void appendCard(Card card, Player player){
        player.addCard(card);
        System.out.println(player.getName() + " got " + card);
    }
    public void showBalance(){
        System.out.println("Your current balance is: " + player.getBalance());
    }

    public void doubleDown(){
        System.out.println("Double down. Doubling bet and picking 1 card.");
        player.setBetAmount(2*player.getBetAmount());
        appendCard(cards.getCard(), player);
        phaseEnd=true;
    }
    public void playGame(){
        System.out.println("Welcome to BlackJack game. You have 500 coins that you can bet on blackjack games.\n If you win, you get your bet doubled.\nBlackJack paying 3 to 2.\nDealer stands at 17 or higher.");
        while(player.getBalance()>=1) {
            clearBoard();
            cards.constructDeck();
            showBalance();
            bet(player);
            appendCard(cards.getCard(), player);
            appendCard(cards.getCard(), player);
            appendCard(cards.getCard(), dealer);
                while (!phaseEnd) {
                    System.out.println(player.getCards());
                    playPhase();
                }
                if(!player.isBusted()) {
                    System.out.println("Dealer's turn.");
                    playDealerPhase();
                }
                if(!defWin()) checkResult();
        }
        System.out.println("Not enough coins to play the game.");

    }

    private void playDealerPhase(){
        while(dealer.getPoints()<17) {
            appendCard(cards.getCard(),dealer);
            aceCheck(dealer);
            checkBust(dealer);
        }
        System.out.println(dealer.getCards());
    }
    private void checkBust(Player player){
        if(player.getPoints()>21)
        {
            System.out.println(player.getName()+" busted.");
            player.setBust(true);
        }
    }

    private void playPhase(){
        if(player.getPoints()<21)
        {
            getOptions();
            aceCheck(player);
            checkBust(player);
        }
        else {
            phaseEnd=true;
        }
}

    private void getOptions(){
        System.out.println("It is your turn.\nYou have "+ player.getPoints()+" points.\nEnter 'H' to Hit, 'S' to stand or 'D' to Double down");
        // ^Change the message to adjust what is possible in the turn.
        Scanner scan=new Scanner(System.in);
        char c=scan.next().charAt(0);
        switch(c)
        {
            case 'H':case 'h':
                appendCard(cards.getCard(),player);
                break;
            case 'S':case 's':
                phaseEnd=true;
                break;
            case 'D':case 'd':
                if(player.getBalance()>=2*player.getBetAmount()){
                    doubleDown();
                    break;
                }
                else{
                    System.out.println("Not enough coins to double down.\n");
                    getOptions();
                }
            case 'X':case 'x':
                //split();   <- Add split function
                break;
            default:
                throw new InvalidParameterException("Wrong character passed. ");
        }
    }

    private boolean defWin(){
        if(player.getHand().size()==2 && player.getPoints()==21)
        {
            if(dealer.getHand().size()==2 && dealer.getPoints()==21){
                System.out.println("Both player and dealer got BlackJacks");
                outcome(2);
            }
            else{
                System.out.println("You got blackjack in first 2 cards! You won " + player.getBetAmount() * 1.5 + " coins.");
                player.addCoins(1.5*player.getBetAmount());
            }
            return true;
        }
        else if(dealer.getHand().size()==2 && dealer.getPoints()==21){
            System.out.println("Dealer got BlackJack.");
            outcome(0);
            return true;
        }
        return false;
    }

    public void aceCheck(Player player){
        if(player.getPoints()>21) {
            player.clearPoints();
            for(Card card: player.getHand()) player.addPoints(card.weight);
            for (Card card : player.getHand()) {
                if (card.rank.equals("Ace") && player.getPoints()>21) player.removePoints(10);
            }
        }
    }

    private void bet(Player player)    {
        System.out.println("How much coins do you want to bet on this game?");
        Scanner scan= new Scanner(System.in);
        int bet = scan.nextInt();
        if(player.getBalance()>=bet) player.setBetAmount(bet);
        else throw new InvalidParameterException("Invalid bet amount.");
    }

    private void clearBoard(){
        player.clearHand();
        dealer.clearHand();
        phaseEnd=false;
    }

    public void checkResult(){
        System.out.println(player.getName()+"'s points: " + player.getPoints()+". "+dealer.getName()+" points: "+dealer.getPoints());
        if(!player.isBusted())
        {
            if (player.getPoints() > dealer.getPoints() || dealer.isBusted()) outcome(1);
            else if (player.getPoints() < dealer.getPoints()) outcome(0);
            else outcome(2);
        }
        else outcome(0);
    }

    public void outcome(int value)
    {
        switch(value){
            case 0:
                player.removeCoins(player.getBetAmount());
                System.out.println("YOU LOST. Amount of " + player.getBetAmount() + " coins has been deducted from your account.");
                break;
            case 1:
                player.addCoins(player.getBetAmount());
                System.out.println("YOU WON. Amount of " + player.getBetAmount() + " coins has been added to your account.");
                break;
            case 2:
                System.out.println("Push. Bet returned.");
        }
    }
}