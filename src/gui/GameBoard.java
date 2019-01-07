package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.JFrame;

import game.Board;
import game.Player;

@SuppressWarnings("serial")
public class GameBoard extends JFrame {
	private JButton[] board  = new JButton[25];		
	private int[] clicks;
	private int figure = 0;
	private Board boardInfo;
	JLabel next ;
	Player playGame;

	
	
	
	public GameBoard(Board board) {
		super();
		this.boardInfo = board;
		playGame = boardInfo.getP1();
		boardInfo.setMyGame(this);
		next = new JLabel("Next Player: " + boardInfo.getP1().getName());
		clicks = new int[25];
		for(int i = 0; i < 25; i++)
			clicks[i] = 0;
		drawBoard();
	}
	
	
	
	private void drawBoard() {
		
		JFrame santoriniBoard = new JFrame("Santorini");
		JLabel pl1 = new JLabel("	Player 1: " + boardInfo.getP1().getName());
		JLabel pl2 = new JLabel("	Player 2: " + boardInfo.getP2().getName());
		JLabel dif = new JLabel("	Dificulty: " + boardInfo.getDificulty());
		JPanel table = new JPanel(new GridLayout(5,5,1,1));
		JPanel labels = new JPanel(new GridLayout(2,3,1,1));
		
		santoriniBoard.setVisible(true);
		santoriniBoard.setLayout(new BorderLayout());
		santoriniBoard.setSize(500, 500);
		santoriniBoard.setResizable(false);
		
		labels.add(pl1);
		labels.add(pl2);
		labels.add(dif);
		labels.add(next);
		labels.setSize(100,500);
		table.setSize(400,500);
	
		pl1.setForeground(Color.BLUE);
		pl2.setForeground(Color.RED);
		
		
		for(int i = 0; i < 25; i++ ) {
			board[i] = new JButton();
			board[i].setOpaque(true);
			board[i].setContentAreaFilled(true);
			board[i].setBackground(Color.WHITE);
			board[i].setSize(new Dimension(50,50));
		}		
		
		for(int i =  0; i < 25; i++) {
			table.add(board[i]);
		}
		addListeners();
		
		santoriniBoard.add(labels, BorderLayout.NORTH);
		santoriniBoard.add(table, BorderLayout.CENTER);
		
	}
	
	public JLabel getNextLabel() {
		return next;
	}
	
	private void initMove(int i) {
		if(playGame.getOneFigure() > 0) {
			if(!(board[i].getText().equals("Figura1") || board[i].getText().equals("Figura2")))	{
				board[i].setText("Figura" + playGame.getNmbr());	
				playGame.setFigurePositions(i);
				if(playGame.getMyFigures() == 0)
					playGame = boardInfo.getNextPlayer();
			}else {
				playGame.setMyFigures(playGame.getMyFigures() + 1);
				new Error("Ponovi potez");
			}
		}
	}
	
	private void putTile(int i) {	
		boolean doIt = false;
		if(!(board[i].getText().equals("Figura1") || board[i].getText().equals("Figura2"))){
			/*if(playGame.getNmbr() == 1) {
				if((board[i].getBackground()).equals(Color.RED))
					doIt = false;
				else
					doIt = true;
			}
			else{
				if((board[i].getBackground()).equals(Color.BLUE))
					doIt = false;
				else
					doIt = true;
			}		*/
			doIt = true;
		}
		
		if(doIt ) {
			/*if(playGame.getNmbr() == 1 )
				board[i].setBackground(Color.BLUE);
			else
				board[i].setBackground(Color.RED);*/
			
			if(!board[i].getText().equals("KUPOLA")) {
				clicks[i]++;
				board[i].setBackground(Color.BLUE);
			}	
			
			if(clicks[i] == 4) {
				board[i].setText("KUPOLA");
				board[i].setForeground(Color.WHITE);
				board[i].setBackground(Color.RED);
			}
			else
				board[i].setText(""+ clicks[i]);	
			playGame.setDoTiles(false);
			playGame = boardInfo.getNextPlayer();	
		}else {
			new Error("Ne moze se ovde postaviti plocica! Izaberite drugo polje!");
		}
	}
	
	
	private void moveFigure(int i) {		
		if(playGame.getFrom() == -1) {
			if((playGame.getNmbr() == 1 && board[i].getText().equals("Figura1")) || 
					(playGame.getNmbr() == 2 && board[i].getText().equals("Figura2"))){						
				playGame.setFrom(i);
			}
			else {
				new Error("Izaberite Vasu figuru!");
			}
			
			
		}else {
			if(!(board[i].getText().equals("Figura1") || board[i].getText().equals("Figura2"))){
				boolean OK = playGame.setTo(i);
				
				if(((clicks[playGame.getFrom()] == clicks[i]) || (clicks[playGame.getFrom()] + 1 == clicks[i] )) && OK && !(board[i].getText().equals("KUPOLA"))) {							
					
					board[i].setText(board[playGame.getFrom()].getText());
					board[playGame.getFrom()].setText("");
					playGame.setFigureIsMoved(true);
					if(board[playGame.getFrom()].getBackground().equals(Color.BLUE)){
						board[playGame.getFrom()].setText(clicks[playGame.getFrom()]+"");
					}
				}else {
					if(!OK)
						new Error("Odredisno polje nije dobro izabrano!");
					else {
						if(!checkForDifferentMove(playGame.getFrom())) 	
							new GameEndInfo("Izgubio je igrac: " + playGame.getName());
						else
							new Error("Pokusajte ponovo");
					}	
				}
			}
			else
				new Error("Izaberite drugo polje");
		}
		
	}
	
	
	private boolean checkForDifferentMove(int from) {
		int level = clicks[from];
		if(from == 0) {
			if( level == clicks[1] || (level == clicks[1] + 1) || !(board[1].getText().equals("KUPOLA")) || level == clicks[6] || (level == clicks[6] + 1)
					|| !(board[6].getText().equals("KUPOLA")) || level == clicks[5] || (level == clicks[5] + 1) ||  !(board[5].getText().equals("KUPOLA")) )
				return true;
		}else if(from == 4) {
			if( level == clicks[3] || (level == clicks[3] + 1) || !(board[3].getText().equals("KUPOLA")) || level == clicks[8] || (level == clicks[8] + 1)
			|| !(board[8].getText().equals("KUPOLA")) || level == clicks[9] || (level == clicks[9] + 1) ||  !(board[9].getText().equals("KUPOLA")) )
			
				return true;
		}else if(from == 20) {
			if( level == clicks[15] || (level == clicks[15] + 1) || !(board[15].getText().equals("KUPOLA")) || level == clicks[16] || (level == clicks[16] + 1)
					|| !(board[16].getText().equals("KUPOLA")) || level == clicks[15] || (level == clicks[15] + 1) ||  !(board[15].getText().equals("KUPOLA")) )
					return true;
		}else if(from == 24) {
			if( level == clicks[23] || (level == clicks[23] + 1) || !(board[23].getText().equals("KUPOLA")) || level == clicks[18] || (level == clicks[18] + 1)
					|| !(board[18].getText().equals("KUPOLA")) || level == clicks[19] || (level == clicks[19] + 1) ||  !(board[19].getText().equals("KUPOLA")) )
					return true;
		}else if(from == 1 || from == 2 || from == 3) {
			if( level == clicks[from - 1] || (level == clicks[from - 1] + 1) || !(board[from - 1].getText().equals("KUPOLA")) || level == clicks[from + 6] || (level == clicks[from + 6] + 1)
					|| !(board[from + 6].getText().equals("KUPOLA")) || level == clicks[from + 5] || (level == clicks[from + 5] + 1) ||  !(board[from + 5].getText().equals("KUPOLA"))
							|| level == clicks[from + 1] || (level == clicks[from + 1] + 1) || !(board[from + 1].getText().equals("KUPOLA"))|| level == clicks[from + 4] || (level == clicks[from + 4] + 1) || !(board[from + 4].getText().equals("KUPOLA")) )
					return true;
		}else if(from == 21 || from == 22 || from == 23) {
			if( level == clicks[from - 1] || (level == clicks[from - 1] + 1) || !(board[from - 1].getText().equals("KUPOLA"))  || level == clicks[from - 5] || (level == clicks[from - 5] + 1) ||  !(board[from - 5].getText().equals("KUPOLA"))
							|| level == clicks[from + 1] || (level == clicks[from + 1] + 1) || !(board[from + 1].getText().equals("KUPOLA")) || level == clicks[from - 6] || (level == clicks[from - 6] + 1)
								|| !(board[from - 6].getText().equals("KUPOLA")) || level == clicks[from - 5] || (level == clicks[from - 5] + 1) ||  !(board[from - 5].getText().equals("KUPOLA")))
					return true;
		}else if(from == 5 || from == 10 || from == 15) {
			if( level == clicks[from - 5] || (level == clicks[from - 5] + 1) || !(board[from - 5].getText().equals("KUPOLA"))  || level == clicks[from + 5] || (level == clicks[from + 5] + 1) ||  !(board[from + 5].getText().equals("KUPOLA"))
					|| level == clicks[from + 1] || (level == clicks[from + 1] + 1) || !(board[from + 1].getText().equals("KUPOLA")) || level == clicks[from + 6] || (level == clicks[from + 6] + 1)
						|| !(board[from + 6].getText().equals("KUPOLA")) || level == clicks[from - 4] || (level == clicks[from - 4] + 1) ||  !(board[from - 4].getText().equals("KUPOLA")))
					return true;
		}else if(from == 19 || from == 9 || from == 14) {
			if( level == clicks[from - 5] || (level == clicks[from - 5] + 1) || !(board[from - 5].getText().equals("KUPOLA"))  || level == clicks[from + 5] || (level == clicks[from + 5] + 1) ||  !(board[from + 5].getText().equals("KUPOLA"))
					|| level == clicks[from - 1] || (level == clicks[from - 1] + 1) || !(board[from - 1].getText().equals("KUPOLA")) || level == clicks[from - 6] || (level == clicks[from - 6] + 1)
						|| !(board[from - 6].getText().equals("KUPOLA")) || level == clicks[from + 4] || (level == clicks[from + 4] + 1) ||  !(board[from + 4].getText().equals("KUPOLA")))
					return true;
		}else {
			
			if( level == clicks[from - 5] || (level == clicks[from - 5] + 1) || !(board[from - 5].getText().equals("KUPOLA"))  
					|| level == clicks[from - 1] || (level == clicks[from - 1] + 1) || !(board[from - 1].getText().equals("KUPOLA")) 
					|| level == clicks[from - 6] || (level == clicks[from - 6] + 1)|| !(board[from - 6].getText().equals("KUPOLA")) 
					|| level == clicks[from + 4] || (level == clicks[from + 4] + 1) ||  !(board[from + 4].getText().equals("KUPOLA"))
					|| level == clicks[from - 5] || (level == clicks[from - 5] + 1) || !(board[from - 5].getText().equals("KUPOLA"))  
					|| level == clicks[from + 5] || (level == clicks[from + 5] + 1) ||  !(board[from + 5].getText().equals("KUPOLA"))
					|| level == clicks[from + 6] || (level == clicks[from + 6] + 1) || !(board[from + 6].getText().equals("KUPOLA"))
					|| level == clicks[from - 4] || (level == clicks[from - 4] + 1) ||  !(board[from - 4].getText().equals("KUPOLA")))
					return true;
		}
		return false;
	}



	private void buttonFunc(int i) {													
		
		if(playGame.getMyFigures() > 0)
			initMove(i);		
		else {		
			
			if(playGame.isFigureIsMoved()) {
				playGame.setFrom(-1);
				playGame.setTo(-1);
				playGame.setFigureIsMoved(false);
				playGame.setDoTiles(true);
			}else if(!(playGame.isDoTiles())) {
				moveFigure(i);
			}
			
			if(playGame.isDoTiles()) {
				putTile(i);
			}
		}
		
	}
	





	/*
	 * @button table listeners
	 */
	private void addListeners() {
		
			board[0 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(0);
				}
				});

				board[1 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(1);
				}
				});

				board[2 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(2);
				}
				});

				board[3 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(3);
				}
				});

				board[4 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(4);
				}
				});

				board[5 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(5);
				}
				});

				board[6 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(6);
				}
				});

				board[7 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(7);
				}
				});

				board[8 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(8);
				}
				});

				board[9 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(9);
				}
				});

				board[10 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(10);
				}
				});

				board[11 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(11);
				}
				});

				board[12 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(12);
				}
				});

				board[13 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(13);
				}
				});

				board[14 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(14);
				}
				});

				board[15 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(15);
				}
				});

				board[16 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(16);
				}
				});

				board[17 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(17);
				}
				});

				board[18 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(18);
				}
				});

				board[19 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(19);
				}
				});

				board[20 ].addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent arg0) {
					buttonFunc(20);
				}
				});

				board[21 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(21);
				}
				});

				board[22 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(22);
				}
				});

				board[23 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(23);
				}
				});

				board[24 ].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent arg0) {
					 buttonFunc(24);
				}
				});


		
	}
	
	/*
	 * @getters and setters
	 */

	public JButton getBoutton(int i) {
		return board[i];
	}
		
	public JButton[] getBoard() {
		return board;
	}

	public boolean FromtTo(int to, int from) {
		
		if(from == 0) {
			if(to == 1 || to == 6 || to == 5 )
				return true;
		}else if(from == 4) {
			if(to == 3 || to == 8 || to == 9 )
				return true;
		}else if(from == 20) {
			if(to == 15 || to == 16 || to == 21 )
				return true;
		}else if(from == 24) {
			if(to == 18 || to == 19 || to == 23 )
				return true;
		}else if(from == 1 || from == 2 || from == 3) {
			if(to == from -1 || to == from + 1 || to == from + 4 || to == from + 5 || to == from + 6 )
				return true;
		}else if(from == 21 || from == 22 || from == 23) {
			if(to == from -1 || to == from + 1 || to == from - 4 || to == from - 5 || to == from - 6 )
				return true;
		}else if(from == 5 || from == 10 || from == 15) {
			if(to == from + 1 || to == from + 5 || to == from - 5 || to == from - 4 || to == from + 6 )
				return true;
		}else if(from == 19 || from == 9 || from == 14) {
			if(to == from + 1 || to == from + 5 || to == from - 5 || to == from + 4 || to == from - 6 )
				return true;
		}else {
			if(to == from + 1 || to ==from - 1 || to == from + 4 || to == from - 4 || to ==from + 6 || to == from - 6 || to == from + 5 || to == from - 5)
				return true;
		}
		return false;
	}
		

}
