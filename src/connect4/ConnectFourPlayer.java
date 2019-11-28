package connect4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Stack;

import gameSetUps.*;

public class ConnectFourPlayer {

	public Game game;
	public Handler handler;
	public String name1;
	public String name2;
	public Image boardimg;
	public Image bgimg;
	public Image header;
	public int xDistBoard, yDistBoard;
	public int rowIndex;
	public int distBetween = 97;
	public boolean[][] hasCoin;
	public char[][] coinGrid;
	public char whoseTurn = 'g';
	public Color gold = new Color(255,215,0);
	
	public ConnectFourPlayer(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
		boardimg = Game.IM.getImage("/connectfourboard.png");
		header = Game.IM.getImage("/connectfourheader.png");
		bgimg = Game.IM.getImage("/cfourbackground.jpg");
		xDistBoard = 150;
		yDistBoard = 100;
		hasCoin = new boolean[7][6];
		coinGrid = new char[7][6];
	}
	
	public void setNames(Stack<Character> name1, Stack<Character> name2) {
		String s = "";
		String s2 = "";
		for (char c : name1) {
			s += c;
		}
		for (char d : name2) {
			s2 += d;
		}
		this.name1 = s;
		this.name2 = s2;
	}
	
	public void render(Graphics g) {
		drawBackDrop(g);
		drawTurnCoin(g, whoseTurn);
		drawTarget(g, rowIndex);
	}
	
	public void placeCoin(int x, int y) {
	if (x >= 0 && y >= 0 && x <= 7 && y <= 6) {	
		new Coin(x, y, game, handler, whoseTurn);
		hasCoin[x][y] = true;
		coinGrid[x][y] = whoseTurn;
	}
	}
	
	public int largestIndex(int row) {
		
		ArrayList<Boolean> theRow = new ArrayList<>();
		
		for (boolean a : hasCoin[row]) {
			theRow.add(a);
		}
		
		for (int i = 0; i < theRow.size(); i++) {
			if (theRow.get(i)) return i-1;
		}
		
		return 5;
	}
	
	public void drawTarget(Graphics g, int index) {
		
		int thickness = 6;
		int circWidth = 72;
		int circHeight = 85;
		
		if (whoseTurn == 'g') g.setColor(gold);
		else  g.setColor(Color.red);
		
		for (int i = 0; i < thickness; i++) {
		g.drawArc(xDistBoard + (index * distBetween) + 16 + i + rowIndex, yDistBoard + 8 + i, circWidth, circHeight, 0, 360);
		}
	}
	
	public void drawTurnCoin(Graphics g, char whoseTurn) {
		
		int coinWidth = 50;
		int xCoin, yCoin;
		if (whoseTurn == 'g') {
		xCoin = 120;
		yCoin = 30;
		} else {
			xCoin = 890;
			yCoin = 30;
		}
		int reg = 4;
		g.setColor(Color.black);
		g.drawArc(xCoin, yCoin, coinWidth, coinWidth, 0, 360);
		g.drawArc(xCoin + coinWidth/4 - reg, yCoin + coinWidth/4 - reg, 2*coinWidth/3, 2*coinWidth/3, 0, 360);
		g.drawLine(xCoin, yCoin + coinWidth/2, xCoin + coinWidth, yCoin + coinWidth/2);
		g.drawLine(xCoin + coinWidth/2, yCoin , xCoin + coinWidth/2, yCoin + coinWidth);
	}
	
	
	
	public void drawBackDrop(Graphics g) {
		
		g.drawImage(bgimg, 0,0,Game.WIDTH,Game.HEIGHT,null);
		g.setColor(Color.white);
		int distFromBot = 20;
		g.fillRect(xDistBoard, yDistBoard, Game.WIDTH-2*xDistBoard, Game.HEIGHT-yDistBoard - distFromBot);
		
		g.drawImage(boardimg, xDistBoard, yDistBoard, Game.WIDTH-2*xDistBoard, Game.HEIGHT-yDistBoard - distFromBot, null);
		
		int thickness = 8;
		int width = 200;
		int xStart = Game.WIDTH - width - 15;
		
		int height = 85;
		
		g.setColor(Color.black);
		for (int i = 0; i < thickness; i++) {
		g.drawRect(i, i, width, height);
		g.drawRect(xStart + i, i, width, height);
		}
		g.setColor(gold);
		g.fillRect(thickness, thickness, width - thickness, height - thickness);
		g.setColor(Color.red);
		g.fillRect(xStart + thickness, thickness, width-thickness, height-thickness);
		
		g.setColor(Color.white);
		g.setFont(new Font("Times New Roman", Font.BOLD, 30));
		g.drawString(name1, thickness + 15, thickness + height/2);
		g.drawString(name2, xStart + thickness + 15, thickness + height/2);
		g.drawImage(header, 350, 0, 250, 80, null);
		
	}
	
	public void nextTurn() {
		if (whoseTurn == 'g') {
			whoseTurn = 'r';
		} else {
			whoseTurn = 'g';
		}
	}
	
	public void incrementRowIndex() {
		rowIndex++;
		if (rowIndex == 7) {
			rowIndex = 0;
		}
	}
	public void decrementRowIndex() {
		rowIndex--;
		if (rowIndex == -1) {
			rowIndex = 6;
		}
	}
	
	public void checkWinner() {
		
		//check each row of coingrid
		//check each column of coingrid
		//check every diagonal available in coingrid
		
	}
}
