package players;

import java.awt.Color;

import game.Move;

public class HumanPlayer extends Player {
		

	private boolean finished = false;

	public HumanPlayer(String name, int n) {
		super(name, n);		
	}
	public HumanPlayer(int n) {
		super(null, n);
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
}
