package blackJack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Stack;

import gameSetUps.Game;

public class Gambler { // this is the class that handles each player playing in the game of Black Jack

	public Game game;
	public int playerNum;
	public String name;
	public int xPos, yPos;
	public Color myColor;
	public Stack<Card> myDeck;
	public Color gold = new Color(255, 215, 0);
	public int currency = 1000;
	public boolean didBet;
	public int roundBetAmount;
	public boolean usedInsurance;
	public ArrayList<Integer> coinList;
	public ArrayList<Coin> coinWallet;

	public Gambler(Game game, int num) {
		this.game = game;
		this.playerNum = num;
		name = "";
		myDeck = new Stack<>();
		usedInsurance = false;
		coinList = new ArrayList<>();
		coinWallet = new ArrayList<>();
		if (playerNum == 0)
			myColor = Color.green;
		if (playerNum == 1)
			myColor = Color.red;
		if (playerNum == 2)
			myColor = Color.yellow;
		if (playerNum == 3)
			myColor = Color.magenta;
		if (playerNum == 4)
			myColor = Color.cyan;
		if (playerNum < 5)
			game.BJP.addGambler(this);
	}

	public void render(Graphics g) {
		g.setColor(myColor);
		g.setFont(new Font("Times New Roman", Font.BOLD, 31));
		g.drawString(getName(), xPos - 40, yPos + 60);
		renderCoins(g);
	if (checkIsTurn())	renderGoldRing(g);
	}

	public void renderCoins(Graphics g) {
		if (!coinWallet.isEmpty()) {
			for (int i = 0; i < coinWallet.size(); i++) {
				coinWallet.get(i).render(g);
			}
		}
	}
	
	public void renderMenu(Graphics g) {
		int thickness = 5;
		int width = 450;
		int height = 75;
		int xDist = game.BJP.BJM.addX - 160;
		int yDist = game.BJP.BJM.addY + ((5 * height / 4) * (playerNum + 1)) - 30;

		g.setColor(Color.black);

		for (int i = 0; i < thickness; i++) {
			g.drawRect(xDist + i, yDist + i, width, height);
		}

		g.setColor(Color.white);
		g.fillRect(xDist + thickness, yDist + thickness, width - thickness, height - thickness);
		g.setColor(Color.black);
		g.setFont(new Font("Times New Roman", Font.BOLD, 26));
		g.drawString(getName(), xDist + 25, yDist + 45);

	}

	public String getName() {
		String s = "";
		for (int i = 0; i < game.BJP.BJM.names.get(playerNum).size(); i++) {
			char c = game.BJP.BJM.names.get(playerNum).get(i);
			s += c;
		}
		return s;
	}

	public void setXYPos(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public void renderGoldRing(Graphics g) {

		g.setColor(gold);
		int thickness = 9;
		int x = xPos - 55;
		int ringWidth = 107;
		int ringHeight = 100;
		if (playerNum == 0 || playerNum == 1) {
			for (int i = 0; i < thickness; i++) {
				g.drawArc(x + i, yPos + i, ringWidth, ringHeight, 0, 360);
			}
		} else if (playerNum == 2) {
			for (int i = 0; i < thickness; i++) {
				g.drawArc(xPos - 62 + i, yPos + i, ringWidth+4, ringHeight+1, 0, 360);
			}
		} else if (playerNum == 3) {
			for (int i = 0; i < thickness; i++) {
				g.drawArc(xPos - 52 + i, yPos + i, ringWidth+4, ringHeight+1, 0, 360);
			}
		} else if (playerNum == 4) {
			for (int i = 0; i < thickness; i++) {
				g.drawArc(x-7 + i, yPos + i + 1, ringWidth+11, ringHeight+5, 0, 360);
			}
		}
	}
	public boolean checkIsTurn() {
		if (game.BJP.playerIndex == playerNum) return true;
		else return false;
	}
	public void setCurrency(int c) {
		currency = c;
	}
	public void addToCurrency(int val) {
		currency += val;
	}
	public void setDidBet(boolean tf) {
		didBet = tf;
	}
	public void addToDeck(Card c) {
		myDeck.add(c);
	}
	public void removeFromDeck(Card c) {
		myDeck.remove(c);
	}
	public void setRoundBetAmount(int amount) {
		roundBetAmount = amount;
	}
	public void setUsedInsurance(boolean tf) {
		usedInsurance = tf;
	}
	public void setCoinList(ArrayList<Integer> coins) {
		coinList = coins;
		coinWallet.clear();
		for (int i = 0; i < coinList.size(); i++) {
			for (int j = 0; j < coinList.get(i); j++) {
				new Coin(game, this, i, j);
			}
		}
	}
	public void addToCoinWallet(Coin c) {
		coinWallet.add(c);
	}
}
