package blackJack;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Stack;

import gameSetUps.*;

public class Dealer {
	public Game game;
	public BlackJackPlayer BJP;
	public ArrayList<String> sayings;
	public Image img;
	public Stack<Card> dealerDeck;
	public int xPos, yPos;
	
	public Dealer(Game game, BlackJackPlayer BJP) {
		sayings = new ArrayList<>();
		this.game = game;
		this.BJP = BJP;
		img = Game.IM.getImage("/dealer.png");
		dealerDeck = new Stack<>();
		xPos = Game.WIDTH/2 - 60;
		yPos = 45;
	}
	
	public void render(Graphics g) {
		
		g.drawImage(img, xPos, yPos, 120,120,null);
		
	}
	
	public void addSayings() {
		sayings.add("Welcome to Black Jack!"); //index 0	
		
		
		//sayings.add("Have a good night!"); index LAST
	}
	public void addCardToDeck(Card c) {
		dealerDeck.add(c);
	}
}
