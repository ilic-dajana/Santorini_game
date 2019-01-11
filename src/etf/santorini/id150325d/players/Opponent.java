package etf.santorini.id150325d.players;

import etf.santorini.id150325d.game.GameState;
import etf.santorini.id150325d.game.Move;

public interface Opponent {
	
	public static final int MIN_DIFFICULTY = 1;
    public static final int DIFFICULTY_DEFAULT_VALUE = 3;
    public static final int MAX_DIFFICULTY = 5;
    
    public static final int PLAYER_MIN = -1;
    public static final int PLAYER_MAX =  1;
    
    public static final int INFINITY = Integer.MAX_VALUE;
    public static final int MINUS_INFINITY = Integer.MIN_VALUE;

	public Move getNexMove(GameState state, Move lastPlayedMove);
	public void setDifficulty(int dificulty);
	public void printMoves();
}
