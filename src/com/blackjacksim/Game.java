package com.blackjacksim;

import java.security.InvalidParameterException;
import java.util.Scanner;

public class Game {
    private Deck cards = new Deck();
    private Player player = new Player("Player");
    private Player dealer = new Player("Dealer");
    private boolean splitPossible= false;
    private boolean doubleDownPossible=false;
    private boolean splitCalled=false;

    private void appendCard(Card card, Player player){
        player.addCard(card);
        System.out.println(player.getName() + " got " + card);
    }
    public void showBalance(){
        System.out.println("Your current balance is: " + player.getBalance());
    }

    public void doubleDown(Player player){
        System.out.println("Double down. Doubling bet and picking 1 card.");
        player.setBetAmount(2*player.getBetAmount());
        appendCard(cards.getRandomCard(), player);
        player.setPhaseEnd(true);
    }


    private void getStartingCards(){
        if(player.getHand().size()==0) {
            appendCard(cards.getRandomCard(), player);
            appendCard(cards.getRandomCard(), player);
            appendCard(cards.getRandomCard(), dealer);
        }
    }

    public void initializeGame(){
        System.out.println("Welcome to BlackJack game. You have 500 coins that you can bet on blackjack games.\n If you win, you get your bet doubled.\nBlackJack paying 3 to 2.\nDealer stands at 17 or higher.");
        while(player.getBalance()>=1) {
            clearBoard(player);
            cards.constructDeck();
            showBalance();
            bet(player);
            getStartingCards();
            playGame(player);
        }
        System.out.println("Not enough coins to play the game.");
    }
    private void playGame(Player player){
            getStartingCards();
            playPhase(player);
                if(!splitCalled) {
                    if (!player.isBusted()) {
                        System.out.println("Dealer's turn.");
                        playDealerPhase();
                    }
                    if (!defWin(player)) checkResult(player);
                }
    }

    private void playDealerPhase(){
        while(dealer.getPoints()<17) {
            appendCard(cards.getRandomCard(),dealer);
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

    private void playPhase(Player player){
        while(!player.isPhaseEnd()) {
            System.out.println(player.getCards());
            if (player.getPoints() < 21) {
                getOptions(player);
                aceCheck(player);
                checkBust(player);
            } else {
                player.setPhaseEnd(true);
            }
        }
}

    private void getPossibilities(Player player){
            if (player.getHand().size() == 2) doubleDownPossible = true;
            else doubleDownPossible = false;
            if (!splitCalled && player.getHand().get(0).getRank().equals(player.getHand().get(1).getRank())) splitPossible = true;
            else splitPossible = false;
    }

    private void getOptions(Player player){
        getPossibilities(player);
        System.out.println("It is your turn.\nYou have "+ player.getPoints()+" points.");
        System.out.println("'H' to hit.");
        System.out.println("'S' to stand.");
        if(doubleDownPossible) System.out.println("'D' to double down.");
        if(splitPossible) System.out.println("'X' to split.");

        Scanner scan=new Scanner(System.in);
        char c=scan.next().charAt(0);
        switch(c)
        {
            case 'H':case 'h':
                appendCard(cards.getRandomCard(),player);
                break;
            case 'S':case 's':
                player.setPhaseEnd(true);
                break;
            case 'D':case 'd':
                if(player.getBalance()>=2*player.getBetAmount()){
                    doubleDown(player);
                    break;
                }
                else{
                    System.out.println("Not enough coins to double down.\n");
                    getOptions(player);
                }
            case 'X':case 'x':
                splitCalled=true;
                split();
                break;
            default:
                throw new InvalidParameterException("Wrong character passed. ");
        }
    }

    private boolean defWin(Player player){
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
            for(Card card: player.getHand()) player.addPoints(card.getWeight());
            for (Card card : player.getHand()) {
                if (card.getRank().equals("Ace") && player.getPoints()>21) player.removePoints(10);
            }
        }
    }

    private void bet(Player player)    {
        if(player.getBetAmount()==0) {
            System.out.println("How much coins do you want to bet on this game?");
            Scanner scan = new Scanner(System.in);
            int bet = scan.nextInt();
            if (player.getBalance() >= bet) player.setBetAmount(bet);
            else throw new InvalidParameterException("Invalid bet amount.");
        }
    }

    private void clearBoard(Player player){
        player.clearHand();
        dealer.clearHand();
        player.setPhaseEnd(false);
        splitCalled=false;
    }

    private void split(){
        Player player1 = new Player(player.getName());
        player1.setBetAmount(player.getBetAmount());
        player1.addCard(player.getHand().get(0));
        playPhase(player1);
        player.removePoints(player.getHand().get(0).getWeight());
        player.getHand().remove(0);
        playPhase(player);
        playDealerPhase();
        checkResult(player1);
        checkResult(player);
    }

    public void checkResult(Player player){
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