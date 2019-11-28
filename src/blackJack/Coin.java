package blackJack;

import java.awt.Graphics;
import java.awt.Image;

import gameSetUps.Game;

public class Coin {

	public Image image;
	public Game game;
	public Gambler carrier;
	public int numCoin;
	public int scale = 30;
	public int id;
	public int yDist, xDist;
	
	public Coin(Game game, Gambler carrier, int id, int numCoin) {
		this.game = game;
		this.carrier = carrier;
		this.numCoin = numCoin;
		this.id = id;
		yDist = 30 + (15 * numCoin);
		xDist = 24 * (id+1);
		carrier.addToCoinWallet(this);
		if (id == 0) {
			image = Game.IM.getImage("/redchip.png");
		} else if (id == 1) {
			image = Game.IM.getImage("/greenchip.png");
		} else if (id == 2) {
			image = Game.IM.getImage("/bluechip.png");
		} else if (id == 3) {
			image = Game.IM.getImage("/blackchip.png");
		} else if (id == 4) {
			image = Game.IM.getImage("/goldchip.png");
		}	
	}
	
	public void render(Graphics g) {
		
	if (carrier.playerNum == 0 || carrier.playerNum == 2 || carrier.playerNum == 4) {	
		g.drawImage(image, carrier.xPos + xDist, carrier.yPos - yDist, scale, scale, null);
	} else if (carrier.playerNum == 1 || carrier.playerNum == 3) {
		g.drawImage(image, carrier.xPos - xDist, carrier.yPos - yDist, scale, scale, null);
	}
	}
}
