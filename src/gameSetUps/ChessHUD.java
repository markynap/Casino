package gameSetUps;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import ChessPieces.Pawn;
import ChessPieces.*;
import gameSetUps.Game.STATE;

public class ChessHUD {

	public Game game;
	public Handler handler;
	public long time;
	public int mins;
	public String whoseTurn;
	public int turnCount;
	public String rebirthTeam;
	public int selX, selY;
	public int rows, cols;
	public ChessObject rebirthedUnit;
	public ArrayList<ChessObject> myDead;
	public Pawn rebirthedPawn;
	
	public ChessHUD(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
		time = System.currentTimeMillis();
		whoseTurn = "WHITE";
		rows = 4;
		cols = 4;
		rebirthedUnit = null;
		myDead = new ArrayList<>();
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		g.setColor(Color.white);
		g.drawString("Turn: ", 15, 40);
		g.drawString(whoseTurn, 15, 65);
		
		g.drawString("Move: " + turnCount, 15, 90);
		long time2 = ((System.currentTimeMillis() - time) / 1000);
		if (time2 > 59) {
			time = System.currentTimeMillis();
			time2 = time2 % 60;
			mins++;
		}
		g.drawString("Time: " + mins + " : " + time2, 15, 120);
		
		if (game.getGameState() == STATE.RebirthState) {
			
			setMyDead(rebirthTeam);
			renderRebirth(g, rebirthTeam);
		}
	}
	
	public void setMyDead(String team) {
		for (int i = 0; i < game.chessPlayer.deadPieces.size(); i++) {
			ChessObject c = game.chessPlayer.deadPieces.get(i);
			if (c.team.equalsIgnoreCase(team)) {
				if (!myDead.contains(c)) myDead.add(c);
			}
		}
	}
	
	public void renderRebirth(Graphics g, String team) {
		
		int size = myDead.size();
		
		int scale = 30;
		int yStart = 30;
		int xStart = Game.WIDTH - 180;
		
		g.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				g.fillRect(xStart + (j * scale), yStart + (i * scale), scale-1, scale-1);
				if (((i * cols) + j) < size) {
					g.drawImage(myDead.get(((i * cols) + j)).img, xStart + (j * scale), yStart + (i * scale), scale-1, scale-1, null);
				}
			}
		}
		
		drawSelected(g, selX, selY, xStart, yStart, scale, Color.blue);
	}
	
	public void drawSelected(Graphics g, int selX, int selY, int xPlus, int yPlus, int scale, Color color) {
		int thickness = 5;
		
		g.setColor(color);
		
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
	
	public void flipWhoseTurn() {
		if (whoseTurn.equals("WHITE")) {
			whoseTurn = "BLACK";
		} else {
			whoseTurn = "WHITE";
		}
		turnCount++;
	}
	
	public void setRebirthTeam(String s) {
		rebirthTeam = s;
	}
	public void incrementSelX() {
		if (selX < cols-1) {
			selX++;
		}
	}
	public void decrementSelX() {
		if (selX > 0) {
			selX--;
		}
	}
	public void incrementSelY() {
		if (selY < rows-1) {
			selY++;
		}
	}
	public void decrementSelY() {
		if (selY > 0) {
			selY--;
		}
	}
	public void setRebirthedUnit(ChessObject o) {
		rebirthedUnit = o;
		
	}
	public void placeRebirthedUnit() {
	//	rebirthedUnit.setX(rebirthedPawn.x);
	//	rebirthedUnit.setY(rebirthedPawn.y);
		game.chessPlayer.deadPieces.remove(rebirthedUnit);
		//handler.addChessPiece(rebirthedUnit);

		if (rebirthedUnit.type.equalsIgnoreCase("Rook")) {
			new Rook(rebirthedPawn.x, rebirthedPawn.y, game.chessPlayer.scale, "Rook", rebirthTeam, game);
		} else if (rebirthedUnit.type.equalsIgnoreCase("Bishop")) {
			new Bishop(rebirthedPawn.x, rebirthedPawn.y, game.chessPlayer.scale, "Bishop", rebirthTeam, game);
		} else if (rebirthedUnit.type.equalsIgnoreCase("Knight")) {
			new Knight(rebirthedPawn.x, rebirthedPawn.y, game.chessPlayer.scale, "Knight", rebirthTeam, game);
		} else if (rebirthedUnit.type.equalsIgnoreCase("Queen")) {
			new Queen(rebirthedPawn.x, rebirthedPawn.y, game.chessPlayer.scale, "Queen", rebirthTeam, game);
		} else if (rebirthedUnit.type.equalsIgnoreCase("Pawn")) {
			new Pawn(rebirthedPawn.x, rebirthedPawn.y, game.chessPlayer.scale, "Pawn", rebirthTeam, game);
		}
		
		rebirthedPawn.setExtinct(true);
		ChessTile oldTile = handler.getChessTile(rebirthedPawn.x, rebirthedPawn.y);
		handler.removeChessPiece(rebirthedPawn);
		oldTile.setCarrier(null);
		game.setGameState(STATE.Chess);
	}
	public void setRebirthedPawn(Pawn p) {
		this.rebirthedPawn = p;
	}
	public void turnCountMinusTwo() {
		turnCount -= 2;
	}
}
