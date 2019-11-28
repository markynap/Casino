package connect4;

import java.awt.Color;
import java.awt.Graphics;

import gameSetUps.*;

public class Coin {
	
	public Game game;
	public Handler handler;
	public char team;
	public int x,y;
	public int yPosition;
	public Color gold = new Color(255,215,0);
	
	
	public Coin(int x, int y, Game game, Handler handler, char team) {
		this.x = x;
		this.y = y;
		this.game = game;
		this.handler = handler;
		this.team = team;
		handler.addCoin(this);
	}
	
	
	public void render(Graphics g) {
		int circWidth = 78;
		int circHeight = 90;
		if (team == 'g') {
			g.setColor(gold);
		} else {
			g.setColor(Color.red);
		}
		int xPosition = game.c4p.xDistBoard + (x * game.c4p.distBetween) + 15 + (3*x)/2;
		if (yPosition < game.c4p.yDistBoard + (y * (game.c4p.distBetween+6)) + 8 + 3*y/2) {
			yPosition += 2;
		}
	//	g.drawArc(xDistBoard + (index * distBetween) + 16 + i + rowIndex, yDistBoard + 8 + i, circWidth, circHeight, 0, 360);
		g.fillArc(xPosition, yPosition, circWidth, circHeight, 0, 360);
		
	}
}
