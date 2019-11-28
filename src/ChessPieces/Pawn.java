package ChessPieces;

import java.awt.Graphics;
import java.util.ArrayList;

import gameSetUps.ChessObject;
import gameSetUps.ChessTile;
import gameSetUps.Game;
import gameSetUps.Game.STATE;

public class Pawn extends ChessObject{

	public boolean extinct;
	
	public Pawn(int x, int y, int scale, String type, String team, Game game) {
		super(x, y, scale, type, team, game);
		if (team.equalsIgnoreCase("White")) {
			img = Game.IM.getImage("/allypawn.png");
		} else if (team.equalsIgnoreCase("Black")) {
			img = Game.IM.getImage("/enemypawn.png");
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, x, y, scale, scale, null);
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		int newY = (y - 26)/scale;
	if (!extinct) {
		if (team.equalsIgnoreCase("White")) {
			if (newY == 7) {
				game.cHUD.setRebirthedPawn(this);
				game.cHUD.setRebirthTeam(team);
				game.setGameState(STATE.RebirthState);
			}
		} else {
			if (newY == 0) {
				game.cHUD.setRebirthedPawn(this);
				game.cHUD.setRebirthTeam(team);
				game.setGameState(STATE.RebirthState);
			}
		}
	}
	}

	@Override
	public ArrayList<ChessTile> getMoves() {
		moves.clear();
		int scale = Game.WIDTH / 12;
		ChessTile nextmove;
		ChessTile oneMove;
		if (team.equalsIgnoreCase("White")) {
			nextmove = game.handler.getChessTile(x, y + scale);
			if (nextmove != null) {
				if (!nextmove.isOccupied) {
					addMove(nextmove);
				}
			}
			
			
			// and if it is the first time we have used this pawn we can check this condition
			if (!hasMoved) {
			nextmove = game.handler.getChessTile(x, y + 2*scale);
			oneMove = game.handler.getChessTile(x, y + scale);
			if (nextmove != null) {
				if (!nextmove.isOccupied && !oneMove.isOccupied) {
					addMove(oneMove);
					addMove(nextmove);
				}
			}
			}
			
			nextmove = game.handler.getChessTile(x - scale, y + scale);
			if (nextmove != null) {
				if (nextmove.isOccupied) {
					if (!nextmove.carrier.team.equalsIgnoreCase(team)) {
						addMove(nextmove);
					}
				}
			}
			nextmove = game.handler.getChessTile(x + scale, y + scale);
			if (nextmove != null) {
				if (nextmove.isOccupied) {
					if (!nextmove.carrier.team.equalsIgnoreCase(team)) {
						addMove(nextmove);
					}
				}
			}
		
			
		} else {
			nextmove = game.handler.getChessTile(x, y - scale);
			if (nextmove != null) {
				if (!nextmove.isOccupied) {
					addMove(nextmove);
				}
			}
			
			// and if it is the first time we have used this pawn we can check this condition
			if (!hasMoved) {
				nextmove = game.handler.getChessTile(x, y - 2*scale);
				oneMove = game.handler.getChessTile(x, y - scale);
				if (nextmove != null) {
					if (!nextmove.isOccupied && !oneMove.isOccupied) {
						addMove(oneMove);
						addMove(nextmove);
					}
				}
			}
			
			nextmove = game.handler.getChessTile(x - scale, y - scale);
			if (nextmove != null) {
				if (nextmove.isOccupied) {
					if (!nextmove.carrier.team.equalsIgnoreCase(team)) {
						addMove(nextmove);
					}
				}
			}
			}
			nextmove = game.handler.getChessTile(x + scale, y - scale);
			if (nextmove != null) {
				if (nextmove.isOccupied) {
					if (!nextmove.carrier.team.equalsIgnoreCase(team)) {
						addMove(nextmove);
					}
				}
			}
	
		return moves;
	}

	public void setExtinct(boolean tf) {
		extinct = tf;
	}
	
}
