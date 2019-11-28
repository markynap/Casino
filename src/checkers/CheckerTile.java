package checkers;

import java.awt.Color;
import java.awt.Graphics;

import gameSetUps.*;

public class CheckerTile {

	public int x, y, scale;
	public boolean isOccupied;
	public boolean selected;
	public Game game;
	public Handler handler;
	public int offSet;
	public int xDist, yDist;
	public CheckerPiece currentPiece;
	
	public CheckerTile(int x, int y, int scale, Game game, Handler handler, int xDist, int yDist, int offSet) {
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.game = game;
		this.handler = handler;
		this.offSet = offSet;
		this.xDist = xDist;
		this.yDist = yDist;
		handler.addCheckerTile(this);
		currentPiece = handler.getCheckerPiece(x, y);
	}
	
	public void render(Graphics g) {
		
		if (selected) {
			g.setColor(Color.BLUE);
			g.fillRect(xDist + x * scale, yDist + y * scale, scale-1, scale-1);
			return;
		}
		
		if (offSet == 1) {
			g.setColor(Color.red);
		} else {
			g.setColor(Color.black);
		}
		
		g.fillRect(xDist + x * scale, yDist + y * scale, scale-1, scale-1);
		
	}
	
	public void tick() {
		
		if (currentPiece == null) {
			isOccupied = false;
			currentPiece = handler.getCheckerPiece(x, y);
		} else {
			isOccupied = true;
		}
		
	}
	
	public void resetCurrentPiece() {
		currentPiece = null;
	}
	public void setSelected(boolean tf) {
		selected = tf;
	}
}
