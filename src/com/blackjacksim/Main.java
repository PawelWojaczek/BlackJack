package com.blackjacksim;

public class Main {

    public static void main(String[] args) {
        Player player = new Player("Gracz1");
        Game newGame = new Game();
        newGame.playGame(player);
    }
}
