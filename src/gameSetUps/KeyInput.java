package gameSetUps;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import blackJack.Card;
import checkers.CheckerPiece;
import checkers.CheckerTile;
import gameSetUps.Game.STATE;

public class KeyInput extends KeyAdapter{

	public Handler handler;
	public Game game;
	public ChessPlayer chessPlayer;
	
	public KeyInput(Game game, Handler handler) {
		this.handler = handler;
		this.game = game;
		chessPlayer = game.getChessPlayer();
	}
	
	
	public void keyPressed(KeyEvent e) {
		
		char key = e.getKeyChar();
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_ESCAPE) System.exit(42);
		
		if (game.getGameState() == STATE.Chess) {
			
			int scale = Game.WIDTH / 12;
			int xPlus = 152;
			int yPlus = 26;
			
			if (keyCode == KeyEvent.VK_RIGHT) {
				chessPlayer.incrementSelX();
			} else if (keyCode == KeyEvent.VK_LEFT) {
				chessPlayer.decrementSelX();
			} else if (keyCode == KeyEvent.VK_UP) {
				chessPlayer.decrementSelY();
			} else if (keyCode == KeyEvent.VK_DOWN) {
				chessPlayer.incrementSelY();
			} else if (keyCode == KeyEvent.VK_ENTER) {
				ChessTile tile = game.handler.getChessTile(chessPlayer.selX*scale + xPlus, chessPlayer.selY * scale + yPlus);
				if (tile != null)	{
					chessPlayer.setSelectedTile(tile);
					game.setGameState(STATE.MoveState);
				}
			}
			
			if (key == 'a') {
				ChessTile tile = game.handler.getChessTile(chessPlayer.selX*scale + xPlus, chessPlayer.selY * scale + yPlus);
				if (tile != null)	{
					chessPlayer.setSelectedTile(tile);
					game.setGameState(STATE.MoveState);
				}
				
			} else if (key == 's') {
				handler.resetSelected();
			}
		} else if (game.getGameState() == STATE.MoveState) {
			
			int scale = Game.WIDTH / 12;
			int xPlus = 152;
			int yPlus = 26;
			
			if (keyCode == KeyEvent.VK_RIGHT) {
				chessPlayer.incrementSelX();
			} else if (keyCode == KeyEvent.VK_LEFT) {
				chessPlayer.decrementSelX();
			} else if (keyCode == KeyEvent.VK_UP) {
				chessPlayer.decrementSelY();
			} else if (keyCode == KeyEvent.VK_DOWN) {
				chessPlayer.incrementSelY();
			} else if (keyCode == KeyEvent.VK_ENTER) {
			if (chessPlayer.selectedTile.isOccupied) {
				chessPlayer.selectedTile.carrier.move(chessPlayer.selX*scale + xPlus, chessPlayer.selY*scale + yPlus);
				game.handler.resetSelected();
				game.setGameState(STATE.Chess);
			}
			}
			
			
			if (key == 'a') {
				if (chessPlayer.selectedTile.isOccupied) {
				chessPlayer.selectedTile.carrier.move(chessPlayer.selX*scale + xPlus, chessPlayer.selY*scale + yPlus);
				game.handler.resetSelected();
				game.setGameState(STATE.Chess);
				}
			}  else if (key == 's'){
				handler.resetSelected();
				game.setGameState(STATE.Chess);
			}
		} else if (game.getGameState() == STATE.Menu) {
			
			if (keyCode == KeyEvent.VK_DOWN) {
				game.menu.incrementSelected();
			} else if (keyCode == KeyEvent.VK_UP) {
				game.menu.decrementSelected();
			} else if (keyCode == KeyEvent.VK_ENTER) {
				if (game.menu.selectedIndex == 0) game.setGameState(STATE.Chess);
				if (game.menu.selectedIndex == 1) game.setGameState(STATE.ConnectMenu);
				if (game.menu.selectedIndex == 2) game.setGameState(STATE.Checkers);
				if (game.menu.selectedIndex == 3) game.setGameState(STATE.BlackJackMenu);
			}
			
			if (key == 'a') {
				if (game.menu.selectedIndex == 0) game.setGameState(STATE.Chess);
				if (game.menu.selectedIndex == 1) game.setGameState(STATE.ConnectMenu);
				if (game.menu.selectedIndex == 2) game.setGameState(STATE.Checkers);
				if (game.menu.selectedIndex == 3) game.setGameState(STATE.BlackJackMenu);
			}
			
			
			
		} else if (game.getGameState() == STATE.ConnectMenu) {
			
			if (keyCode == KeyEvent.VK_BACK_SPACE) {
				if (game.connectMenu.onBoxOne) {
					if (!game.connectMenu.name1.isEmpty()) game.connectMenu.name1.pop();
				} else if (game.connectMenu.onBoxTwo) {
					if (!game.connectMenu.name2.isEmpty()) game.connectMenu.name2.pop();
				}
				return;
			} else if (keyCode == KeyEvent.VK_SHIFT) {
				return;
			} else if (keyCode == KeyEvent.VK_TAB) {
				game.connectMenu.flipBoxes();
			}
			
			
			if (game.connectMenu.onBoxOne) {
				if (game.connectMenu.name1.size() < game.connectMenu.maxLength) {
				game.connectMenu.name1.add(key);
				}
			} else if (game.connectMenu.onBoxTwo) {
				if (game.connectMenu.name2.size() < game.connectMenu.maxLength) {
					game.connectMenu.name2.add(key);
				}
			}
		
		} else if (game.getGameState() == STATE.Connect) {
			
			
			if (keyCode == KeyEvent.VK_RIGHT) {
				game.c4p.incrementRowIndex();
			} else if (keyCode == KeyEvent.VK_LEFT) {
				game.c4p.decrementRowIndex();
			} else if (keyCode == KeyEvent.VK_ENTER) {
				game.c4p.placeCoin(game.c4p.rowIndex, game.c4p.largestIndex(game.c4p.rowIndex));
				game.c4p.nextTurn();
			}
			
			if (key == 'a') {
				game.c4p.placeCoin(game.c4p.rowIndex, game.c4p.largestIndex(game.c4p.rowIndex));
				game.c4p.nextTurn();
			}
			
		} else if (game.getGameState() == STATE.Checkers) {
			
			if (keyCode == KeyEvent.VK_RIGHT) {
				game.checkPlayer.incrementSelX();
			} else if (keyCode == KeyEvent.VK_LEFT) {
				game.checkPlayer.decrementSelX();
			} else if (keyCode == KeyEvent.VK_UP) {
				game.checkPlayer.decrementSelY();
			} else if (keyCode == KeyEvent.VK_DOWN) {
				game.checkPlayer.incrementSelY();
			}
			
			if (key == 'a') {
				game.checkPlayer.resetSelected();
				game.checkPlayer.flipSelected(handler.getCheckerTile(game.checkPlayer.selX, game.checkPlayer.selY));
				if (handler.getCheckerTile(game.checkPlayer.selX, game.checkPlayer.selY).isOccupied) {
					
					CheckerPiece piece = handler.getCheckerPiece(game.checkPlayer.selX, game.checkPlayer.selY);
					game.checkPlayer.setSelectedPiece(piece);
					
					if (piece.isKing) {
						game.checkPlayer.generateMoves(piece, piece.dir);
						game.checkPlayer.generateKingMoves(piece, -1*piece.dir);
					} else {
						game.checkPlayer.generateMoves(piece, piece.dir);
					}
					//if King generate moves for -dir
					game.setGameState(STATE.CheckersMove);
				}
			} else if (key == 's') {
				game.checkPlayer.resetSelected();
			} else if (key == 'p') {
				game.checkPlayer.nextTurn();
			}
			
			
		} else if (game.getGameState() == STATE.CheckersMove) {
			if (keyCode == KeyEvent.VK_RIGHT) {
				game.checkPlayer.incrementSelX();
			} else if (keyCode == KeyEvent.VK_LEFT) {
				game.checkPlayer.decrementSelX();
			} else if (keyCode == KeyEvent.VK_UP) {
				game.checkPlayer.decrementSelY();
			} else if (keyCode == KeyEvent.VK_DOWN) {
				game.checkPlayer.incrementSelY();
			}
			
			if (key == 'a') {
				
				CheckerTile destTile = handler.getCheckerTile(game.checkPlayer.selX, game.checkPlayer.selY);
				if (destTile.selected) {
					game.checkPlayer.selectedPiece.move(destTile.x, destTile.y);
				}
				game.checkPlayer.resetSelected();
				game.setGameState(STATE.Checkers);
			} else if (key == 's') {
				game.checkPlayer.resetSelected();
				game.setGameState(STATE.Checkers);
			}
			
		} else if (game.getGameState() == STATE.RebirthState) {
			
			if (keyCode == KeyEvent.VK_RIGHT) {
				game.cHUD.incrementSelX();
			} else if (keyCode == KeyEvent.VK_LEFT) {
				game.cHUD.decrementSelX();
			} else if (keyCode == KeyEvent.VK_UP) {
				game.cHUD.decrementSelY();
			} else if (keyCode == KeyEvent.VK_DOWN) {
				game.cHUD.incrementSelY();
			} 
			
			if (key == 'a') {
				int index = ((game.cHUD.selY * game.cHUD.cols) + game.cHUD.selX);
				if (index < game.cHUD.myDead.size()) {
				ChessObject p = game.cHUD.myDead.get(index);
				game.cHUD.setRebirthedUnit(p);
				game.cHUD.placeRebirthedUnit();
				game.setGameState(STATE.Chess);
				} else {
					game.setGameState(STATE.Chess);
				}
			}
			
		} else if (game.getGameState() == STATE.BlackJackMenu) {
			
			
			if (keyCode == KeyEvent.VK_BACK_SPACE) {
				if (game.BJP.BJM.onBoxOne) {
					if (!game.BJP.BJM.names.get(0).isEmpty()) game.BJP.BJM.names.get(0).pop();
				} else if (game.BJP.BJM.onBoxTwo) {
					if (!game.BJP.BJM.names.get(1).isEmpty()) game.BJP.BJM.names.get(1).pop();
				} else if (game.BJP.BJM.onBoxThree) {
					if (!game.BJP.BJM.names.get(2).isEmpty()) game.BJP.BJM.names.get(2).pop();
				} else if (game.BJP.BJM.onBoxFour) {
					if (!game.BJP.BJM.names.get(3).isEmpty()) game.BJP.BJM.names.get(3).pop();
				} else if (game.BJP.BJM.onBoxFive) {
					if (!game.BJP.BJM.names.get(4).isEmpty()) game.BJP.BJM.names.get(4).pop();
				} 
				return;
			} else if (keyCode == KeyEvent.VK_SHIFT) {
				return;
			}
			
			if (game.BJP.BJM.onBoxOne) {
				if (game.BJP.BJM.names.get(0).size() < game.connectMenu.maxLength) {
					game.BJP.BJM.names.get(0).add(key);
					
				}
			} else if (game.BJP.BJM.onBoxTwo) {
				if (game.BJP.BJM.names.get(1).size() < game.connectMenu.maxLength) {
					game.BJP.BJM.names.get(1).add(key);
				}
			} else if (game.BJP.BJM.onBoxThree) {
				if (game.BJP.BJM.names.get(2).size() < game.connectMenu.maxLength) {
					game.BJP.BJM.names.get(2).add(key);
				}
			} else if (game.BJP.BJM.onBoxFour) {
				if (game.BJP.BJM.names.get(3).size() < game.connectMenu.maxLength) {
					game.BJP.BJM.names.get(3).add(key);
				}
			} else if (game.BJP.BJM.onBoxFive) {
				if (game.BJP.BJM.names.get(4).size() < game.connectMenu.maxLength) {
					game.BJP.BJM.names.get(4).add(key);
				}
			} 
			
			
		} else if (game.getGameState() == STATE.BlackJack) {
			
			if (keyCode == KeyEvent.VK_ENTER) {
				if (!game.BJP.betDone) {
					game.BJP.setBetDone(true);
					game.setGameState(STATE.BJBet);
				} else {
					game.BJP.incrementPlayer();	
				}
			} else if (key == 'a') {
	/*			if (!game.BJP.deck.isEmpty()) {
				game.BJP.deck.pop();
				}*/
			} else if (key == 'S') {
				game.BJP.shuffle();
			}
			
			if (keyCode == KeyEvent.VK_RIGHT) {
				game.BJP.incrementBox();
			} else if (keyCode == KeyEvent.VK_LEFT) {
				game.BJP.decrementBox();
			}
			
		} else if (game.getGameState() == STATE.BJCollectInsurance) {
			
			if (keyCode == KeyEvent.VK_ENTER) {
				game.BJP.incrementPlayer();
			}
			
			
		} else if (game.getGameState() == STATE.BJBet) {
			
			if (keyCode == KeyEvent.VK_UP) {
				game.BJP.incrementBetAmount(5);
			}
			if (keyCode == KeyEvent.VK_DOWN) {
				game.BJP.incrementBetAmount(-5);
			}
			if (key == 'a') {
				game.BJP.currentGambler.addToCurrency((-1 * game.BJP.betAmount));
				game.BJP.currentGambler.setRoundBetAmount(game.BJP.betAmount);
				game.BJP.setCoins(game.BJP.currentGambler, game.BJP.betAmount);
				game.BJP.resetBetAmount();
				game.BJP.incrementPlayer();
				game.BJP.currentGambler.setDidBet(true);
				if (game.BJP.allDidBet()) {
					game.BJP.setBetDone(true);
					game.BJP.dealFirstHand();
					game.setGameState(STATE.BlackJackFirstHand);
				}
			}
			
		} else if (game.getGameState() == STATE.BlackJackFirstHand) {
			
			if (keyCode == KeyEvent.VK_RIGHT) {
				game.BJP.incrementBox();
			}
			if (keyCode == KeyEvent.VK_LEFT) {
				game.BJP.decrementBox();
			}
			if (key == 'a') {
				if (game.BJP.boxSelected == 0) {
					game.BJP.addCardToGambler(game.BJP.deck.pop(), game.BJP.currentGambler);
					//we have to right a method that will put this card in the user's deck
					//and figure out exactly which x and y it should get, depending on their position
					//and depending on how many cards the player currently has
				} else if (game.BJP.boxSelected == 1) {
					game.BJP.nextPlayerForHit();
				} else if (game.BJP.boxSelected == 2) {
					game.BJP.placeInsurance();
					game.BJP.setCoins(game.BJP.currentGambler, 3*game.BJP.currentGambler.roundBetAmount/2);
				}
			}
		}
	}
}
