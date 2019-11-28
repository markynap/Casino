package connect4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Stack;

import gameSetUps.*;

public class HomeMenuC4 {

	public Game game;
	public Handler handler;
	public Image bgimg;
	public int nameBarX, nameBarY, nameBarX2, nameBarWidth, nameBarHeight;
	public long time, time2;
	public boolean onBoxOne, onBoxTwo;
	public Stack<Character> name1;
	public Stack<Character> name2;
	public int indentation = 10;
	public int maxLength = 11;
	public boolean readyToStart;
	public int enterWidth, enterX, enterY, enterHeight;
	
	public HomeMenuC4(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
		bgimg = Game.IM.getImage("/connectbgimg.jpg");
		
		nameBarX = 50;
		nameBarY = 75;
		nameBarWidth = 250;
		nameBarX2 = Game.WIDTH - nameBarWidth - nameBarX;
		nameBarHeight = 75;
		time = System.currentTimeMillis();
		time2 = System.currentTimeMillis();
		name1 = new Stack<>();
		name2 = new Stack<>();
	}
	
	public void render(Graphics g) {
		
		int thickness = 5;
		int letterSpacing = 20;
		g.drawImage(bgimg, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		
		if (readyToStart) {
			drawEnterBox(g);
		}
		
		
		g.setColor(Color.black);
		for (int i = 0; i < thickness; i++) {
		g.drawRect(nameBarX+i, nameBarY+i, nameBarWidth, nameBarHeight);
		g.drawRect(nameBarX2+i, nameBarY+i, nameBarWidth, nameBarHeight);
		}
		g.setColor(Color.white);
		g.fillRect(nameBarX + thickness, nameBarY + thickness, nameBarWidth - thickness, nameBarHeight - thickness);
		g.fillRect(nameBarX2 + thickness, nameBarY + thickness, nameBarWidth - thickness, nameBarHeight - thickness);
		g.setFont(new Font("Times New Roman", Font.BOLD, 22));
		g.drawString("Enter Player 1 Name:", nameBarX + 25, nameBarY - 10);
		g.drawString("Enter Player 2 Name:", nameBarX2 + 25, nameBarY - 10);
		
		if (onBoxOne) {
			drawCursor(g, 1, name1.size());
		} else if (onBoxTwo) {
			drawCursor(g, 2, name2.size());
		}
		if (!name1.isEmpty()) {
			int index = 0;
			g.setColor(Color.black);
			for (char c : name1) {
				g.drawString("" + c, nameBarX + thickness + (letterSpacing*index) + indentation, nameBarY + 40);
				index++;
			}
		}
		if (!name2.isEmpty()) {
			int index = 0;
			g.setColor(Color.black);
			for (char c : name2) {
				g.drawString("" + c, nameBarX2 + thickness + (letterSpacing*index) + indentation, nameBarY + 40);
				index++;
			}
		}
		
	if (!readyToStart) {
		if (!name1.isEmpty() && name2.size() > 2) {
			readyToStart = true;
		}
	}
	}
	
	public void drawEnterBox(Graphics g) {
		enterX = Game.WIDTH/3 + 75;
		enterY = 120;
		enterWidth = 160;
		enterHeight = 50;
		int thickness = 5;
		g.setColor(Color.black);
		for (int i = 0; i < thickness; i++) {
		g.drawRect(enterX + i, enterY + i, enterWidth, enterHeight);
		}
		g.setColor(Color.red);
		g.fillRect(enterX + thickness, enterY + thickness, enterWidth - thickness, enterHeight - thickness);
		g.setColor(Color.black);
		g.setFont(new Font("Times New Roman", Font.BOLD, 22));
		g.drawString("Press To Play", enterX + 15, enterY + 35);
	}
	
	
	public void drawCursor(Graphics g, int whichBox, int position) {
		
		int fontSize = 20;
		int distFromChar = 7;
		int delay = 1000;
		int spacing = 300;
		if (whichBox == 1) {
			if (System.currentTimeMillis() - time > spacing) {
				g.setColor(Color.black);
				g.drawLine(nameBarX + (position*fontSize) + distFromChar + indentation, nameBarY + 16, nameBarX + position*fontSize + distFromChar + indentation, nameBarY + nameBarHeight - 16);
				if (System.currentTimeMillis() - time2 > delay) {
					time = System.currentTimeMillis();
					time2 = System.currentTimeMillis();
				}
			} 
		} else {
			if (System.currentTimeMillis() - time > spacing) {
				g.setColor(Color.black);
				g.drawLine(nameBarX2 + (position*fontSize) + distFromChar + indentation, nameBarY + 16, nameBarX2 + (position*fontSize) + distFromChar + indentation, nameBarY + nameBarHeight - 16);
				if (System.currentTimeMillis() - time2 > delay) {
					time = System.currentTimeMillis();
					time2 = System.currentTimeMillis();
				}
			} 
		}
		
		
	}
	
	public void setOnBoxOne() {
		onBoxTwo = false;
		onBoxOne = true;
	}
	public void setOnBoxTwo() {
		onBoxOne = false;
		onBoxTwo = true;
	}
	public void flipBoxes() {
		if (onBoxOne) {
			setOnBoxTwo();
		} else if (onBoxTwo) {
			setOnBoxOne();
		}
	}
	
}
