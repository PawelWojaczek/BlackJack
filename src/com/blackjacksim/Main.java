package com.blackjacksim;


public class Main {

    public static void main(String[] args) {
        /*Player player = new Player("Gracz1");
        Game newGame = new Game();
        newGame.playGame(player);*/
        Deck deck = new Deck();
        deck.constructDeck();
        Deck abc;
        int i=0;
        while(i<52){
            abc=deck.GetCard();
            System.out.println(abc.rank + " of " + abc.suit + " with a weight of " + abc.weight);
            i++;
        }
    }
}
