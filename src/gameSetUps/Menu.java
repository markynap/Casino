package gameSetUps;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class Menu {

	public Image bgImg;
	public ImageManager IM;
	public int xDist;
	public int height;
	public int boxLength, boxYScale;
	public int chessBoxX, connectBoxX, checkersBoxX, blackjackBoxX;
	public int chessBoxY, connectBoxY, checkersBoxY, blackjackBoxY;
	public int selectedIndex;
	
	public Menu(ImageManager IM) {
		this.IM = IM;
		bgImg = IM.getImage("/casinobg.jpg");
		xDist = 125;
		height = 50;
		boxLength = 2*Game.WIDTH/3;
		boxYScale = Game.HEIGHT/5;
		chessBoxX = xDist + boxLength/2 - 40;
		chessBoxY = Game.HEIGHT/4;
		connectBoxX = xDist + boxLength/2 - 50;
		connectBoxY = 2*Game.HEIGHT/4 - 40;
		checkersBoxX = xDist + boxLength/2 - 50;
		checkersBoxY = 3*Game.HEIGHT/5 + 35;
		blackjackBoxX = xDist + boxLength/2 - 55;
		blackjackBoxY = 4*Game.HEIGHT/5 + 35;
		
	}
	
	
	public void render(Graphics g) {
		
		
		
		g.drawImage(bgImg, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Times New Roman", Font.BOLD, 36));
		
		g.drawString("Menu", 3*Game.WIDTH/7 + 15, Game.HEIGHT/8);
		
		g.drawString("Chess", chessBoxX, chessBoxY);
		g.setColor(Color.WHITE);
		g.drawRect(xDist, boxYScale, boxLength, height);
		g.setColor(Color.blue);
		g.drawString("Connect 4", connectBoxX, connectBoxY);
		g.drawRect(xDist, 2*boxYScale, boxLength, height);
		g.setColor(Color.red);
		g.drawString("Checkers", checkersBoxX, checkersBoxY);
		g.drawRect(xDist, 3*boxYScale, boxLength, height);
		g.setColor(Color.black);
		g.drawString("Black Jack", blackjackBoxX, blackjackBoxY);
		g.drawRect(xDist, 4*boxYScale, boxLength, height);
		
		drawSelectedBox(g, selectedIndex);
	}
	
	public void drawSelectedBox(Graphics g, int index) {
		
		int thickness = 7;
		Color selectedColor = Color.blue;
		int boxY = (index + 1) * boxYScale;
			
		for (int i = 0; i < thickness; i++) {
		g.setColor(selectedColor);
		g.drawRect(xDist - thickness + i + 1, boxY - thickness + i + 1, boxLength+1, height+1);
		}
		
		
	}
	public void incrementSelected() {
		selectedIndex++;
		if (selectedIndex == 4) {
			selectedIndex = 0;
		}
	}
	public void decrementSelected() {
		selectedIndex--;
		if (selectedIndex == -1) {
			selectedIndex = 3;
		}
	}
	
}
