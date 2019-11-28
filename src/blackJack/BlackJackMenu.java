package blackJack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Stack;

import gameSetUps.*;
import gameSetUps.Game.STATE;

public class BlackJackMenu {

	public Game game;
	public ImageManager IM;
	public Image bgimg;
	public int addX, addY, addW, addH, playerIndex;
	public int startX, startY, startW, startH;
	public boolean onBoxOne, onBoxTwo, onBoxThree, onBoxFour, onBoxFive;
	public long time, time2;
	public ArrayList<Stack<Character>> names;

	public BlackJackMenu(Game game) {
		this.game = game;
		IM = Game.IM;
		bgimg = IM.getImage("/blackjackmenu.jpg");
		addX = 410;
		addY = 40;
		addW = 125;
		addH = 50;
		startX = 410;
		startY = Game.HEIGHT - 120;
		names = new ArrayList<>();
		setNames();
	}

	public void render(Graphics g) {
		g.drawImage(bgimg, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		drawAddPlayerBox(g);
		for (int i = 0; i < game.BJP.players.size(); i++) {
			Gambler gam = game.BJP.players.get(i);
			gam.renderMenu(g);
		}
		if (onBoxOne) {
			drawCursor(g, 1, names.get(0).size());
		} else if (onBoxTwo) {
			drawCursor(g, 2, names.get(1).size());
		} else if (onBoxThree) {
			drawCursor(g, 3, names.get(2).size());
		} else if (onBoxFour) {
			drawCursor(g, 4, names.get(3).size());
		} else if (onBoxFive) {
			drawCursor(g, 5, names.get(4).size());
		}
	}

	public void addPlayer() {
		new Gambler(game, playerIndex);
		playerIndex++;
	}
	public void removePlayer(Gambler p) {
		names.get(p.playerNum).clear();
		game.BJP.removeGambler(p);
	}

	public void setNames() {
		for (int i = 0; i < 5; i++) {
			names.add(new Stack<>());
		}
	}

	public void drawAddPlayerBox(Graphics g) {
		int thickness = 5;
		g.setColor(Color.black);

		for (int i = 0; i < thickness; i++) {
			g.drawRect(addX + i, addY + i, addW, addH);
		}

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(addX + thickness, addY + thickness, addW - thickness, addH - thickness);

		g.setColor(Color.black);
		g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		g.drawString("Add Player", addX + addW / 3 - (5 * thickness), addY + addH / 2 + thickness + 2);

		if (game.BJP.players.size() > 0) {
			for (int i = 0; i < thickness; i++) {
				g.drawRect(startX + i, startY + i, addW, addH);
			}
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(startX + thickness, startY + thickness, addW - thickness, addH - thickness);
			g.setColor(Color.red);
			g.drawString("Start Game", startX + (3 * thickness), startY + (6 * thickness));
		}
	}
	
	public void startGame() {
		String name;
		for (int i = 0; i < game.BJP.players.size(); i++) {
			name = "";
			for (int j = 0; j < names.get(i).size(); j++) {
				name += names.get(i).get(j);
			}
			game.BJP.put(game.BJP.getGambler(i), name);
		}
		game.BJP.setPlayerPositions();
		game.BJP.addCards();
		game.BJP.shuffle();
		game.BJP.shuffle();
		game.BJP.shuffle();
		game.setGameState(STATE.BlackJack);
	}

	public void setOnBoxOne() {
		onBoxTwo = false;
		onBoxThree = false;
		onBoxFour = false;
		onBoxFive = false;
		onBoxOne = true;
	}

	public void setOnBoxTwo() {
		onBoxTwo = true;
		onBoxThree = false;
		onBoxFour = false;
		onBoxFive = false;
		onBoxOne = false;
	}

	public void setOnBoxThree() {
		onBoxTwo = false;
		onBoxThree = true;
		onBoxFour = false;
		onBoxFive = false;
		onBoxOne = false;
	}

	public void setOnBoxFour() {
		onBoxTwo = false;
		onBoxThree = false;
		onBoxFour = true;
		onBoxFive = false;
		onBoxOne = false;
	}

	public void setOnBoxFive() {
		onBoxTwo = false;
		onBoxThree = false;
		onBoxFour = false;
		onBoxFive = true;
		onBoxOne = false;
	}

	public void drawCursor(Graphics g, int whichBox, int position) {

		int fontSize = 19;
		int distFromChar = 7;
		int delay = 1000;
		int spacing = 300;
		int height = 75;
		int indentation = 10;
		int nameBarY = addY + ((5 * height / 4) * whichBox) - 30;
		int nameBarX = addX - 160;

		if (System.currentTimeMillis() - time > spacing) {
			g.setColor(Color.black);
			g.drawLine(nameBarX + (position * fontSize) + distFromChar + indentation, nameBarY + 16,
					nameBarX + (position * fontSize) + distFromChar + indentation, nameBarY + height - 16);
			if (System.currentTimeMillis() - time2 > delay) {
				time = System.currentTimeMillis();
				time2 = System.currentTimeMillis();
			}
		}

	}
}
