package etf.santorini.id150325d.players;

import java.util.ArrayList;
import java.util.List;

import etf.santorini.id150325d.game.GameState;
import etf.santorini.id150325d.game.Move;
import etf.santorini.id150325d.gui.GameBoard;

import java.lang.*;

public class SmartOpponent extends Player implements Opponent {
	
	private int difficulty;
	private int playerIdx;
	private GameState state;
	private Move bestMove;
	private Move lastMove;
	List<Move> possibleMoves;
	public SmartOpponent(int playerIdx) {
		super(null, playerIdx);
		this.difficulty = Opponent.DIFFICULTY_DEFAULT_VALUE;
		this.bestMove = null;
		this.playerIdx = playerIdx;
		lastMove = new Move();
	}	
	
	public SmartOpponent(int playerIdx, int difficulty) {
		super(null, playerIdx);
		this.difficulty = difficulty;
		this.bestMove = null;
		this.playerIdx = playerIdx;
		lastMove = new Move();
	}	
	
	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	public Move getNexMove(GameState state, Move lastPlayedMove) { // regularan potez (lastPlayedMove za prvi potez treba da bude null)
		this.bestMove = null;
		this.state = state;
		lastMove = lastPlayedMove;
		if (lastMove == null) { // prvi regularan potez u igri
			lastMove = new Move(0, -1, -1, 0);
		}
		MinMax(Opponent.PLAYER_MAX, 0, Opponent.MINUS_INFINITY, Opponent.INFINITY);
		return bestMove;
	}
	
	private int MinMax(int player, int depth, int alpha, int beta) {
		if (depth == difficulty) {
			return estimationFunc();
		}
		
		List<Move> checkedMoves = getAllMoves();
		if (checkedMoves.isEmpty()) {
			return (player == Opponent.PLAYER_MAX) ? Opponent.MINUS_INFINITY + 1 :
				Opponent.INFINITY - 1;
		}
		
		int bestEstimation = (player == Opponent.PLAYER_MAX) ? 
				Opponent.MINUS_INFINITY : Opponent.INFINITY;
		
		for (Move move: checkedMoves) {
			
			state.doMove(move, playerIdx - 1);
			Move temp = lastMove;
			lastMove = move;
			int stateEstimation = MinMax(
					(player == Opponent.PLAYER_MAX) ? Opponent.PLAYER_MIN : Opponent.PLAYER_MAX,
					depth + 1,
					alpha,
					beta);
			state.undoMove(move, playerIdx - 1);
			lastMove = temp;
			
			if (player == Opponent.PLAYER_MAX && stateEstimation > bestEstimation) {
				bestEstimation = stateEstimation;
				if (depth == 0) {
					this.bestMove = move;
				}
				if (bestEstimation >= beta) {
                    return bestEstimation;
                }
                alpha = (alpha > bestEstimation) ? alpha : bestEstimation;
			}
			
			if (player == Opponent.PLAYER_MIN && stateEstimation < bestEstimation) {
				bestEstimation = stateEstimation;
				if (depth == 0) {
					this.bestMove = move;
				}
				if (bestEstimation <= alpha) {
                    return bestEstimation;
                }
                beta = (beta < bestEstimation) ? beta : bestEstimation;
			}
		}
		
		return bestEstimation;
	}
	
	private List<Move> getAllMoves() {
		possibleMoves = new ArrayList<Move>();
		int[] fig1 = state.fig[playerIdx - 1];
		int[] fig2 = state.fig[2 - playerIdx];
		int[] levels = state.clicks;

		List<Integer> possibleMovesFigure1 = FromTo(fig1[0]);
		List<Integer> possibleMovesFigure2 = FromTo(fig1[1]);

		for (int i = 0; i < possibleMovesFigure1.size(); i++) {
			int index = possibleMovesFigure1.remove(0);
			if ((index != fig2[0]) && (index != fig2[1]) && levels[index] < 4 && (index != fig1[1])
			&& (levels[index] == levels[fig1[0]] || (levels[index]  == levels[fig1[0]] + 1)) ) {

				List<Integer> possibleTileInc = FromTo(index);
				for (int j = 0; j < possibleTileInc.size(); j++) {

					int tileIndex = possibleTileInc.remove(0);

					
					if ((tileIndex != fig2[0]) && (tileIndex != fig2[1]) && (levels[tileIndex] < 4)
					&& (tileIndex != fig1[1]) && (tileIndex != fig1[0])) {
						Move newMove = new Move(0, fig1[0], index, tileIndex);
						possibleMoves.add(newMove);
					}
				}
			}
		}

		for (int i = 0; i < possibleMovesFigure2.size(); i++) {
			int index = possibleMovesFigure2.remove(0);
			if ((index != fig2[0]) && (index != fig2[1]) && levels[index] < 4 && (index != fig1[0])
			&& (levels[index] == levels[fig1[1]] || (levels[index] == levels[fig1[1]] + 1))) {
				
				List<Integer> possibleTileInc = FromTo(index);
				for (int j = 0; j < possibleTileInc.size(); j++) {

					int tileIndex = possibleTileInc.remove(0);

					Move newMove;
					if ((tileIndex != fig2[0]) && (tileIndex != fig2[1]) && (levels[tileIndex] < 4) 
					&& (tileIndex != fig1[1]) && (tileIndex != fig1[0])) {
						newMove = new Move(1, fig1[1], index, tileIndex);
						possibleMoves.add(newMove);
					}
				}
			}
		}

		return possibleMoves;
	}
	
	private int distance(int figIdx, int tileIdx) {
		int x1 = figIdx / 5;
		int y1 = figIdx % 5;
		int x2 = tileIdx / 5;
		int y2 = tileIdx % 5;
		return (int)Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	private int estimationFunc() {
		int m = state.clicks[state.fig[2 - playerIdx][lastMove.figIdx]];
		int dist = distance(state.fig[playerIdx - 1][0], lastMove.tileIdx)
				+ distance(state.fig[playerIdx - 1][1], lastMove.tileIdx)
				- distance(state.fig[2 - playerIdx][0], lastMove.tileIdx)
				- distance(state.fig[2 - playerIdx][1], lastMove.tileIdx);
		int l = (state.clicks[lastMove.tileIdx] - 1) * dist;
		return m + l;
	}
	
	public List<Integer> FromTo(int from) {

		List<Integer> possibleTiles = new ArrayList<Integer>();

		if (from == 0) {
			possibleTiles.add(1);
			possibleTiles.add(6);
			possibleTiles.add(5);
		} else if (from == 4) {
			possibleTiles.add(3);
			possibleTiles.add(8);
			possibleTiles.add(9);
		} else if (from == 20) {
			possibleTiles.add(15);
			possibleTiles.add(16);
			possibleTiles.add(21);
		} else if (from == 24) {
			possibleTiles.add(18);
			possibleTiles.add(19);
			possibleTiles.add(23);
		} else if (from == 1 || from == 2 || from == 3) {
			possibleTiles.add(from - 1);
			possibleTiles.add(from + 1);
			possibleTiles.add(from + 4);
			possibleTiles.add(from + 5);
			possibleTiles.add(from + 6);
		} else if (from == 21 || from == 22 || from == 23) {
			possibleTiles.add(from - 1);
			possibleTiles.add(from + 1);
			possibleTiles.add(from - 4);
			possibleTiles.add(from - 5);
			possibleTiles.add(from - 6);
		} else if (from == 5 || from == 10 || from == 15) {
			possibleTiles.add(from + 1);
			possibleTiles.add(from - 5);
			possibleTiles.add(from + 5);
			possibleTiles.add(from - 4);
			possibleTiles.add(from + 6);
		} else if (from == 19 || from == 9 || from == 14) {
			possibleTiles.add(from - 1);
			possibleTiles.add(from - 5);
			possibleTiles.add(from + 5);
			possibleTiles.add(from + 4);
			possibleTiles.add(from - 6);
		} else {
			possibleTiles.add(from - 1);
			possibleTiles.add(from + 1);
			possibleTiles.add(from - 5);
			possibleTiles.add(from + 5);
			possibleTiles.add(from + 4);
			possibleTiles.add(from - 4);
			possibleTiles.add(from - 6);
			possibleTiles.add(from + 6);
		}

		return possibleTiles;
	}

	public void printMoves() {
		for(int i = 0; i < possibleMoves.size(); i++)
			System.out.println(possibleMoves.get(i).figIdx + " " + possibleMoves.get(i).fromIdx + " "
					+ " " + possibleMoves.get(i).toIdx+" "+ possibleMoves.get(i).tileIdx);
	}

}
