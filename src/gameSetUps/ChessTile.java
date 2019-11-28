package gameSetUps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class ChessTile {

	
	public int x, y, scale;
	public boolean isOccupied;
	public boolean selected;
	public ChessObject carrier;
	public int offSet;
	public Game game;
	
	
	public ChessTile(int x, int y, int scale, int offSet, Game game) {
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.game = game;
		this.offSet = offSet;
		game.handler.addChessTile(this);
	}
	
	
	public void render(Graphics g) {
		
		if (selected) {
			g.setColor(Color.blue);
			g.fillRect(x, y, scale-1, scale-1);
			return;
		}
		
		if (offSet == 1) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x, y, scale, scale);
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(x, y, scale, scale);
		}
		
	}
	
	public void tick() {
		if (carrier == null) {
			isOccupied = false;
			carrier = game.handler.getChessPiece(x, y);
		} else {
			isOccupied = true;
			carrier.tick();
		}
	}
	
	public ChessObject getCarrier() {
		return carrier;
	}
	public void setSelected(boolean tf) {
		selected = tf;
	}
	public void setCarrier(ChessObject o) {
		carrier = o;
	}
}
