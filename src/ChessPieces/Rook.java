package ChessPieces;

import java.awt.Graphics;
import java.util.ArrayList;

import gameSetUps.*;

public class Rook extends ChessObject{

	public Rook(int x, int y, int scale, String type, String team, Game game) {
		super(x, y, scale, type, team, game);
		if (team.equalsIgnoreCase("White")) {
			img = Game.IM.getImage("/allyrook.png");
		} else if (team.equalsIgnoreCase("Black")){
			img = Game.IM.getImage("/enemyrook.png");
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(img, x, y, scale, scale, null);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<ChessTile> getMoves() {
		
		int scale = Game.WIDTH / 12;
		int xPlus = 152;
		int yPlus = 26;
		ChessTile nextmove;
		ChessTile current = game.handler.getChessTile(x, y);
		int enemyinpath = 0;
		moves.clear();
		for (int i = ((x - xPlus)/scale); i < 8; i++) {//This loops from the players current X to the far right of the board
			nextmove = game.handler.getChessTile(i * scale + xPlus, y);
			if (nextmove != null) {
				if (nextmove == current) continue;
				if (nextmove.isOccupied) {
					if (nextmove.carrier.team.equalsIgnoreCase(team) && nextmove != current) {
						break;
					} else if (!nextmove.carrier.team.equalsIgnoreCase(team) && nextmove != current) {
						if (enemyinpath > 0) {
							enemyinpath = 0;
							break;
						}
						enemyinpath++;
					}
				} else {
					if (enemyinpath > 0) {
						enemyinpath = 0;
						break;
					}
				}
			}
			addMove(nextmove);
		}
		enemyinpath = 0;
		for (int i = ((x - xPlus)/scale); i >= 0; i--) {//This loops from the players current X to the left side of the board
			nextmove = game.handler.getChessTile(i * scale + xPlus, y);
			if (nextmove != null) {
				if (nextmove == current) continue;
				if (nextmove.isOccupied) {
					if (nextmove.carrier.team.equalsIgnoreCase(team) && nextmove != current) {
						break;
					} else if (!nextmove.carrier.team.equalsIgnoreCase(team) && nextmove != current) {
						if (enemyinpath > 0) {
							enemyinpath = 0;
							break;
						}
						enemyinpath++;
					}
				} else {
					if (enemyinpath > 0) {
						enemyinpath = 0;
						break;
					}
				}
			}
			addMove(nextmove);
		}
		enemyinpath = 0;
		for (int i = ((y - yPlus)/scale); i < 8; i++) {//This loops from players current Y to bottom of board
			nextmove = game.handler.getChessTile(x, i * scale + yPlus);
			if (nextmove != null) {
				if (nextmove == current) continue;
				if (nextmove.isOccupied) {
					if (nextmove.carrier.team.equalsIgnoreCase(team)) {
						break;
					} else if (!nextmove.carrier.team.equalsIgnoreCase(team)) {
						if (enemyinpath > 0) {
							enemyinpath = 0;
							break;
						}
						enemyinpath++;
					}
				} else {
					if (enemyinpath > 0) {
						enemyinpath = 0;
						break;
					}
				}
			}
			addMove(nextmove);
		}
		enemyinpath = 0;
		for (int i = ((y - yPlus)/scale); i >= 0; i--) {//This loops from players current Y to top of board
			nextmove = game.handler.getChessTile(x, i * scale + yPlus);
			if (nextmove != null) {
				if (nextmove == current) continue;
				if (nextmove.isOccupied) {
					if (nextmove.carrier.team.equalsIgnoreCase(team) && nextmove != current) {
						break;
					} else if (!nextmove.carrier.team.equalsIgnoreCase(team) && nextmove != current) {
						if (enemyinpath > 0) {
							enemyinpath = 0;
							break;
						}
						enemyinpath++;
					}
				} else {
					if (enemyinpath > 0) {
						enemyinpath = 0;
						break;
					}
				}
			}
			addMove(nextmove);
		}
		
		
		return moves;
	}
}
