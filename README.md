# BlackJack
BlackJack - console app imitating a casino card game "BlackJack" or "21". 
The purpose of the game is to get higher score than dealer without exceeding 21. Player competes against the dealer.

Functionality:

The deck contains 52 cards, from 2 to an Ace (without Jokers).
Application uses one full deck for each game. 
Before getting cards, player must bet on the game.
After bet, player gets 2 cards from the deck and dealer gets one card. Cards are being picked randomly from the deck.

Getting 21 points in first 2 cards ( f.e. Ace - King) results in Blackjack. Blackjacks work as following:
Both player and dealer got BlackJack - Push. Bet returned.
Only player got Blackjack - Player wins 1.5 times his bet amount.
Only dealer got Blackjack - Dealer wins, even if player had 21 score.


Depending on the cards received, player can:

Hit - draw additional card

Stand - end turn

Split - split two cards into two hands, each with the same bet amount. Player can split if his first 2 cards have the same values.
Hitting split aces is allowed. BlackJacks from splits are counted as normal BlackJacks.

Double down - double the bet amount, draw one card and end turn.

Player/Dealer busts when exceeding 21 points.



Each card has it's own value:

Card    Value
2       2
3       3
4       4
5       5
6       6
7       7
8       8
9       9
10      10
Jack    10
Queen   10
King    10
Ace     11/1

Ace's value depends on score.




