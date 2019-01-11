package etf.santorini.id150325d.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.JFrame;

import etf.santorini.id150325d.game.Board;
import etf.santorini.id150325d.game.GameState;
import etf.santorini.id150325d.game.Move;
import etf.santorini.id150325d.players.HumanPlayer;
import etf.santorini.id150325d.players.Opponent;
import etf.santorini.id150325d.players.Player;

@SuppressWarnings("serial")
public class GameBoard extends JFrame {
	
	private JButton[] board = new JButton[25];
	private JButton button = new JButton("Sledeci korak");
	private int[] clicks;
	private int figure = 0;
	private Board boardInfo;
	JLabel next;
	Player playGame;
	boolean endGame;
	private Move lastMove;
	private GameState state;
	private BufferedWriter writer;
	
	public GameBoard(Board board) {
		super();
		this.boardInfo = board;
		playGame = boardInfo.getP1();
		boardInfo.setMyGame(this);
		next = new JLabel("Next Player: " + boardInfo.getP1().getName());
		clicks = new int[25];
		for (int i = 0; i < 25; i++)
			clicks[i] = 0;
		this.endGame = false;
		try {
			writer = new BufferedWriter(new FileWriter("game.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		lastMove = new Move();
		drawBoard();
		
		
	}

	private void drawBoard() {

		JFrame santoriniBoard = new JFrame("Santorini");
		JLabel pl1 = new JLabel("	Player 1: " + boardInfo.getP1().getName());
		JLabel pl2 = new JLabel("	Player 2: " + boardInfo.getP2().getName());
		JLabel dif = new JLabel("	Dificulty: " + boardInfo.getDificulty());
		JPanel table = new JPanel(new GridLayout(5, 5, 1, 1));
		JPanel labels = new JPanel(new GridLayout(2, 3, 1, 1));
		JPanel buttonPanel = new JPanel();
		santoriniBoard.setVisible(true);
		santoriniBoard.setLayout(new BorderLayout());
		santoriniBoard.setSize(500, 500);
		santoriniBoard.setResizable(false);

		labels.add(pl1);
		labels.add(pl2);
		labels.add(dif);
		labels.add(next);
		labels.setSize(100, 500);
		table.setSize(400, 500);
		buttonPanel.add(button);
		buttonPanel.setSize(100, 500);
		
		pl1.setForeground(Color.BLUE);
		pl2.setForeground(Color.RED);

		for (int i = 0; i < 25; i++) {
			board[i] = new JButton();
			board[i].setOpaque(true);
			board[i].setContentAreaFilled(true);
			board[i].setBackground(Color.WHITE);
			board[i].setSize(new Dimension(50, 50));
		}

		for (int i = 0; i < 25; i++) {
			table.add(board[i]);
		}
		addListeners();

		santoriniBoard.add(labels, BorderLayout.NORTH);
		santoriniBoard.add(table, BorderLayout.CENTER);
		if(boardInfo.isStepBystep() && boardInfo.isBothComputer)
			santoriniBoard.add(buttonPanel, BorderLayout.SOUTH);
		else
			doAll();

	}

	private void doAll() {
		while(!endGame) {
			try {
				sledeciKorak();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public JLabel getNextLabel() {
		return next;
	}
	
	private void sledeciKorak() throws IOException {
		if(playGame.getMyFigures() > 0) {
			if(playGame instanceof Opponent) {		
				Random random = new Random();
				int k = random.nextInt(25) , j = random.nextInt(25);
				
				while (board[k].getText().equals("Figura1") || board[k].getText().equals("Figura2") || k==j)
					k = random.nextInt(25);
				
				while (board[j].getText().equals("Figura1") || board[j].getText().equals("Figura2") || k == j)
					j = random.nextInt(25);
				
				board[k].setText("Figura" + playGame.getNmbr());
				playGame.setFigurePositions(k);
				playGame.getOneFigure();
				board[j].setText("Figura" + playGame.getNmbr());
				playGame.setFigurePositions(j);
				
			    playGame.printMove(writer, k, j, 0, true);
								
				playGame.setMyFigures(0);
				
				playGame = boardInfo.getNextPlayer();			
				
			}
		}else {
			state = new GameState(clicks, boardInfo.getP1().getFigurePositions(), boardInfo.getP2().getFigurePositions(), false);
			Move move = ((Opponent) playGame).getNexMove(state, lastMove);	
			if(move == null)
				new GameEndInfo("Izgubio je igrac: " + playGame.getName(), board);
			else {
			((Opponent) playGame).printMoves();
			System.out.println("Picked move:" + move.figIdx +" "+move.fromIdx+" "+move.toIdx+" "+move.tileIdx);
				
			}
			
			moveFigure(move.fromIdx);
			moveFigure(move.toIdx);
			if(playGame.isFigureIsMoved()) {
				playGame.setFigureIsMoved(false);
				putTile(move.tileIdx);
			}
			lastMove = move;
			
		}
	}

	private void initMove(int i) {		
		
		if (playGame.getOneFigure() > 0) {
			if (!(board[i].getText().equals("Figura1") || board[i].getText().equals("Figura2"))) {
				board[i].setText("Figura" + playGame.getNmbr());
				playGame.setFigurePositions(i);
				
				if (playGame.getMyFigures() == 0) {
					try {
						playGame.printMove(writer, playGame.getFigurePositions()[0], playGame.getFigurePositions()[1], 0, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					playGame = boardInfo.getNextPlayer();	
					
					if(playGame.getMyFigures() > 0) {	
						if(playGame instanceof Opponent) {		
							Random random = new Random();
							int k = random.nextInt(25) , j = random.nextInt(25);
							
							while (board[k].getText().equals("Figura1") || board[k].getText().equals("Figura2") || k==j)
								k = random.nextInt(25);
							
							while (board[j].getText().equals("Figura1") || board[j].getText().equals("Figura2") || k == j)
								j = random.nextInt(25);
							
							board[k].setText("Figura" + playGame.getNmbr());
							playGame.setFigurePositions(k);
							playGame.getOneFigure();
							board[j].setText("Figura" + playGame.getNmbr());
							playGame.setFigurePositions(j);
							try {
								playGame.printMove(writer, k,j, 0, true);
							} catch (IOException e) {
								e.printStackTrace();
							}
							playGame.setMyFigures(0);
							playGame = boardInfo.getNextPlayer();				
							
						}
					}
				}
			} else {
				playGame.setMyFigures(playGame.getMyFigures() + 1);
				new Error("Ponovi potez");
			}
		}
		
	}

	private void putTile(int i) {

		if (!(board[i].getText().equals("Figura1") || board[i].getText().equals("Figura2"))) {

			if (FromTo(playGame.getTo(), i)) {

				if (!board[i].getText().equals("KUPOLA")) {
					clicks[i]++;
					board[i].setBackground(Color.GRAY);
					lastMove.setTileIdx(i);
					try {
						playGame.printMove(writer, playGame.getFrom(), playGame.getTo(), i, false);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (clicks[i] == 4) {
					board[i].setText("KUPOLA");
					board[i].setForeground(Color.WHITE);
					board[i].setBackground(Color.RED);
				} else
					board[i].setText("" + clicks[i]);
				
				playGame.setDoTiles(false);
				playGame.setFrom(-1);
				playGame.setTo(-1);
				playGame = boardInfo.getNextPlayer();
				 
				if (!checkForAnotherMove(playGame.getFigurePositions()[0])
				&& !checkForAnotherMove(playGame.getFigurePositions()[1])) {
					new GameEndInfo("Izgubio je igrac: " + playGame.getName(), board);
					this.endGame = true;
				}

			} else {
				new Error("Ne moze se ovde postaviti plocica! Izaberite drugo polje !");
			}
		} else {
			new Error("Ne moze se ovde postaviti plocica! Izaberite drugo polje!");
		}
	}

	private void moveFigure(int i) {
		if (playGame.getFrom() == -1) {

			if ((playGame.getNmbr() == 1 && board[i].getText().equals("Figura1"))
					|| (playGame.getNmbr() == 2 && board[i].getText().equals("Figura2"))) {
				playGame.setFrom(i);
				lastMove.setFigIdx(playGame.getFigureindex(i));
				lastMove.setFromIdx(i);
			} else {
				new Error("Izaberite Vasu figuru!");
			}

		} else {
			if (!(board[i].getText().equals("Figura1") || board[i].getText().equals("Figura2"))) {
				boolean OK = playGame.setTo(i);

				if (((clicks[playGame.getFrom()] == clicks[i]) || (clicks[playGame.getFrom()] + 1 == clicks[i])) && OK
						&& !(board[i].getText().equals("KUPOLA"))) {
					
					board[i].setText(board[playGame.getFrom()].getText());
					board[playGame.getFrom()].setText("");

					if (clicks[i] == 3) {
						new GameEndInfo("Igrac " + playGame.getName() + " je pobedio!", board);
						this.endGame = true;
					}
					playGame.setFigureIsMoved(true);
					lastMove.setToIdx(i);
					if (board[playGame.getFrom()].getBackground().equals(Color.GRAY)) {
						board[playGame.getFrom()].setText(clicks[playGame.getFrom()] + "");
					}

					playGame.newFigurePosition(playGame.getFrom(), playGame.getTo());

				} else {
					if (!OK)
						new Error("Odredisno polje nije dobro izabrano!");
					else {
						new Error("Pokusajte ponovo");
					
					}
				}
			} else
				new Error("Izaberite drugo polje");
		}

	}

	public boolean checkForAnotherMove(int from) {
		int level = clicks[from];

		switch (from) {
		case 0:
			if ((level == clicks[from + 1] || (level == clicks[from + 1] + 1))
					&& !(board[from + 1].getText().equals("Figura1") || board[from + 1].getText().equals("Figura2")
							|| board[from + 1].getText().equals("KUPOLA"))
					|| (level == clicks[from + 5] || (level == clicks[from + 5] + 1))
							&& !(board[from + 5].getText().equals("Figura1")
									|| board[from + 5].getText().equals("Figura2")
									|| board[from + 5].getText().equals("KUPOLA"))
					|| (level == clicks[from + 6] || (level == clicks[from + 6] + 1))
							&& !(board[from + 6].getText().equals("Figura1")
									|| board[from + 6].getText().equals("Figura2")
									|| board[from + 6].getText().equals("KUPOLA")))
				return true;
			break;
		case 4:
			if ((level == clicks[from - 1] || (level == clicks[from - 1] + 1))
					&& !(board[from - 1].getText().equals("Figura1") || board[from - 1].getText().equals("Figura2")
							|| board[from - 1].getText().equals("KUPOLA"))
					|| (level == clicks[from + 5] || (level == clicks[from + 5] + 1))
							&& !(board[from + 5].getText().equals("Figura1")
									|| board[from + 5].getText().equals("Figura2")
									|| board[from + 5].getText().equals("KUPOLA"))
					|| (level == clicks[from + 4] || (level == clicks[from + 4] + 1))
							&& !(board[from + 4].getText().equals("Figura1")
									|| board[from + 4].getText().equals("Figura2")
									|| board[from + 4].getText().equals("KUPOLA")))
				return true;
			break;
		case 20:
			if ((level == clicks[from + 1] || (level == clicks[from + 1] + 1))
					&& !(board[from + 1].getText().equals("Figura1") || board[from + 1].getText().equals("Figura2")
							|| board[from + 1].getText().equals("KUPOLA"))
					|| (level == clicks[from - 5] || (level == clicks[from - 5] + 1))
							&& !(board[from - 5].getText().equals("Figura1")
									|| board[from - 5].getText().equals("Figura2")
									|| board[from - 5].getText().equals("KUPOLA"))
					|| (level == clicks[from - 4] || (level == clicks[from - 4] + 1))
							&& !(board[from - 4].getText().equals("Figura1")
									|| board[from - 4].getText().equals("Figura2")
									|| board[from - 4].getText().equals("KUPOLA")))
				return true;
			break;
		case 24:
			if ((level == clicks[from - 1] || (level == clicks[from - 1] + 1))
					&& !(board[from - 1].getText().equals("Figura1") || board[from - 1].getText().equals("Figura2")
							|| board[from - 1].getText().equals("KUPOLA"))
					|| (level == clicks[from - 5] || (level == clicks[from - 5] + 1))
							&& !(board[from - 5].getText().equals("Figura1")
									|| board[from - 5].getText().equals("Figura2")
									|| board[from - 5].getText().equals("KUPOLA"))
					|| (level == clicks[from - 6] || (level == clicks[from - 6] + 1))
							&& !(board[from - 6].getText().equals("Figura1")
									|| board[from - 6].getText().equals("Figura2")
									|| board[from - 6].getText().equals("KUPOLA")))
				return true;
			break;
		case 1:
		case 2:
		case 3:
			if ((level == clicks[from + 1] || (level == clicks[from + 1] + 1))
					&& !(board[from + 1].getText().equals("Figura1") || board[from + 1].getText().equals("Figura2")
							|| board[from + 1].getText().equals("KUPOLA"))
					|| (level == clicks[from - 1] || (level == clicks[from - 1] + 1))
							&& !(board[from - 1].getText().equals("Figura1")
									|| board[from - 1].getText().equals("Figura2")
									|| board[from - 1].getText().equals("KUPOLA"))
					|| (level == clicks[from + 5] || (level == clicks[from + 5] + 1))
							&& !(board[from + 5].getText().equals("Figura1")
									|| board[from + 5].getText().equals("Figura2")
									|| board[from + 5].getText().equals("KUPOLA"))
					|| (level == clicks[from + 6] || (level == clicks[from + 6] + 1))
							&& !(board[from + 6].getText().equals("Figura1")
									|| board[from + 6].getText().equals("Figura2")
									|| board[from + 6].getText().equals("KUPOLA"))
					|| (level == clicks[from + 4] || (level == clicks[from + 4] + 1))
							&& !(board[from + 4].getText().equals("Figura1")
									|| board[from + 4].getText().equals("Figura2")
									|| board[from + 4].getText().equals("KUPOLA")))
				return true;
			break;
		case 5:
		case 10:
		case 15:
			if ((level == clicks[from + 1] || (level == clicks[from + 1] + 1))
					&& !(board[from + 1].getText().equals("Figura1") || board[from + 1].getText().equals("Figura2")
							|| board[from + 1].getText().equals("KUPOLA"))
					|| (level == clicks[from - 5] || (level == clicks[from - 5] + 1))
							&& !(board[from - 5].getText().equals("Figura1")
									|| board[from - 5].getText().equals("Figura2")
									|| board[from - 5].getText().equals("KUPOLA"))
					|| (level == clicks[from + 5] || (level == clicks[from + 5] + 1))
							&& !(board[from + 5].getText().equals("Figura1")
									|| board[from + 5].getText().equals("Figura2")
									|| board[from + 5].getText().equals("KUPOLA"))
					|| (level == clicks[from - 4] || (level == clicks[from - 4] + 1))
							&& !(board[from - 4].getText().equals("Figura1")
									|| board[from - 4].getText().equals("Figura2")
									|| board[from - 4].getText().equals("KUPOLA"))
					|| (level == clicks[from + 4] || (level == clicks[from + 4] + 1))
							&& !(board[from + 4].getText().equals("Figura1")
									|| board[from + 4].getText().equals("Figura2")
									|| board[from + 4].getText().equals("KUPOLA")))
				return true;
			break;
		case 9:
		case 14:
		case 19:
			if ((level == clicks[from - 1] || (level == clicks[from - 1] + 1))
					&& !(board[from - 1].getText().equals("Figura1") || board[from - 1].getText().equals("Figura2")
							|| board[from - 1].getText().equals("KUPOLA"))
					|| (level == clicks[from - 5] || (level == clicks[from - 5] + 1))
							&& !(board[from - 5].getText().equals("Figura1")
									|| board[from - 5].getText().equals("Figura2")
									|| board[from - 5].getText().equals("KUPOLA"))
					|| (level == clicks[from + 5] || (level == clicks[from + 5] + 1))
							&& !(board[from + 5].getText().equals("Figura1")
									|| board[from + 5].getText().equals("Figura2")
									|| board[from + 5].getText().equals("KUPOLA"))
					|| (level == clicks[from - 6] || (level == clicks[from - 6] + 1))
							&& !(board[from - 6].getText().equals("Figura1")
									|| board[from - 6].getText().equals("Figura2")
									|| board[from - 6].getText().equals("KUPOLA"))
					|| (level == clicks[from + 4] || (level == clicks[from + 4] + 1))
							&& !(board[from + 4].getText().equals("Figura1")
									|| board[from + 4].getText().equals("Figura2")
									|| board[from + 4].getText().equals("KUPOLA")))
				return true;
			break;
		case 21:
		case 22:
		case 23:
			if ((level == clicks[from + 1] || (level == clicks[from + 1] + 1))
					&& !(board[from + 1].getText().equals("Figura1") || board[from + 1].getText().equals("Figura2")
							|| board[from + 1].getText().equals("KUPOLA"))
					|| (level == clicks[from - 1] || (level == clicks[from - 1] + 1))
							&& !(board[from - 1].getText().equals("Figura1")
									|| board[from - 1].getText().equals("Figura2")
									|| board[from - 1].getText().equals("KUPOLA"))
					|| (level == clicks[from - 5] || (level == clicks[from - 5] + 1))
							&& !(board[from - 5].getText().equals("Figura1")
									|| board[from - 5].getText().equals("Figura2")
									|| board[from - 5].getText().equals("KUPOLA"))
					|| (level == clicks[from - 4] || (level == clicks[from - 4] + 1))
							&& !(board[from - 4].getText().equals("Figura1")
									|| board[from - 4].getText().equals("Figura2")
									|| board[from - 4].getText().equals("KUPOLA"))
					|| (level == clicks[from - 6] || (level == clicks[from - 6] + 1))
							&& !(board[from - 6].getText().equals("Figura1")
									|| board[from - 6].getText().equals("Figura2")
									|| board[from - 6].getText().equals("KUPOLA")))
				return true;
			break;
		default:
			if ((level == clicks[from - 1] || (level == clicks[from - 1] + 1))
					&& !(board[from - 1].getText().equals("Figura1") || board[from - 1].getText().equals("Figura2")
							|| board[from - 1].getText().equals("KUPOLA"))
					|| (level == clicks[from + 1] || (level == clicks[from + 1] + 1))
							&& !(board[from + 1].getText().equals("Figura1")
									|| board[from + 1].getText().equals("Figura2")
									|| board[from + 1].getText().equals("KUPOLA"))
					|| (level == clicks[from + 5] || (level == clicks[from + 5] + 1))
							&& !(board[from + 5].getText().equals("Figura1")
									|| board[from + 5].getText().equals("Figura2")
									|| board[from + 5].getText().equals("KUPOLA"))
					|| (level == clicks[from - 5] || (level == clicks[from - 5] + 1))
							&& !(board[from - 5].getText().equals("Figura1")
									|| board[from - 5].getText().equals("Figura2")
									|| board[from - 5].getText().equals("KUPOLA"))
					|| (level == clicks[from - 6] || (level == clicks[from + 6] + 1))
							&& !(board[from + 6].getText().equals("Figura1")
									|| board[from + 6].getText().equals("Figura2")
									|| board[from + 6].getText().equals("KUPOLA"))
					|| (level == clicks[from + 6] || (level == clicks[from - 6] + 1))
							&& !(board[from - 6].getText().equals("Figura1")
									|| board[from - 6].getText().equals("Figura2")
									|| board[from - 6].getText().equals("KUPOLA"))
					|| (level == clicks[from - 4] || (level == clicks[from - 4] + 1))
							&& !(board[from - 4].getText().equals("Figura1")
									|| board[from - 4].getText().equals("Figura2")
									|| board[from - 4].getText().equals("KUPOLA"))
					|| (level == clicks[from + 4] || (level == clicks[from + 4] + 1))
							&& !(board[from + 4].getText().equals("Figura1")
									|| board[from + 4].getText().equals("Figura2")
									|| board[from + 4].getText().equals("KUPOLA")))
				return true;
			break;
		}
		return false;
	}

	
	private void buttonFunc(int i) {	
		
		if (playGame.getMyFigures() > 0) {					
				initMove(i);
		}else {			

			if (playGame.isFigureIsMoved()) {
				playGame.setFigureIsMoved(false);
				playGame.setDoTiles(true);
			} else if (!(playGame.isDoTiles())) {
				moveFigure(i);
			}

			if (playGame.isDoTiles()) {
				putTile(i);
				if(playGame instanceof Opponent) {
					state = new GameState(clicks, boardInfo.getP1().getFigurePositions(), boardInfo.getP2().getFigurePositions(), false);
				
				Move move = ((Opponent) playGame).getNexMove(state, lastMove);						
				((Opponent) playGame).printMoves();			
					
							playGame.setFigureIsMoved(false);
							playGame.setDoTiles(true);
							moveFigure(move.getFromIdx());
							moveFigure(move.getToIdx());
							if(playGame.isFigureIsMoved()) {										
								playGame.setFigureIsMoved(false);
								putTile(move.getTileIdx());
							}
						
					}
				}
			}
			
		}

	

	/*
	 * @button table listeners
	 */
	private void addListeners() {
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					sledeciKorak();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		board[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(0);
			}
		});

		board[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(1);
			}
		});

		board[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(2);
			}
		});

		board[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(3);
			}
		});

		board[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(4);
			}
		});

		board[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(5);
			}
		});

		board[6].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(6);
			}
		});

		board[7].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(7);
			}
		});

		board[8].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(8);
			}
		});

		board[9].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(9);
			}
		});

		board[10].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(10);
			}
		});

		board[11].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(11);
			}
		});

		board[12].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(12);
			}
		});

		board[13].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(13);
			}
		});

		board[14].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(14);
			}
		});

		board[15].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(15);
			}
		});

		board[16].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(16);
			}
		});

		board[17].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(17);
			}
		});

		board[18].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(18);
			}
		});

		board[19].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(19);
			}
		});

		board[20].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(20);
			}
		});

		board[21].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(21);
			}
		});

		board[22].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(22);
			}
		});

		board[23].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(23);
			}
		});

		board[24].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFunc(24);
			}
		});

	}

	public JButton getBoutton(int i) {
		return board[i];
	}

	public JButton[] getBoard() {
		return board;
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

	public boolean isEndGame() {
		return endGame;
	}

	
	
}
