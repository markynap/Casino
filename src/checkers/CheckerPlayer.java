package checkers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;

import gameSetUps.*;
import gameSetUps.Game.STATE;

public class CheckerPlayer {

	public Game game;
	public Handler handler;
	public ImageManager IM;
	public int rows, cols;
	public int scale = 80;
	public int xDist, yDist;
	public Image img;
	public int selX, selY;
	public CheckerPiece selectedPiece;
	public CheckerTile doubleLeft;
	public CheckerTile doubleRight;
	public String whoseTurn;
	public HashMap<CheckerTile, CheckerPiece> killList;
	
	public CheckerPlayer(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
		this.IM = Game.IM;
		rows = 8;
		cols = 8;
		xDist = 150;
		yDist = 60;
		img = Game.IM.getImage("/checkerbg.jpg");
		makeBoard();
		addPieces();
		selectedPiece = null;
		doubleLeft = null;
		doubleRight = null;
		whoseTurn = "Red";
		killList = new HashMap<>();
	}
	
	public void render(Graphics g) {

		g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		drawTurn(g);
		renderPieces(g);
		drawSelected(g);
	}
	
	public void tick() {
		for (int i = 0; i < handler.checkerTiles.size(); i++) {
			CheckerTile t = handler.checkerTiles.get(i);
			t.tick();
		}
		
	}
	
	public void drawTurn(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Times New Roman", Font.BOLD, 35));
		g.drawString("Move: ", Game.WIDTH/3 + 25, 40);
		if (whoseTurn.equalsIgnoreCase("Red")) g.setColor(Color.red);
		else g.setColor(Color.black);
		g.drawString(whoseTurn, Game.WIDTH/3 + 125, 40);
	}
	
	public void addPieces() {
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < cols; j++) {
				if (i % 2 == 0) {
					if (j % 2 == 0) {
						new CheckerPiece(j, i, scale, game, handler, xDist, yDist, "Red");
					}
				} else {
					if (j % 2 == 1) {
						new CheckerPiece(j, i, scale, game, handler, xDist, yDist, "Red");
					}
				}
			}
		}
		for (int i = 5; i < 8; i++) {
			for (int j = 0; j < cols; j++) {
				if (i % 2 == 1) {
					if (j % 2 == 1) {
						new CheckerPiece(j, i, scale, game, handler, xDist, yDist, "Black");
					}
				} else {
					if (j % 2 == 0) {
						new CheckerPiece(j, i, scale, game, handler, xDist, yDist, "Black");
					}
				}
			}
		}
		
		
	}
	
	public void swapGameStates() {
		if (game.getGameState() == STATE.Checkers) {
			game.setGameState(STATE.CheckersMove);
		} else {
			game.setGameState(STATE.Checkers);
		}
	}
	public void setSelectedPiece(CheckerPiece p) {
		selectedPiece = p;
	}
	
	
	public void makeBoard() {
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				new CheckerTile(i, j, scale, game, handler, xDist, yDist, (i % 2) + (j % 2));		
			}
		}
	}
	public void renderPieces(Graphics g) {
		
		for (int i = 0; i < handler.checkerTiles.size(); i++) {
			CheckerTile c = handler.checkerTiles.get(i);
			c.render(g);
		}
		for (int i = 0; i < handler.checkerPieces.size(); i++) {
			CheckerPiece c = handler.checkerPieces.get(i);
			c.render(g);
		}
	}
	
	public void drawSelected(Graphics g) {
		int thickness = 5;
		int xPlus = xDist;
		int yPlus = yDist;
		g.setColor(Color.WHITE);
		
		for (int i = 0; i < thickness; i++) {
			g.drawLine(selX*scale  + xPlus, selY*scale+i+ yPlus , selX*scale + scale/4  + xPlus, selY*scale+i+ yPlus ); // -
			g.drawLine(selX*scale+i  + xPlus, selY*scale+ yPlus , selX*scale+i  + xPlus, selY*scale+scale/4+ yPlus );  //  |
			
			g.drawLine(selX*scale+i  + xPlus, selY*scale+scale+ yPlus , selX*scale+i  + xPlus, selY*scale+3*scale/4+ yPlus ); //  |
			g.drawLine(selX*scale + xPlus, selY*scale+scale-i+ yPlus , selX*scale+scale/4 + xPlus, selY*scale+scale-i+ yPlus );  // -
			
			g.drawLine(selX*scale+scale-i + xPlus, selY*scale+ yPlus , selX*scale+scale-i + xPlus, selY*scale+scale/4+ yPlus ); //  |
			g.drawLine(selX*scale+scale + xPlus, selY*scale+i+ yPlus , selX*scale+3*scale/4 + xPlus, selY*scale+i+ yPlus );  // -
			
			g.drawLine(selX*scale+scale-i + xPlus, selY*scale+scale+ yPlus , selX*scale+scale-i + xPlus, selY*scale+3*scale/4+ yPlus ); //  |
			g.drawLine(selX*scale+scale + xPlus, selY*scale+scale-i+ yPlus , selX*scale+3*scale/4 + xPlus, selY*scale+scale-i+ yPlus );  // -
		}
	}
	
	public void incrementSelX() {
		selX++;
		if (selX > cols-1) {
			selX = 0;
		}
	}
	public void incrementSelY() {
		selY++;
		if (selY > rows-1) {
			selY = 0;
		}
	}
	public void decrementSelX() {
		selX--;
		if (selX < 0) {
			selX = cols-1;
		}
	}
	public void decrementSelY() {
		selY--;
		if (selY < 0) {
			selY = rows-1;
		}
	}
	
	public void flipSelected(CheckerTile t) {
		if (t.selected) {
			t.setSelected(false);
		} else {
			t.setSelected(true);
		}
	}
	public void resetSelected() {
		for (int i = 0; i < handler.checkerTiles.size(); i++) {
			CheckerTile c = handler.checkerTiles.get(i);
			c.setSelected(false);
		}
		selectedPiece = null;
	}
	public void generateMoves(CheckerPiece piece, int dir) {
		killList.clear();
		if (piece == null) return;
		int xPos = piece.x;
		int yPos = piece.y;
		CheckerTile leftTile = handler.getCheckerTile(xPos - 1, yPos + dir);
		CheckerTile rightTile = handler.getCheckerTile(xPos + 1, yPos + dir);
		CheckerPiece enemyPiece = null;
			
		select(leftTile);
		select(rightTile);
		selectJumpRight(piece, rightTile, dir);
		selectJumpLeft(piece, leftTile, dir);
		
	}
	
	public void generateKingMoves(CheckerPiece piece, int dir) {
		if (piece == null) return;
		int xPos = piece.x;
		int yPos = piece.y;
		CheckerTile leftTile = handler.getCheckerTile(xPos - 1, yPos + dir);
		CheckerTile rightTile = handler.getCheckerTile(xPos + 1, yPos + dir);
		CheckerPiece enemyPiece = null;
			
		select(leftTile);
		select(rightTile);
		selectJumpRight(piece, rightTile, dir);
		selectJumpLeft(piece, leftTile, dir);
	}
	
	public void select(CheckerTile t) {
		if (t != null) {
			if (!t.isOccupied) {
				t.setSelected(true);
			}
		}
	}
	
	public void selectJumpRight(CheckerPiece piece, CheckerTile t, int dir) {
		doubleRight = null;
		if (t == null) return;
		int xPos = piece.x;
		int yPos = piece.y;
		if (t.isOccupied) {
			if (!t.currentPiece.team.equalsIgnoreCase(piece.team)) {
				CheckerTile secRightTile = handler.getCheckerTile(xPos + 2, yPos + (2*dir));
				select(secRightTile);
				if (secRightTile != null) {
					killList.put(secRightTile, t.currentPiece);
					doubleRight = secRightTile;
				}
			}
		}
	}
	public void selectJumpLeft(CheckerPiece piece, CheckerTile t, int dir) {
		doubleLeft = null;
		if (t == null) return;
		int xPos = piece.x;
		int yPos = piece.y;

		if (t.isOccupied) {
			if (!t.currentPiece.team.equalsIgnoreCase(piece.team)) {
				CheckerTile secLeftTile = handler.getCheckerTile(xPos - 2, yPos + (2*dir));
				select(secLeftTile);
				if (secLeftTile != null) {
					killList.put(secLeftTile, t.currentPiece);
					doubleLeft = secLeftTile;
				}
			}
		}
		
		
	}
	
	public void nextTurn() {
		if (whoseTurn.equalsIgnoreCase("Red")) {
			whoseTurn = "Black";
		} else {
			whoseTurn = "Red";
		}
	}
}
