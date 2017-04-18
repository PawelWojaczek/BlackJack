package com.blackjacksim;

import java.security.InvalidParameterException;
import java.util.Scanner;

public class Game {
    private Deck cards = new Deck();
    private Player player = new Player("Player");
    private Player dealer = new Player("Dealer");

    private static final int dealerStandsAt = 17;
    private static final int bustAt = 21;

    private boolean splitPossible= false;
    private boolean doubleDownPossible=false;
    private boolean splitCalled=false;

    private void appendCard(Card card, Player player){
        player.addCard(card);
        System.out.println(player.getName() + " got " + card);
    }

    private void showBalance(){
        System.out.println("Your current balance is: " + player.getBalance());
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
        while(dealer.getPoints()<dealerStandsAt) {
            appendCard(cards.getRandomCard(),dealer);
            aceCheck(dealer);
            checkBust(dealer);
        }
        System.out.println(dealer.getCards());
    }

    private void checkBust(Player player){
        if(playerBust(player))
        {
            System.out.println(player.getName()+" busted.");
            player.setBust(true);
        }
    }

    private void playPhase(Player player){
        while(!player.isPhaseEnd()) {
            System.out.println("Talia: "+cards.deck.size());
            System.out.println(player.getCards());
            if (!playerBust(player) && player.getPoints()!=21) {
                chooseOptions(player);
                aceCheck(player);
                checkBust(player);
            } else {
                player.setPhaseEnd(true);
            }
        }
}

    private void getPossibilities(Player player){
            doubleDownPossible =player.getHand().size() == 2 && player.getBetAmount()<=2*player.getBalance() || splitCalled && player.getHand().size()==1;
            splitPossible=!splitCalled && player.getHand().get(0).getRank().equals(player.getHand().get(1).getRank());
    }

    private void showOptions(Player player){
        getPossibilities(player);
        System.out.println("It is your turn.\nYou have "+ player.getPoints()+" points.");
        System.out.println("'H' to hit.");
        System.out.println("'S' to stand.");
        if(doubleDownPossible) System.out.println("'D' to double down.");
        if(splitPossible) System.out.println("'X' to split.");
    }

    private void chooseOptions(Player player){
        showOptions(player);
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
                if(doubleDownPossible) doubleDown(player);
                else throw new InvalidParameterException("Double down is not possible");
                break;
            case 'X':case 'x':
                if(splitPossible) {
                    splitCalled = true;
                    split();
                }
                else throw new InvalidParameterException("Split is not possible.");
                break;
            default:
                throw new InvalidParameterException("Wrong character passed. ");
        }
    }

    private void doubleDown(Player player){
        System.out.println("Double down. Doubling bet and picking 1 card.");
        player.setBetAmount(2*player.getBetAmount());
        appendCard(cards.getRandomCard(), player);
        player.setPhaseEnd(true);
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

    private boolean defWin(Player player){
        if(isBlackJack(player))
        {
            if(isBlackJack(dealer)){
                System.out.println("Both player and dealer got BlackJacks");
                outcome(2,player);
            }
            else{
                System.out.println("You got blackjack in first 2 cards! You won " + player.getBetAmount() * 1.5 + " coins.");
                player.addCoins(1.5*player.getBetAmount());
            }
            return true;
        }
        else if(isBlackJack(dealer)){
            System.out.println("Dealer got BlackJack.");
            outcome(0,player);
            return true;
        }
        return false;
    }

    private boolean isBlackJack(Player player){
        return player.getHand().size()==2 && player.getPoints()==21;
    }

    private boolean playerBust(Player player){
        return player.getPoints()>bustAt;
    }

    private void aceCheck(Player player){
        if(playerBust(player)) {
            player.clearPoints();
            for(Card card: player.getHand()) player.addPoints(card.getWeight());
            for(Card card : player.getHand()) {
                if (card.getRank().equals("Ace") && playerBust(player)) player.removePoints(10);
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
        cards.deck.clear();
        player.setPhaseEnd(false);
        splitCalled=false;
    }

    private boolean playerWins(Player player){
        return player.getPoints()>dealer.getPoints();
    }

    private boolean dealerWins(Player player){
        return player.getPoints()<dealer.getPoints();
    }

    private void checkResult(Player player){
        System.out.println(player.getName()+"'s points: " + player.getPoints()+". "+dealer.getName()+" points: "+dealer.getPoints());
        if(!player.isBusted())
        {
            if (playerWins(player) || dealer.isBusted()) outcome(1,player);
            else if (dealerWins(player)) outcome(0,player);
            else outcome(2,player);
        }
        else outcome(0,player);
    }

    private void outcome(int value,Player player)
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