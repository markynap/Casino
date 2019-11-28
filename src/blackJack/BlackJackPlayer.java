package blackJack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

import gameSetUps.*;
import gameSetUps.Game.STATE;

public class BlackJackPlayer {

	public Game game;
	public ImageManager IM;
	public Stack<Card> deck; // 52 cards per deck --> 13 groups of 4 A23456789JQK
	public Image bgimg;
	public BlackJackMenu BJM;
	public ArrayList<Gambler> players;
	public HashMap<String, Gambler> nameMap;
	public int playerIndex;
	public Gambler currentGambler;
	public Dealer dealer;
	public int suitCounter = 0;
	public final int maxPoints = 21;
	public final int[] hitBoxStats = { Game.WIDTH / 2 - 145, Game.HEIGHT - 140, 120, 40 }; // x, y, width, height
	public final int[] standBoxStats = { Game.WIDTH / 2 + 25, Game.HEIGHT - 140, 120, 40 };
	public final int[] insuranceBoxStats = { Game.WIDTH / 2 - 145, Game.HEIGHT - 75, 120, 40 };
	public final int[] splitBoxStats = { Game.WIDTH / 2 + 25, Game.HEIGHT - 75, 120, 40 };
	public boolean[] showBoxes = new boolean[4]; // hitBox, standBox, insuranceBox, splitBox
	public int[] betBox = { 200, 200, Game.WIDTH - 400, Game.HEIGHT - 400 };
	public int boxSelected;
	public int betMin = 50;
	public int betAmount = betMin;
	public boolean betDone;
	public boolean stopDrawing = false;

	public BlackJackPlayer(Game game) {
		this.game = game;
		IM = Game.IM;
		deck = new Stack<>();
		bgimg = IM.getImage("/blackjackbg.png");
		BJM = new BlackJackMenu(game);
		players = new ArrayList<>();
		nameMap = new HashMap<>();
		dealer = new Dealer(game, this);
		showBoxes[0] = true;
		showBoxes[1] = true;
		showBoxes[2] = true;
		showBoxes[3] = true;
		currentGambler = null;
	}

	// RULES:
	/*
	 * High Cards are worth 10 - Ace is 11 or 1 - Face cards are worth face value If
	 * you get a blackjack you get paid out 3 : 2 --> get back 1.5x what put in
	 * instead of 1:1 So I put in $50, I get back my 50 plus 75 (50 + 50/2) --> 125
	 * in total $75 profit Otherwise if you beat the dealer you get back 1:1 odds
	 * (put in $50, gets back $50 + $50 profit)
	 * 
	 * Step 1: Dealer deals one card face up to each player from left to right With
	 * the last card going to the dealer's hand which is face down Step 2: Then, the
	 * Dealer deals one more card face up to each player, left to right, then
	 * themselves Also face up this time Step 3: After this initial deal, the dealer
	 * will ask each person in succession if they need one or more cards Each player
	 * gets to ask to be "hit" - get a new card - until you either go over 21 or you
	 * think you have the best hand Once you have the cards you need you "Stand",
	 * then it continues to the next person until they stand
	 * 
	 * After all the players have completed the dealer reveals the down card If the
	 * dealers card is 16 points or lower, the dealer will always draw another card
	 * from the deck and will continue until the house hand is at least17 points If
	 * the dealer has 17 points off the deal without an Ace she stands
	 * 
	 * After this we compare hands, those who did not bust and have a higher score
	 * than dealer will win, black jacks get 3:2, and those who tie is called a Push
	 * and they just get what they put in back again
	 * 
	 * If the dealer has 17 but with an ace (soft 17), then continue as if its 6
	 * until you get 17 or bust
	 * 
	 * A player can bet an insurance hand which is half the betting wage Insurance
	 * bets are paid 2:1 If the dealer does not have 21, the player loses their
	 * insurance bet If the dealer DOES have 21, player loses initial bet but the
	 * house will pay insurance bets at 2:1 --> great investment If You also have 21
	 * though, it's a push and you make the insurance money back and keep your
	 * initial hand THIS IS DONE BEFORE THE DEALER FLIPS HER CARD AT THE END
	 * 
	 * EX: Player 1 First bet 60
	 * 
	 * Dealer draws Ace Player 1 draws 18 and stands Insurance bet: 30
	 * 
	 * Card flip: Dealer draws a King giving the house BLACKJACK Player gets: 30 +
	 * 30 = (60) + 30 back = 90 (30 profit total after bet) but loses the first 60
	 * bet
	 * 
	 * ELSE Player gets 21 : he keeps 60 and gets 2:1 odds on insurance racking in
	 * 60 + 90 = 150 ($90 profit)
	 * 
	 * ELSE Card Flip: Dealer draws a 2 and has a total of 3 or 13, Everyone loses
	 * their insurance bets but dealer can still flip more cards to get 21 without
	 * paying insurance bets off
	 * 
	 * SPLITTING: Two hands you always split are Aces and 8s
	 * 
	 * If you have two hands that have the same black jack numerical value (any face
	 * cards or 10 count as the same) You may split them into two separate hands and
	 * hit/stand on each hand as if it is unique but you need to put in an
	 * additional amount the same as your initial betting wager
	 * 
	 */

	public void render(Graphics g) {
		if (betDone) {
			renderBGDCP(g);
			renderBoxes(g);
		} else {
			renderBGDCP(g);
		}
	}

	public void renderInsurance(Graphics g) {
		renderBGDCP(g);
	if (!stopDrawing) addCardToDealer();
		
		
	}
	public void addCardToDealer() {
		int value = 0;
		int secValue = 0;
		for (Card c : dealer.dealerDeck) {
			value += c.value;
			if (c.secValue > 0) {
				secValue += c.secValue;
			} else {
				secValue += c.value;
			}
		}
		if (value < 17) {
			Card card = deck.pop();
			card.setUpsideDown(false);
			card.setXYPos(dealer.xPos - 30 - (75 * (dealer.dealerDeck.size()-2)), dealer.yPos + 130);
			dealer.addCardToDeck(card);
		} else {
			if (secValue < 12) {
				Card card = deck.pop();
				card.setUpsideDown(false);
				card.setXYPos(dealer.xPos - 30 - (75 * (dealer.dealerDeck.size()-2)), dealer.yPos + 130);
				dealer.addCardToDeck(card);
			}
			stopDrawing = true;
		}
	}
	public void tick() {
		if (betDone) {
			if (currentGambler.myDeck.get(0).value == currentGambler.myDeck.get(1).value) {
				showBoxes[3] = true;
			} else {
				showBoxes[3] = false;
			}

			if (currentGambler.usedInsurance) {
				showBoxes[2] = false;
			} else {
				showBoxes[2] = true;
			}
		}
	}

	public void renderBet(Graphics g) {
		g.drawImage(bgimg, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		renderPlayers(g);
		dealer.render(g);
		drawBetBox(g);
	}

	public void renderBGDCP(Graphics g) {
		g.drawImage(bgimg, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		renderPlayers(g);
		dealer.render(g);
		showCurrency(g);
	}

	public void drawBetBox(Graphics g) {
		int thickness = 5;
		int betBDist = 50;
		int betCDist = 145;

		g.setColor(Color.black);

		for (int i = 0; i < thickness; i++) {
			g.drawRect(betBox[0] + i, betBox[1] + i, betBox[2], betBox[3]);
		}
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(betBox[0] + thickness, betBox[1] + thickness, betBox[2] - thickness, betBox[3] - thickness);

		g.setColor(Color.white);
		g.drawString("Betting Box", betBox[0] + betBox[2] / 3 + 20, betBox[1] + 30);

		g.drawRect(betBox[0] + betBDist, betBox[1] + betBDist, betBox[2] - (2 * betBDist), betBox[3] - (2 * betBDist));

		g.drawRect(betBox[0] + 2 * betCDist - 35, betBox[1] + betCDist, betBox[2] - (4 * betCDist) + 30,
				betBox[3] - (2 * betCDist) - 10);
		g.setColor(Color.black);
		g.drawString("BET: ", betBox[0] + betCDist + 30, betBox[1] + betCDist + 35);
		g.drawString("Currency: " + currentGambler.currency, betBox[0] + betBox[2] / 3 + 10,
				betBox[1] + betBox[3] - 20);
		g.drawString("" + betAmount, betBox[0] + 2 * betCDist - 20, betBox[1] + betCDist + 30);

		g.setFont(new Font("Times New Roman", Font.BOLD, 40));
		g.drawString(currentGambler.getName(), betBox[0] + 2 * betCDist - 45, betBox[1] + betCDist / 2 + 10);
	}

	public void dealFirstHand() {
		Card card;
		for (Gambler g : players) {
			card = deck.pop();
			card.setUpsideDown(false);
			card.setXYPos(g.xPos + 10, g.yPos + 75);
			g.addToDeck(card);
		}
		for (Gambler g : players) {
			card = deck.pop();
			card.setUpsideDown(false);
			card.setXYPos(g.xPos - 60, g.yPos + 75);
			g.addToDeck(card);
		}
		card = deck.pop();
		card.setUpsideDown(false);
		card.setXYPos(dealer.xPos + 40, dealer.yPos + 130);
		dealer.dealerDeck.add(card);
		card = deck.pop();
		card.setUpsideDown(true);
		card.setXYPos(dealer.xPos - 30, dealer.yPos + 130);
		dealer.dealerDeck.add(card);
	}

	public void addCards() {
		// Make the cards based on how many decks
		for (int i = 0; i < 52; i++) {
			new Card(game, i);
		}
	}

	public void addCardToGambler(Card c, Gambler gambler) {
		if (gambler.myDeck.size() == 2) {
			c.setXYPos(gambler.xPos + 10, gambler.yPos + 120);
		} else if (gambler.myDeck.size() == 3) {
			c.setXYPos(gambler.xPos - 60, gambler.yPos + 120);
		} else if (gambler.myDeck.size() == 4) {
			c.setXYPos(gambler.xPos + 10, gambler.yPos + 165);
		} else if (gambler.myDeck.size() == 5) {
			c.setXYPos(gambler.xPos - 60, gambler.yPos + 165);
		}
		c.setUpsideDown(false);
		gambler.addToDeck(c);

	}

	public void placeInsurance() {
		int initial = currentGambler.roundBetAmount;
		currentGambler.setCurrency(currentGambler.currency - (initial / 2));
		currentGambler.setUsedInsurance(true);
	}

	public void setPlayerPositions() {
		// This is where we set each Gambler's xPos and yPos for our rendering
		// It is called once we click the startGame button in the BJMenu
		for (int i = 0; i < nameMap.size(); i++) {
			Gambler g = nameMap.get(players.get(i).getName());
			if (i == 0) {
				g.setXYPos(68, 244);
			} else if (i == 1) {
				g.setXYPos(922, 244);
			} else if (i == 2) {
				g.setXYPos(227, 419);
			} else if (i == 3) {
				g.setXYPos(763, 419);
			} else if (i == 4) {
				g.setXYPos(500, 492);
			}
		}

	}

	public void renderPlayers(Graphics g) {
		for (int i = 0; i < players.size(); i++) {
			Gambler gam = players.get(i);
			gam.render(g);
			for (int j = 0; j < gam.myDeck.size(); j++) {
				gam.myDeck.get(j).render(g);
			}
		}
		for (int i = 0; i < deck.size(); i++) {
			Card c = deck.get(i);
			c.render(g);
		}
		for (int i = 0; i < dealer.dealerDeck.size(); i++) {
			Card c = dealer.dealerDeck.get(i);
			c.render(g);
		}
	}

	public void decrementPlayer() {
		if (playerIndex == 0) {
			playerIndex = players.size() - 1;
		} else {
			playerIndex++;
		}
	}

	public void incrementPlayer() {
		if (playerIndex == players.size() - 1) {
			playerIndex = 0;
			setCurrentGambler();
		} else {
			playerIndex++;
			setCurrentGambler();
		}
	}
	public void nextPlayerForHit() {
		if (playerIndex == players.size() - 1) {
			dealer.dealerDeck.get(1).setUpsideDown(false);
			if (dealer.dealerDeck.get(0).value + dealer.dealerDeck.get(1).value == 21) {
				giveInsurance();
			} else {
				takeInsurance();
			}
		//	checkForWinner();
			game.setGameState(STATE.BJCollectInsurance);
		} else {
			playerIndex++;
			setCurrentGambler();
		}
	}
	
	public void checkForWinner() {
		//We need to loop through each gambler and compare their hand with the dealer's
		
	}

	public void giveInsurance() {
		for (Gambler gambler : players) {
			if (gambler.usedInsurance) {
				gambler.addToCurrency(3*gambler.roundBetAmount/2);
				setCoins(gambler, 5*gambler.roundBetAmount/2);
			}
		}
	}
	public void takeInsurance() {
		for (Gambler gambler : players) {
			if (gambler.usedInsurance) {
				setCoins(gambler, gambler.roundBetAmount);
			}
		}
	}
	public void shuffle() {
		Collections.shuffle(deck);
	}

	public void addGambler(Gambler p) {
		players.add(p);
	}

	public void removeGambler(Gambler p) {
		players.remove(p);
	}

	public void put(Gambler p, String name) {
		nameMap.put(name, p);
	}

	public Gambler getGambler(int num) {
		if (num < players.size())
			return players.get(num);
		else
			return null;
	}

	public void addToDeck(Card c) {
		deck.add(c);
	}

	public void setShowBoxTrue(int index) {
		if (index < 4)
			showBoxes[index] = true;
	}

	public void renderBoxes(Graphics g) {
		int thickness = 6;
		g.setColor(Color.black);
		for (int i = 0; i < thickness; i++) {
			if (showBoxes[0]) {
				g.drawRect(hitBoxStats[0] + i, hitBoxStats[1] + i, hitBoxStats[2], hitBoxStats[3]);
			}
			if (showBoxes[1]) {
				g.drawRect(standBoxStats[0] + i, standBoxStats[1] + i, standBoxStats[2], standBoxStats[3]);
			}
			if (showBoxes[2]) {
				g.drawRect(insuranceBoxStats[0] + i, insuranceBoxStats[1] + i, insuranceBoxStats[2],
						insuranceBoxStats[3]);
			}
			if (showBoxes[3]) {
				g.drawRect(splitBoxStats[0] + i, splitBoxStats[1] + i, splitBoxStats[2], splitBoxStats[3]);
			}
		}

		if (showBoxes[0]) {
			setColor(0, g);
			g.fillRect(hitBoxStats[0] + thickness, hitBoxStats[1] + thickness, hitBoxStats[2] - thickness,
					hitBoxStats[3] - thickness);
		}
		if (showBoxes[1]) {
			setColor(1, g);
			g.fillRect(standBoxStats[0] + thickness, standBoxStats[1] + thickness, standBoxStats[2] - thickness,
					standBoxStats[3] - thickness);
		}
		if (showBoxes[2]) {
			setColor(2, g);
			g.fillRect(insuranceBoxStats[0] + thickness, insuranceBoxStats[1] + thickness,
					insuranceBoxStats[2] - thickness, insuranceBoxStats[3] - thickness);
		}

		if (showBoxes[3]) {
			setColor(3, g);
			g.fillRect(splitBoxStats[0] + thickness, splitBoxStats[1] + thickness, splitBoxStats[2] - thickness,
					splitBoxStats[3] - thickness);
		}

		g.setColor(Color.black);

		if (showBoxes[0]) {
			g.drawString("Hit", hitBoxStats[0] + hitBoxStats[2] / 2 - 15, hitBoxStats[1] + hitBoxStats[3] / 2 + 10);
		}
		if (showBoxes[1]) {
			g.drawString("Stand", standBoxStats[0] + hitBoxStats[2] / 2 - 25,
					standBoxStats[1] + hitBoxStats[3] / 2 + 10);
		}
		if (showBoxes[2]) {
			g.drawString("Insurance", insuranceBoxStats[0] + hitBoxStats[2] / 2 - 50,
					insuranceBoxStats[1] + hitBoxStats[3] / 2 + 10);
		}
		if (showBoxes[3]) {
			g.drawString("Split", splitBoxStats[0] + hitBoxStats[2] / 2 - 25,
					splitBoxStats[1] + hitBoxStats[3] / 2 + 10);
		}
	}

	public void setColor(int num, Graphics g) {
		if (boxSelected == num) {
			g.setColor(Color.BLUE);
		} else {
			g.setColor(Color.LIGHT_GRAY);
		}
	}

	public int numBoxes() {
		int i = 0;
		for (Boolean b : showBoxes) {
			if (b)
				i++;
		}
		return i;
	}

	public void decrementBox() {
		if (boxSelected == 0) {
			if (showBoxes[3])
				boxSelected = 3;
			else if (showBoxes[2])
				boxSelected = 2;
			else
				boxSelected = 1;
		} else {
			boxSelected--;
		}

	}

	public void incrementBox() {

		if (boxSelected == 3) {
			boxSelected = 0;
			return;
		} else if (boxSelected == 2) {
			if (showBoxes[3]) {
				boxSelected = 3;
			} else {
				boxSelected = 0;
			}
		} else if (boxSelected == 1) {
			if (showBoxes[2]) {
				boxSelected = 2;
			} else if (showBoxes[3]) {
				boxSelected = 3;
			} else {
				boxSelected = 0;
			}
		} else {
			boxSelected = 1;
		}

		if (!showBoxes[boxSelected]) {
			boxSelected++;
			if (!showBoxes[boxSelected]) {
				boxSelected++;
			}
		}
		if (boxSelected > 3) {
			boxSelected = 0;
		}

	}

	public void showCurrency(Graphics g) {
		setCurrentGambler();
		g.setColor(Color.black);
		int thickness = 5;
		int y = Game.HEIGHT - 90;
		int width = 240;
		int height = Game.HEIGHT - y - thickness;
		for (int i = 0; i < thickness; i++) {
			g.drawRect(0 + i, y + i, width, height);
		}
		g.setColor(Color.white);
		g.fillRect(thickness, y + thickness, width - thickness, height - thickness);
		g.setColor(Color.black);
		g.drawString(currentGambler.getName(), thickness + 20, y + thickness + 20);
		g.drawString("Currency: " + currentGambler.currency, thickness + 20, y + thickness + 45);
	}

	public void setCurrentGambler() {
		currentGambler = getGambler(playerIndex);
	}

	public void incrementBetAmount(int val) {
		if (betAmount + val < betMin) {
			betAmount = betMin;
			return;
		} else {
			betAmount += val;
		}
	}

	public void resetBetAmount() {
		betAmount = betMin;
	}

	public void setBetDone(boolean tf) {
		betDone = tf;
	}

	public boolean allDidBet() {
		for (Gambler g : players) {
			if (!g.didBet)
				return false;
		}
		return true;
	}

	/**
	 * Returns an array list of integers determining how many of each coin should be
	 * displayed on the board 
	 * list.get(0) will be red coins ($5) 
	 * list.get(1) will be green coins ($15) 
	 * list.get(2) will be blue coins ($50) 
	 * list.get(3) will be black coins ($100) 
	 * list.get(4) will be gold coins ($500)
	 * 
	 * @param amount --> the amount of coins to distribute among the categories above
	 * @returns --> an array list with the listed information
	 */
	public static ArrayList<Integer> coinsUsed(int amount) {

		int[] coins = new int[5];
		ArrayList<Integer> coinzz = new ArrayList<>();
		int tempAmount = amount;
		while (tempAmount > 4) {
			if (tempAmount >= 500) {
				coins[4]++;
				tempAmount -= 500;
				continue;
			}
			if (tempAmount >= 100) {
				coins[3]++;
				tempAmount -= 100;
				continue;
			}
			if (tempAmount >= 50) {
				coins[2]++;
				tempAmount -= 50;
				continue;
			}
			if (tempAmount >= 15) {
				coins[1]++;
				tempAmount -= 15;
				continue;
			}
			if (tempAmount >= 5) {
				coins[0]++;
				tempAmount -= 5;
			}
		}
		for (int i = 0; i < coins.length; i++) {
			coinzz.add(coins[i]);
		}
		return coinzz;
	}
	public void setCoins(Gambler gambler, int amount) {
		gambler.setCoinList(coinsUsed(amount));
	}
}
