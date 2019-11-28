package gameSetUps;
import java.awt.Graphics;
import java.util.LinkedList;

import checkers.*;
import connect4.*;

public class Handler {

	public LinkedList<ChessObject> chessPieces = new LinkedList<>();
	public LinkedList<ChessTile> chessTiles = new LinkedList<>();
	public LinkedList<Coin> coins = new LinkedList<>();
	public LinkedList<CheckerPiece> checkerPieces = new LinkedList<>();
	public LinkedList<CheckerTile> checkerTiles = new LinkedList<>();
	public ChessPlayer chessPlayer;
	public Game game;
	
	public Handler(Game game) {
		this.game = game;
	}
	
	public void render(Graphics g) {
		
		
		
	}
	
	public void renderChess(Graphics g) {
		if (chessPlayer == null) {
			chessPlayer = game.getChessPlayer();
		}
		for (int i = 0; i < chessTiles.size(); i++) {
			ChessTile t = chessTiles.get(i);
			t.render(g);
		}
		for (int i = 0; i < chessPieces.size(); i++) {
			ChessObject p = chessPieces.get(i);
			p.render(g);
		}
		chessPlayer.drawSelected(g);
	}
	
	public void tick() {
		
		for (int i = 0; i < chessTiles.size(); i++) {
			ChessTile t = chessTiles.get(i);
			t.tick();
		}
		for (int i = 0; i < chessPieces.size(); i++) {
			ChessObject t = chessPieces.get(i);
			t.tick();
		}
	}
	
	
	public void renderConnectFour(Graphics g) {
		for (int i = 0; i < coins.size(); i++) {
			Coin c = coins.get(i);
			c.render(g);
		}
	}
	
	public CheckerPiece getCheckerPiece(int x, int y) {
		for (int i = 0; i < checkerPieces.size(); i++) {
			CheckerPiece c = checkerPieces.get(i);
			if (x == c.x && y == c.y) {
				return c;
			}
		}
		return null;
	}
	public CheckerTile getCheckerTile(int x, int y) {
		for (int i = 0; i < checkerTiles.size(); i++) {
			CheckerTile c = checkerTiles.get(i);
			if (x == c.x && y == c.y) {
				return c;
			}
		}
		return null;
	}
	
	public ChessObject getChessPiece(int x, int y) {
		for (ChessObject t : chessPieces) {
			if (t.x == x && t.y == y) {
				return t;
			}
		}
		return null;
	}
	
	public ChessTile getChessTile(int x, int y) {
		for (ChessTile t : chessTiles) {
			if (t.x == x && t.y == y) {
				return t;
			}
		}
		return null;
	}
	
	public void addChessPiece(ChessObject p) {
		chessPieces.add(p);
	}
	public void removeChessPiece(ChessObject p) {
		chessPieces.remove(p);
	}
	public void addChessTile(ChessTile p) {
		chessTiles.add(p);
	}
	public void resetSelected() {
		for (int i = 0; i < chessTiles.size(); i++) {
			ChessTile t = chessTiles.get(i);
			t.setSelected(false);
		}
	}
	public void addCoin(Coin c) {
		coins.add(c);
	}
	public void removeCoin(Coin c) {
		coins.remove(c);
	}
	public void addCheckerTile(CheckerTile t) {
		checkerTiles.add(t);
	}
	public void addCheckerPiece(CheckerPiece p) {
		checkerPieces.add(p);
	}
	public void removeCheckerTile(CheckerTile t) {
		checkerTiles.remove(t);
	}
	public void removeCheckerPiece(CheckerPiece p) {
		checkerPieces.remove(p);
	}
	
}
