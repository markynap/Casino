package gameSetUps;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public abstract class ChessObject {

	
	public int x, y, scale;
	public String type, team;
	public Image img;
	public Game game;
	public boolean hasMoved;
	
	public int maxMove;
	public ArrayList<ChessTile> moves;
	
	public ChessObject(int x, int y, int scale, String type, String team, Game game) {
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.type = type;
		this.team = team;
		this.game = game;
		game.handler.addChessPiece(this);
		moves = new ArrayList<>();
	}
	
	public abstract void render(Graphics g);
	
	public abstract void tick();
	
	public abstract ArrayList<ChessTile> getMoves();
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void addMove(ChessTile t) {
		if (t!= null) {
			if (t.isOccupied) {
				if (!t.carrier.team.equalsIgnoreCase(team)) {
					moves.add(t);
				}
			} else {
				moves.add(t);
			}
		}
	}
	
	public void move(int x, int y) {
		game.chessPlayer.setKilledUnit(false);
		int xPlus = game.chessPlayer.xPlus;
		int yPlus = game.chessPlayer.yPlus;
		int oldX = this.x;
		int oldY = this.y;
		int oldEnX;
		int oldEnY;
		if (x == this.x && y == this.y) return;
		if (!game.cHUD.whoseTurn.toLowerCase().equalsIgnoreCase(team.toLowerCase())) return;
		ChessTile dest = game.handler.getChessTile(x, y);
		ChessTile current = game.handler.getChessTile(this.x, this.y);
		String s;
		if (dest == null) return;
		if (dest.selected) {
			
		s = type + " : " + (dest.x - xPlus)/game.chessPlayer.scale + "," + (dest.y - yPlus)/game.chessPlayer.scale;
		if (team.equalsIgnoreCase("White")) game.chessPlayer.addToPrevMoves1(s);
		else game.chessPlayer.addToPrevMoves2(s);
		this.x = x;
		this.y = y;
		if (type.equalsIgnoreCase("Pawn")) hasMoved = true;
		if (dest.isOccupied) {
			ChessObject obj = dest.carrier;
			oldEnX = obj.x;
			oldEnY = obj.y;
			game.chessPlayer.setOldUnitXY(oldEnX, oldEnY);
			game.chessPlayer.setUnitKilled(dest.carrier);
			game.chessPlayer.addDeadUnit(obj);
			game.handler.removeChessPiece(obj);
			game.chessPlayer.setKilledUnit(true);
			dest.setCarrier(null);
		}
		game.cHUD.flipWhoseTurn();
		game.chessPlayer.prevMove.clear();
		
		int[] move = {oldX, oldY};
		game.chessPlayer.prevMove.put(this, move);
		current.setCarrier(null);
		}
	}
	public void undoMove(int x, int y) {
		int oldX = this.x;
		int oldY = this.y;
		game.handler.getChessTile(oldX, oldY).setCarrier(null);
		this.x = x;
		this.y = y;
		if (game.chessPlayer.killedUnit) {
			game.chessPlayer.deadPieces.remove(game.chessPlayer.unitKilled);
			game.handler.addChessPiece(game.chessPlayer.unitKilled);
			game.chessPlayer.unitKilled.setX(game.chessPlayer.oldUnitXY[0]);
			game.chessPlayer.unitKilled.setY(game.chessPlayer.oldUnitXY[1]);
			game.cHUD.turnCountMinusTwo();
			ChessTile oldTile = game.handler.getChessTile(game.chessPlayer.oldUnitXY[0], game.chessPlayer.oldUnitXY[1]);
			oldTile.setCarrier(null);
			game.handler.getChessTile(x, y).setCarrier(null);
		}
	}
	public void setHasMoved(boolean tf) {
		hasMoved = tf;
	}
}
