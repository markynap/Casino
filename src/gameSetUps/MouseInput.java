package gameSetUps;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import gameSetUps.Game.STATE;

public class MouseInput extends MouseAdapter{

	public Handler handler;
	public Game game;
	
	public MouseInput(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
	}
	
	public void mousePressed(MouseEvent e) {
		
		Point p = e.getPoint();
		int yPos = (int)p.getY();
		int xPos = (int)p.getX();
		
		if (game.getGameState() == STATE.Menu) {
			
		/*	int yPos = (int)p.getY();
			int xPos = (int)p.getX();
			
			int yRange = 50;
			int xSpot = 125;
			int boxLength = game.menu.boxLength;
			
			int chessGameY = game.menu.chessBoxY;
			
			int connectGameY = game.menu.connectBoxY;
			int connectGameX = game.menu.connectBoxX;
			
			if ((yPos > chessGameY && yPos < chessGameY + yRange) && (xPos > xSpot && xPos < xSpot + boxLength)) {
				game.setGameState(STATE.Chess);
			} else if ((yPos > connectGameY && yPos < connectGameY + yRange) && (xPos > connectGameX && xPos < connectGameX + boxLength)) {
				game.setGameState(STATE.ConnectMenu);
			}*/
			
		} else if (game.getGameState() == STATE.Chess) {
			int undoX = game.chessPlayer.uP[0] + 5;
			int undoY = game.chessPlayer.uP[1] + 5;
			int xRange = game.chessPlayer.uP[2];
			int yRange = game.chessPlayer.uP[3];
			if ((xPos > undoX && xPos < undoX + xRange) && (yPos > undoY && yPos < undoY + yRange)) {
				//System.out.println("UNDO");
				game.chessPlayer.undo();
			}
			
			
			
			
		}else if (game.getGameState() == STATE.ConnectMenu) {		
			
			int yRange = game.connectMenu.nameBarHeight;
			int xRange = game.connectMenu.nameBarWidth;
			int barx = game.connectMenu.nameBarX;
			int bary = game.connectMenu.nameBarY;
			int barx2 = game.connectMenu.nameBarX2;
			boolean inYRange = false;
			
			if (yPos > bary && yPos < bary + yRange) {
				inYRange = true;
			}
			
			if (inYRange && (xPos > barx && xPos < barx + xRange)) {
				game.connectMenu.setOnBoxOne();
			} else if (inYRange && (xPos > barx2 && xPos < barx2 + xRange)) {
				game.connectMenu.setOnBoxTwo();
			}
			
			if (game.connectMenu.readyToStart) {
				
				
				
				int xStart = game.connectMenu.enterX;
				int yStart = game.connectMenu.enterY;
				int boxWidth = game.connectMenu.enterWidth;
				int boxHeight = game.connectMenu.enterHeight;
				
				if ((xPos > xStart && xPos < xStart + boxWidth) && (yPos > yStart && yPos < yStart + boxHeight)) {
					game.c4p.setNames(game.connectMenu.name1, game.connectMenu.name2);
					game.setGameState(STATE.Connect);
				}
				
				
			}
			
		} else if (game.getGameState() == STATE.BlackJackMenu) {
			
			int xDist = game.BJP.BJM.addX;
			int yDist = game.BJP.BJM.addY;
			int xxRange = game.BJP.BJM.addW;
			int yyRange = game.BJP.BJM.addH;
			
			int yDist2 = game.BJP.BJM.startY;
			int xDist2 = game.BJP.BJM.startX;
			
			if ((xPos > xDist && xPos < xDist + xxRange) && (yPos > yDist && yPos < yDist + yyRange)) {
				game.BJP.BJM.addPlayer();
			} else if ((xPos > xDist2 && xPos < xDist2 + xxRange) && (yPos > yDist2 && yPos < yDist2 + yyRange)) {
				game.BJP.BJM.startGame();
			}
			
			int yRange = game.connectMenu.nameBarHeight;
			int xRange = game.connectMenu.nameBarWidth;
			int barx = game.BJP.BJM.addX - 160;
			int height = 75;
			int bary1 = game.BJP.BJM.addY + ((5 * 75/4) * (1)) - 30;
			int bary2 = game.BJP.BJM.addY + ((5 * 75/4) * (2)) - 30;
			int bary3 = game.BJP.BJM.addY + ((5 * 75/4) * (3)) - 30;
			int bary4 = game.BJP.BJM.addY + ((5 * 75/4) * (4)) - 30;
			int bary5 = game.BJP.BJM.addY + ((5 * 75/4) * (5)) - 30;
			
			boolean inXRange = false;
			
			if (xPos > barx && xPos < barx + xRange) {
				inXRange = true;
			}
			if (inXRange && (yPos > bary1 && yPos < bary1 + yRange)) {
				game.BJP.BJM.setOnBoxOne();
			} else if (inXRange && (yPos > bary2 && yPos < bary2 + yRange)) {
				game.BJP.BJM.setOnBoxTwo();
			} else if (inXRange && (yPos > bary3 && yPos < bary3 + yRange)) {
				game.BJP.BJM.setOnBoxThree();
			} else if (inXRange && (yPos > bary4 && yPos < bary4 + yRange)) {
				game.BJP.BJM.setOnBoxFour();
			} else if (inXRange && (yPos > bary5 && yPos < bary5 + yRange)) {
				game.BJP.BJM.setOnBoxFive();
			}
			
		} else if (game.getGameState() == STATE.BlackJack) {

			
			
		}
	}
}
