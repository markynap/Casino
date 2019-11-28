package ChessPieces;

import java.awt.Graphics;
import java.util.ArrayList;

import gameSetUps.ChessObject;
import gameSetUps.ChessTile;
import gameSetUps.Game;

public class King extends ChessObject{

	public King(int x, int y, int scale, String type, String team, Game game) {
		super(x, y, scale, type, team, game);
		if (team.equalsIgnoreCase("White")) {
			img = Game.IM.getImage("/allyking.png");
		} else if (team.equalsIgnoreCase("Black")) {
			img = Game.IM.getImage("/enemyking.png");
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
		addMove(game.handler.getChessTile(x, y + scale));
		addMove(game.handler.getChessTile(x, y - scale));
		addMove(game.handler.getChessTile(x + scale, y));
		addMove(game.handler.getChessTile(x - scale, y));
		addMove(game.handler.getChessTile(x - scale, y - scale));
		addMove(game.handler.getChessTile(x - scale, y + scale));
		addMove(game.handler.getChessTile(x + scale, y - scale));
		addMove(game.handler.getChessTile(x + scale, y + scale));
		
		//Give kings diagonal access
		return moves;
	}

}
