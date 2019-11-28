package blackJack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import gameSetUps.*;

public class Card {

	public Game game;
	public int cardNum;
	public char suit; //C S H D 
	public char num; // 1 2 3 4 5... J Q K A
	public char secNum; //This is the zero for card#10
	boolean inDeck;
	boolean inHand;
	public int xPos, yPos, cardWidth, cardHeight;
	public Image suitpic;
	public Image backImg;
	public int value;
	public int secValue;
	public boolean upsideDown;
	
	public Card(Game game, int num) {
		this.game = game;
		this.cardNum = num;
		secNum = ' ';
		setSuitNum();
		game.BJP.addToDeck(this);
		xPos = 40;
		yPos = 40;
		cardWidth = 60;
		cardHeight = 120;
		upsideDown = true;
	}
	
	public void setSuitNum() {
		String s = "";
		//this is where we figure out the suit and num based on the cardNum input we get
		if (cardNum % 13 == 0) {
			num = 'A';
			value = 11;
			secValue = 1;
			game.BJP.suitCounter++;
		} else {
			int newNum = cardNum % 13;
			if (newNum < 9) {
				 s = Integer.toString(newNum+1);
				 value = newNum+1;
				 num = s.charAt(0);
			} else {
				if (newNum == 9) {
					num = '1';
					secNum = '0';
					value = 10;
				} else if (newNum == 10) {
					num = 'J';
					value = 10;
				} else if (newNum == 11) {
					num = 'Q';
					value = 10;
				} else if (newNum == 12) {
					num = 'K';
					value = 10;
				}
			}
		}
		
		if (game.BJP.suitCounter == 1) {
			suit = 'C';
			suitpic = Game.IM.getImage("/clubs.png");
		} else if (game.BJP.suitCounter == 2) {
			suit = 'S';
			suitpic = Game.IM.getImage("/spades.png");
		} else if (game.BJP.suitCounter == 3) {
			suit = 'H';
			suitpic = Game.IM.getImage("/heart.png");
		} else if (game.BJP.suitCounter == 4) {
			suit = 'D';
			suitpic = Game.IM.getImage("/diamonds.png");
		}
		backImg = Game.IM.getImage("/backcard.png");
	}
	
	public void render(Graphics g) {
		if (upsideDown) {
			g.drawImage(backImg, xPos, yPos, cardWidth, cardHeight, null);
		} else {
		g.setColor(Color.black);
		int thickness = 1;
		g.drawRect(xPos, yPos, cardWidth, cardHeight);
		g.setColor(Color.white);
		g.fillRect(xPos+thickness, yPos+thickness, cardWidth - thickness, cardHeight - thickness);
		g.setColor(Color.black);
		g.setFont(new Font("Times New Roman", Font.BOLD, 25));
		g.drawString("" + num + secNum, xPos + thickness + 5, yPos + thickness + 20);
		g.drawImage(suitpic, xPos + thickness + 5, yPos + thickness + 30, cardWidth/3, cardWidth/3, null);
		
		g.drawString("" + num + secNum, xPos + thickness + 4 + cardWidth/2, yPos + thickness + 2*cardHeight/3 + 32);
		g.drawImage(suitpic, xPos + thickness + 5 + cardWidth/2, yPos + thickness + 20 + cardHeight/2 - 10, cardWidth/3, cardWidth/3, null);
		}
	}
	public void setXYPos(int x, int y) {
		this.xPos = x;
		this.yPos = y;
	}
	public void setUpsideDown(boolean tf) {
		upsideDown = tf;
	}
}
