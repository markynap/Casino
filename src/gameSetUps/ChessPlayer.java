package gameSetUps;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import ChessPieces.*;

public class ChessPlayer {

	public Game game;
	public Handler handler;
	public ChessTile[][] chessBoard;
	public int selX, selY;
	public ChessTile selectedTile;
	public ArrayList<ChessTile> moves;
	public int scale = Game.WIDTH / 12;
	public int xPlus = 152;
	public int yPlus = 26;
	public ArrayList<ChessObject> deadPieces;
	public ArrayList<String> prevMoves1;
	public ArrayList<String> prevMoves2;
	public int[] uP = {Game.WIDTH - 180, Game.HEIGHT - 80, 70, 45};
	public HashMap<ChessObject, int[]> prevMove;
	public boolean killedUnit;
	public int[] oldUnitXY; //Responsible for undoing moves and respawning units
	public ChessObject unitKilled;
	public Image bgimg;
	
	public ChessPlayer(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;	
		chessBoard = new ChessTile[8][8];
		constructGame();
		moves = new ArrayList<>();
		deadPieces = new ArrayList<>();
		prevMoves1 = new ArrayList<>();
		prevMoves2 = new ArrayList<>();
		prevMove = new HashMap<>();
		oldUnitXY = new int[2];
		bgimg = Game.IM.getImage("/chessjgbg.jpg");
	}
	
	
	public void render(Graphics g) {
		
		g.drawImage(bgimg, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		drawGraph(g);
		renderPreviousMoves(g);
		renderDeadImages(g);
		renderUndoBox(g);
		//Have data to keep track of average time to make an attack or choose a move
		//Also have a 'bank' of defeated enemies for both sides
	}
	public void undo() {
		ArrayList<ChessObject> obj = new ArrayList<>();
		if (!prevMove.isEmpty()) {
			obj = new ArrayList<>();
			obj.addAll(prevMove.keySet());
			ChessObject object = obj.get(0);
			int x = prevMove.get(object)[0];
			int y = prevMove.get(object)[1];
			if (object.type.equalsIgnoreCase("Pawn")) {
			//	if (object.hasMoved) object.setHasMoved(true);
				//Add code to make the pawn able to move 2 again if the undo replaced its move
			}
			object.undoMove(x, y);
			game.cHUD.flipWhoseTurn();
		}
		
		prevMove.clear();
	}
	public void renderUndoBox(Graphics g) {
		int thickness = 5;
		g.setColor(Color.black);
		for (int i = 0; i < thickness; i++) {
			g.drawRect(uP[0] + i, uP[1] + i, uP[2], uP[3]);
		}
		g.setColor(Color.white);
		g.setFont(new Font("Times New Roman", Font.BOLD, 18));
		g.fillRect(uP[0] + thickness, uP[1] + thickness, uP[2] - thickness, uP[3] - thickness);
		g.setColor(Color.black);
		g.drawString("UNDO", uP[0] + uP[2]/3 - 12, uP[1] + uP[3]/2 + 8);
	}
	
	public void renderDeadImages(Graphics g) {
		
		int xDist = 5;
		int yDist = Game.HEIGHT - 280;
		int yDist2 = yDist;
		int size = 60;
		int secxDist = Game.WIDTH - 175;
		
		int inc = 0;
		int inc1 = 0;
		for (int i = 0; i < deadPieces.size(); i++) {
			ChessObject o = deadPieces.get(i);
		if (o.team.equalsIgnoreCase("Black"))	{
			g.drawImage(o.img, xDist + (40*inc), yDist, size, size ,null);
			inc++;
			if (inc == 3) {
				yDist += 60;
				inc = 0;
			}
		} else {
			g.drawImage(o.img, secxDist + (40*inc1), yDist2, size, size ,null);
			inc1++;
			if (inc1 == 3) {
				yDist2 += 60;
				inc1 = 0;
			}
		}
			
			
		}
		
		
		
	}
	
	public void renderPreviousMoves(Graphics g) {
		
		int xDist = 10;
		int yDist = 150;
		int width = 125;
		int height = 300;
		
		int surrenderBoxY = yDist + height - 30;
		int secxDist = Game.WIDTH - 150;
		g.setColor(Color.red);
		g.fillRect(xDist, surrenderBoxY, width, 30);
		g.fillRect(secxDist, surrenderBoxY, width, 30);
		g.setColor(Color.black);
		g.setFont(new Font("Times New Roman", Font.BOLD, 22));
		g.drawString("Surrender", xDist + 15, surrenderBoxY + 20);
		g.drawString("Surrender", secxDist + 15, surrenderBoxY + 20);
		
		
		g.setColor(Color.white);
		g.drawRect(xDist, yDist, width, height);
		g.drawRect(secxDist, yDist, width, height);
		g.setFont(new Font("Times New Roman", Font.BOLD, 22));
		int maxHistory = 8;
		int start1 = Math.max(0, prevMoves1.size() - maxHistory);
		int start2 = Math.max(0, prevMoves2.size() - maxHistory);
		int inc = 0;
		for (int i = start1; i < prevMoves1.size(); i++) {
			String a = prevMoves1.get(i);
			g.drawString(a, xDist + 2, yDist + (inc * 24) + 26);
			inc++;
		}
		inc = 0;
		for (int i = start2; i < prevMoves2.size(); i++) {
			String a = prevMoves2.get(i);
			g.drawString(a, secxDist + 2, yDist + (inc * 24) + 26);
			inc++;
		}
		
	}
	
	public void drawGraph(Graphics g) {
		g.setColor(Color.white);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				g.drawRect(i*scale + xPlus, j * scale + yPlus, scale-1, scale-1);
			}
		}
	}
	
	public void drawSelected(Graphics g) {
		int thickness = 5;
		
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
	
	public void constructGame() {
		int rows = 8;
		int cols = 8;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				ChessTile t = new ChessTile(i*scale + 152, j*scale + 26, scale-1, (i % 2) + (j % 2), game);
				chessBoard[i][j] = t;
			}
		}
		
		Rook r = new Rook(152, 26, scale-10, "Rook", "White", game);
		Rook r2 = new Rook(152, scale *7 + 26, scale-10, "Rook", "Black", game);
		Rook er = new Rook(152 + scale*7, 26, scale-10, "Rook", "White", game);
		Rook er2 = new Rook(152 + scale*7, scale * 7 + 26, scale-10, "Rook", "Black", game);
		Knight k = new Knight(scale + 152, 26, scale-10, "Knight", "White", game);
		Knight k2 = new Knight(scale*6 + 152, 26, scale-10, "Knight", "White", game);
		Knight kn = new Knight(scale + 152, scale * 7 + 26, scale-10, "Knight", "Black", game);
		Knight kn2 = new Knight(scale*6 + 152, scale * 7 + 26, scale-10, "Knight", "Black", game);
		Bishop b = new Bishop(scale*2 + 152, 26, scale-10, "Bishop", "White", game);
		Bishop b2 = new Bishop(scale*5 + 152, 26, scale-10, "Bishop", "White", game);
		Bishop bi = new Bishop(scale*2 + 152, scale*7 + 26, scale-10, "Bishop", "Black", game);
		Bishop bi2 = new Bishop(scale*5 + 152, scale*7 + 26, scale-10, "Bishop", "Black", game);	
		King ki = new King(scale * 4 + 152, 26, scale-10, "King", "White", game);
		King k2i = new King(scale * 4 + 152, scale * 7 + 26, scale-10, "King", "Black", game);	
		Queen q = new Queen(scale * 3 + 152, 26, scale-10, "Queen", "White", game);
		Queen q2 = new Queen(scale * 3 + 152, scale * 7 + 26, scale-10, "Queen", "Black", game);
		
		for (int i = 0; i < rows; i++) {
			Pawn p = new Pawn(scale * i + 152, scale + 26, scale-10, "Pawn", "White", game);
			Pawn p2 = new Pawn(scale * i + 152, scale*6 + 26, scale-10, "Pawn", "Black", game);
		}
		
	}
	
	public void incrementSelX() {
		if (selX < 7) {
			selX++;
		}
	}
	public void decrementSelX() {
		if (selX > 0) {
			selX--;
		}
	}
	public void incrementSelY() {
		if (selY < 7) {
			selY++;
		}
	}
	public void decrementSelY() {
		if (selY > 0) {
			selY--;
		}
	}
	public void setSelectedTile(ChessTile t) {
		if (t != null) {
		handler.resetSelected();
		t.setSelected(true);
		selectedTile = t;
		if (t.isOccupied) {
			moves = t.carrier.getMoves();
			for (ChessTile tile : moves) {
			if (tile != null)	tile.setSelected(true);
			}
		}
		
		}
	}
	public void addDeadUnit(ChessObject o) {
		deadPieces.add(o);
	}
	public void removeDeadUnit(ChessObject o) {
		deadPieces.remove(o);
	}
	public void addToPrevMoves1(String s) {
		prevMoves1.add(s);
	}
	public void addToPrevMoves2(String s) {
		prevMoves2.add(s);
	}
	public void setKilledUnit(boolean tf) {
		killedUnit = tf;
	}
	public void setOldUnitXY(int x, int y) {
		oldUnitXY[0] = x;
		oldUnitXY[1] = y;
	}
	public void setUnitKilled(ChessObject o) {
		unitKilled = o;
	}
}
