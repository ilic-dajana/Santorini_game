package players;

public class Player {	
	private String name;
	private int nmbr;
	private int myFigures;
	private int[] figurePositions;
	private boolean figureIsMoved = false, doTiles = false;
	private int from, to;
	
	
	public Player(String name, int n) {		
		this.from = -1;
		this.to = -1;
		figurePositions = new int[2];
		figurePositions[0] = -1;
		figurePositions[1] = -1;
		nmbr = n;
		myFigures = 2;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public boolean FromTo(int from, int to) {

		if (from == 0) {
			if (to == 1 || to == 6 || to == 5)
				return true;
		} else if (from == 4) {
			if (to == 3 || to == 8 || to == 9)
				return true;
		} else if (from == 20) {
			if (to == 15 || to == 16 || to == 21)
				return true;
		} else if (from == 24) {
			if (to == 18 || to == 19 || to == 23)
				return true;
		} else if (from == 1 || from == 2 || from == 3) {
			if (to == from - 1 || to == from + 1 || to == from + 4 || to == from + 5 || to == from + 6)
				return true;
		} else if (from == 21 || from == 22 || from == 23) {
			if (to == from - 1 || to == from + 1 || to == from - 4 || to == from - 5 || to == from - 6)
				return true;
		} else if (from == 5 || from == 10 || from == 15) {
			if (to == from + 1 || to == from + 5 || to == from - 5 || to == from - 4 || to == from + 6)
				return true;
		} else if (from == 19 || from == 9 || from == 14) {
			if (to == from - 1 || to == from + 5 || to == from - 5 || to == from + 4 || to == from - 6)
				return true;
		} else {
			if (to == from + 1 || to == from - 1 || to == from + 4 || to == from - 4 || to == from + 6 || to == from - 6
					|| to == from + 5 || to == from - 5)
				return true;
		}
		return false;
	}


	public int getMyFigures() {
		return myFigures;
	}

	public void setMyFigures(int myFigures) {
		this.myFigures = myFigures;
	}
	

	public int getOneFigure() {
		int ret = myFigures;
		myFigures = myFigures - 1;
		return ret;
	}

	public int getNmbr() {
		return nmbr;
	}

	public void setNmbr(int nmbr) {
		this.nmbr = nmbr;
	}

	public boolean isFigureIsMoved() {
		return figureIsMoved;
	}

	public void setFigureIsMoved(boolean figureIsMoved) {
		this.figureIsMoved = figureIsMoved;
	}

	public int[] getFigurePositions() {
		return figurePositions;
	}

	public void setFigurePositions(int pos) {
		figurePositions[(myFigures + 1) % 2] = pos;
		System.out.println("Fig pos:" + " "+ figurePositions[0]+ " " +figurePositions[1]);
	}

	public void newFigurePosition(int from, int to) {
		for (int i = 0; i < 2; i++) {
			if (figurePositions[i] == from) {
				figurePositions[i] = to;
			}
		}
		System.out.println("Fig pos:" + " "+ figurePositions[0]+ " " +figurePositions[1]);

	}
	
	public int getFigureindex(int boardIndex) {
		if(figurePositions[0] == boardIndex)
			return 0;
		return 1;
	}
	

	public boolean isDoTiles() {
		return doTiles;
	}

	public void setDoTiles(boolean doTiles) {
		this.doTiles = doTiles;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public boolean setTo(int to) {
		this.to = to;
		if(FromTo(from, to))
			return true;
		return false;
	}


}
