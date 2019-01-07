package game;

import gui.GameBoard;

public class Board {
	private Player p1;
	private Player p2;
	private int dificulty = -1;
	private boolean nextPlayer;
	private GameBoard myGame;
	
	
	public Board() {	
		p1 = new Player(null, null, 1);
		p2 = new Player(null, null, 2);
		nextPlayer = true;
	}
	
	public Player getNextPlayer() {
		if(nextPlayer) {
			nextPlayer = false;
			myGame.getNextLabel().setText("Next Player: " + p2.getName());
			return p2;
		}else {
			nextPlayer = true;
			myGame.getNextLabel().setText("Next Player: " + p1.getName());
			return p1;
		}
	}
	
	public String getDificulty() {
		switch(dificulty) {
			case 0: return "easy";
			case 1: return "normal";
			case 2: return "hard";
			default: return "no dificulty";
		}
	}
		
	public GameBoard getMyGame() {
		return myGame;
	}

	public void setMyGame(GameBoard myGame) {
		this.myGame = myGame;
	}

	public Player getP1() {
		return p1;
	}	
	public void setP1(Player p1) {
		this.p1 = p1;
	}
	public Player getP2() {
		return p2;
	}
	public void setP2(Player p2) {
		this.p2 = p2;
	}	
	
	public void setDificulty(int dificulty) {
		this.dificulty = dificulty;
	}
	
	
}
