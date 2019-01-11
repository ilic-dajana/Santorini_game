package game;

import gui.GameBoard;
import players.HumanPlayer;
import players.Opponent;
import players.Player;

public class Board {
	private Player p1;
	private Player p2;
	private int dificulty = -1;
	private boolean nextPlayer;
	private GameBoard myGame;
	public boolean isBothComputer = false;
	public boolean stepBystep = true;

	public Board() {
		nextPlayer = true;
	}

	public Player getNextPlayer() {
		if (nextPlayer) {
			nextPlayer = false;
			myGame.getNextLabel().setText("Next Player: " + p2.getName());
			return p2;
		} else {
			nextPlayer = true;
			myGame.getNextLabel().setText("Next Player: " + p1.getName());
			return p1;
		}
	}

	public String getDificulty() {
		switch (dificulty) {
		case 0:
			return "easy";
		case 1:
			return "normal";
		case 2:
			return "hard";
		default:
			return "no dificulty";
		}
	}
	
	
	
	public boolean isBothComputer() {
		return isBothComputer;
	}

	public void setBothComputer(boolean isBothComputer) {
		this.isBothComputer = isBothComputer;
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
	
	public boolean isStepBystep() {
		return stepBystep;
	}

	public void setStepBystep(boolean stepBystep) {
		this.stepBystep = stepBystep;
	}

	public void setDificulty(int dificulty) {
		this.dificulty = dificulty;
		if(p1 instanceof Opponent) {
			((Opponent) p1).setDifficulty((dificulty + 1) * 2);
		}
		if(p2 instanceof Opponent) {
			((Opponent) p2).setDifficulty((dificulty + 1) * 2);
		}
	}

}
