package checkers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import gameSetUps.*;

public class CheckerPiece {

	public int x, y, scale;
	public int xDist, yDist;
	public boolean selected;
	public Game game;
	public Handler handler;
	public String team;
	public Image redimg;
	public Image blackimg;
	public Color gold = new Color(255, 215, 0);
	public boolean isKing;
	public boolean canDouble;
	public int dir;

	public CheckerPiece(int x, int y, int scale, Game game, Handler handler, int xDist, int yDist, String team) {
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.game = game;
		this.handler = handler;
		this.team = team;
		this.xDist = xDist;
		this.yDist = yDist;
		handler.addCheckerPiece(this);
		redimg = Game.IM.getImage("/redchecker.png");
		blackimg = Game.IM.getImage("/blackchecker.png");
		if (team.equalsIgnoreCase("Red"))
			dir = 1;
		else
			dir = -1;
	}

	public void move(int destX, int destY) {
		canDouble = false;
		if (game.checkPlayer.whoseTurn.equalsIgnoreCase(team)) {

			CheckerTile destTile = handler.getCheckerTile(destX, destY);
			CheckerTile currentTile = handler.getCheckerTile(x, y);
			if (destTile == currentTile)
				return;
			currentTile.resetCurrentPiece();
			if (!game.checkPlayer.killList.isEmpty()) {
				CheckerPiece enemy = game.checkPlayer.killList.get(destTile);
				if (enemy != null) {
					int enX = enemy.x;
					int enY = enemy.y;
					handler.removeCheckerPiece(enemy);
					handler.getCheckerTile(enX, enY).resetCurrentPiece();
				}
				if (isKing) {
					checkCanDouble(dir);
					if (!canDouble)
						checkCanDouble(-1 * dir);
				} else {
					checkCanDouble(dir);
				}

			}
			this.x = destX;
			this.y = destY;
			currentTile.resetCurrentPiece();
			destTile.resetCurrentPiece();
			// This is where we check for double, before we call nextTurn
			if (canDouble) {
				// game.checkPlayer.flipSelected(handler.getCheckerTile(destX, destY));
				// game.checkPlayer.setSelectedPiece(this);
			} else {
				game.checkPlayer.nextTurn();
			}

		}

	}

	public void checkCanDouble(int dir) {

		canDouble = false;

		CheckerTile leftTile = handler.getCheckerTile(x - 4, y + (4 * dir));
		CheckerTile rightTile = handler.getCheckerTile(x + 4, y + (4 * dir));
		CheckerTile midTile = handler.getCheckerTile(x, y + (4 * dir));

		CheckerTile midwayL = handler.getCheckerTile(x - 3, y + (3 * dir));
		CheckerTile midwayLL = handler.getCheckerTile(x - 1, y + (3 * dir));

		CheckerTile midwayR = handler.getCheckerTile(x + 3, y + (3 * dir));
		CheckerTile midwayRR = handler.getCheckerTile(x + 1, y + (3 * dir));

		CheckerTile midwayM = handler.getCheckerTile(x, y + (3 * dir));
		// we also need to check if there is an opponent in the midway

		if (leftTile != null) {
			if (!leftTile.isOccupied) {
				if (midwayL.isOccupied) {
					if (!midwayL.currentPiece.team.equalsIgnoreCase(team)) {
						canDouble = true;
						return;
					}
				}
			}
		}
		if (rightTile != null) {
			if (!rightTile.isOccupied) {
				if (midwayR.isOccupied) {
					if (!midwayR.currentPiece.team.equalsIgnoreCase(team)) {
						canDouble = true;
						return;
					}
				}
			}
		}
		if (midTile != null) {
			if (!midTile.isOccupied) {
				if (midwayM != null) {
					if (midwayM.isOccupied) {
						if (!midwayM.currentPiece.team.equalsIgnoreCase(team)) {
							canDouble = true;
							return;
						}
					}
				}
				if (midwayLL != null) {
					if (midwayLL.isOccupied) {
						if (!midwayLL.currentPiece.team.equalsIgnoreCase(team)) {
							canDouble = true;
							return;
						}
					}
				}
				if (midwayRR != null) {
					if (midwayRR.isOccupied) {
						if (!midwayRR.currentPiece.team.equalsIgnoreCase(team)) {
							canDouble = true;
							return;
						}
					}
				}
			}
		}
	}

	public void render(Graphics g) {

		if (team.equalsIgnoreCase("Red")) {
			g.drawImage(redimg, xDist + x * scale, yDist + y * scale, scale - 1, scale - 1, null);
			if (isKing) {
				g.setColor(gold);
				g.setFont(new Font("Times New Roman", Font.BOLD, 26));
				g.drawString("K", xDist + x * scale + scale / 3 + 1, yDist + y * scale + scale / 2 + 3);
			}
		} else {
			g.drawImage(blackimg, xDist + x * scale, yDist + y * scale, scale - 1, scale - 1, null);
			if (isKing) {
				g.setColor(gold);
				g.setFont(new Font("Times New Roman", Font.BOLD, 26));
				g.drawString("K", xDist + x * scale + scale / 3 + 1, yDist + y * scale + scale / 2 + 3);
			}
		}
		tick();
	}

	public void tick() {
		if (dir < 0) { // if we're on the black team
			if (y == 0)
				isKing = true;
		} else {
			if (y == 7)
				isKing = true;
		}
	}

	public void setSelected(boolean tf) {
		selected = tf;
	}
}
