package game;

public class GameState {

	public int[] clicks;
	public int[][] fig;
	public boolean endGame;
	
	public GameState(int[] lvl, int[] fig1, int[] fig2, boolean endGame) {
		clicks = new int[25];
		fig = new int [2][];
		fig[0] = new int [2];
		fig[1] = new int [2];
		
		for (int i = 0; i < 25; i++) {
			this.clicks[i] = lvl[i];
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (i == 0) {
					fig[i][j] = fig1[j];
				} else {
					fig[i][j] = fig2[j];
				}
			}
		}
		this.endGame = endGame;
	}
	
	public void doMove(Move move, int playerIdx) {
		fig[playerIdx][move.figIdx] = move.toIdx;
		clicks[move.tileIdx]++;
	}
	
	public void undoMove(Move move, int playerIdx) {
		clicks[move.tileIdx]--;
		fig[playerIdx][move.figIdx] = move.fromIdx;
	}
	
}
