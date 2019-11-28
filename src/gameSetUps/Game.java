package gameSetUps;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.util.Random;

import audio.*;
import blackJack.*;
import checkers.CheckerPlayer;
import connect4.*;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 25L;

	public final static int WIDTH = 1000;
	
	public final static int HEIGHT = WIDTH/12 * 9;
	
	private Thread thread;
	private boolean running = false;
	public ThreadPool pool;
	public MusicPlayer MP;
	
	public Handler handler;
	Random r;
	public static ImageManager IM;
	public Image bgimg;
	public Menu menu;
	public ChessPlayer chessPlayer;
	public ChessHUD cHUD;
	public HomeMenuC4 connectMenu;
	public ConnectFourPlayer c4p;
	public CheckerPlayer checkPlayer;
	public BlackJackPlayer BJP;
	
	public STATE gameState = STATE.Menu;
	
	public enum STATE {
		Menu,
		Game,
		MoveState,
		RebirthState,
		ConnectMenu,
		Connect,
		Chess,
		Checkers,
		CheckersMove,
		BlackJackMenu,
		BlackJack,
		BJBet,
		BlackJackFirstHand,
		BJCollectInsurance
	}
	
	
	public Game() {
		handler = new Handler(this);
		IM = new ImageManager();
		menu = new Menu(IM);
		chessPlayer = new ChessPlayer(this, handler);
		cHUD = new ChessHUD(this, handler);
		this.addKeyListener(new KeyInput(this, handler));
		this.addMouseListener(new MouseInput(this, handler));
	//	this.addMouseMotionListener(new MouseMotionInput(this, handler));
		new Window(WIDTH, HEIGHT, "Casino Game", this);
		r = new Random();
		connectMenu = new HomeMenuC4(this, handler);
		c4p = new ConnectFourPlayer(this, handler);
		checkPlayer = new CheckerPlayer(this, handler);
		BJP = new BlackJackPlayer(this);
	}

	public synchronized void start() {
	//	pool = new ThreadPool(2);
	//	pool.runTask(this);
		thread = new Thread(this);
		thread.start();
	//	MP = new MusicPlayer("FireEmblemTheme", "FireEmblemHomeTune");
	//	pool.runTask(MP);
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void tick() {

		if (gameState == STATE.Chess) {
		handler.tick();	
		} else if (gameState == STATE.Checkers || gameState == STATE.CheckersMove) {
			checkPlayer.tick();
		} else if (gameState == STATE.BlackJack || gameState == STATE.BlackJackFirstHand || gameState == STATE.BJCollectInsurance) {
			BJP.tick();
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		if (gameState == STATE.Menu) {
			menu.render(g);
		} else if (gameState == STATE.Game) {
			handler.render(g);
		} else if (gameState == STATE.Chess) {
			chessPlayer.render(g);
			handler.renderChess(g);
			cHUD.render(g);
		} else if (gameState == STATE.MoveState) {
			chessPlayer.render(g);
			handler.renderChess(g);
			cHUD.render(g);
		} else if (gameState == STATE.ConnectMenu) {
			connectMenu.render(g);
		} else if (gameState == STATE.Connect) {
			c4p.render(g);
			handler.renderConnectFour(g);
		} else if (gameState == STATE.Checkers || gameState == STATE.CheckersMove) {
			checkPlayer.render(g);
		} else if (gameState == STATE.RebirthState) {
			chessPlayer.render(g);
			handler.renderChess(g);
			cHUD.render(g);
		} else if (gameState == STATE.BlackJack) {
			BJP.render(g);
		} else if (gameState == STATE.BlackJackMenu) {
			BJP.BJM.render(g);
		} else if (gameState == STATE.BlackJackFirstHand) {
			BJP.render(g);
		} else if (gameState == STATE.BJCollectInsurance) {
			BJP.renderInsurance(g);
		} else if (gameState == STATE.BJBet) {
			BJP.renderBet(g);
		}
		
		
		g.dispose();
		bs.show();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
	//	int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running) {
				render();
			}
	//		frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
		//		frames = 0;
				
			}
		}
		stop();
	}
	
	public static void main(String[] args) {
		new Game();
	}
	
	public STATE getGameState() {
		return gameState;
	}
	public void setGameState(STATE s) {
		gameState = s;
	}
	public ChessPlayer getChessPlayer() {
		return chessPlayer;
	}
}
