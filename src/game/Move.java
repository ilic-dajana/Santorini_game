package game;

public class Move {
	public int figIdx;
	public int fromIdx;
	public int toIdx;
	public int tileIdx;
	
	public Move(int figIdx, int fromIdx, int toIdx, int tileIdx) {
		super();
		this.figIdx = figIdx;
		this.fromIdx = fromIdx;
		this.toIdx = toIdx;
		this.tileIdx = tileIdx;
	}
	
	public Move() {
		this.figIdx = 1;
		this.fromIdx = 0;
		this.toIdx = 0;
		this.tileIdx = 0;
	}

	public int getFigIdx() {
		return figIdx;
	}

	public void setFigIdx(int figIdx) {
		this.figIdx = figIdx;
	}

	public int getFromIdx() {
		return fromIdx;
	}

	public void setFromIdx(int fromIdx) {
		this.fromIdx = fromIdx;
	}

	public int getToIdx() {
		return toIdx;
	}

	public void setToIdx(int toIdx) {
		this.toIdx = toIdx;
	}

	public int getTileIdx() {
		return tileIdx;
	}

	public void setTileIdx(int tileIdx) {
		this.tileIdx = tileIdx;
	}
	
	
	
	
}
