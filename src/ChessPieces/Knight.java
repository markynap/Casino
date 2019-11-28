package ChessPieces;

import java.awt.Graphics;
import java.util.ArrayList;

import gameSetUps.ChessObject;
import gameSetUps.ChessTile;
import gameSetUps.Game;

public class Knight extends ChessObject{

	public Knight(int x, int y, int scale, String type, String team, Game game) {
		super(x, y, scale, type, team, game);
		
		if (team.equalsIgnoreCase("White")) {
			img = Game.IM.getImage("/allyknight.png");
		} else {
			img = Game.IM.getImage("/enemyknight.png");
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, x, y, scale, scale, null);
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<ChessTile> getMoves() {
		moves.clear();
		int scale = Game.WIDTH / 12;
		addMove(game.handler.getChessTile(x + scale, y + 2*scale));
		addMove(game.handler.getChessTile(x + scale, y - 2*scale));
		addMove(game.handler.getChessTile(x - scale, y + 2*scale));
		addMove(game.handler.getChessTile(x - scale, y - 2*scale));
		
		addMove(game.handler.getChessTile(x + 2*scale, y + scale));
		addMove(game.handler.getChessTile(x + 2*scale, y - scale));
		addMove(game.handler.getChessTile(x - 2*scale, y + scale));
		addMove(game.handler.getChessTile(x - 2*scale, y - scale));
		return moves;
	}

}
