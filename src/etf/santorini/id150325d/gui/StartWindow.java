package etf.santorini.id150325d.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import etf.santorini.id150325d.game.Board;
import etf.santorini.id150325d.players.AdvancedOpponent;
import etf.santorini.id150325d.players.HumanPlayer;
import etf.santorini.id150325d.players.Player;
import etf.santorini.id150325d.players.SimpleOpponent;
import etf.santorini.id150325d.players.SmartOpponent;

@SuppressWarnings("serial")
public class StartWindow extends JFrame {
	private Board board = new Board();
	private Player player1, player2;
	private JLabel gameType = new JLabel("  Select game type: ");
	private JRadioButton humanVShuman;
	private JRadioButton humanVScomputer;
	private JRadioButton computerVScomputer;
	private ButtonGroup gameTypeButtons = new ButtonGroup();

	private JLabel playerOneLabel = new JLabel("  Player 1: ");
	private JLabel playerTwoLabel = new JLabel("  Player 2: ");

	private JTextField playerOneName = new JTextField("Player1");
	private JTextField playerTwoName = new JTextField("Player2");

	private JLabel difficultyLevel = new JLabel("  Select difficulty level: ");
	private JRadioButton easy;
	private JRadioButton hard;
	private JRadioButton normal;
	private ButtonGroup difficultyButtons = new ButtonGroup();
	private int dificultyLvl;

	private JLabel file = new JLabel("  Load from file:");
	private JRadioButton Yes;
	private JRadioButton No;
	private ButtonGroup fileLoad = new ButtonGroup();

	private JLabel load = new JLabel("  Game type: ");
	private JRadioButton stepBystep;
	private JRadioButton allDone;
	private ButtonGroup game = new ButtonGroup();
	private JButton newGame = new JButton("Start game");

	public StartWindow() {
		super();
		drawLayout();
	}

	private void drawLayout() {
		JFrame parentFrame = new JFrame("Start Game");
		JPanel labelPanel = new JPanel();
		JPanel buttonsPanel1 = new JPanel();
		JPanel buttonsPanel2 = new JPanel();
		JPanel newButtons = new JPanel();

		labelPanel.setLayout(new GridLayout(2, 2));
		buttonsPanel1.setLayout(new GridLayout(4, 1));
		buttonsPanel2.setLayout(new GridLayout(4, 1));
		newButtons.setLayout(new GridLayout(6, 1));

		buttonsPanel1.setSize(new Dimension(200, 200));
		buttonsPanel1.setSize(new Dimension(200, 200));
		newButtons.setSize(new Dimension(200, 200));

		parentFrame.setLayout(new BorderLayout(10, 1));
		parentFrame.setVisible(true);
		parentFrame.setSize(600, 300);
		// startFrame.setResizable(false);
		parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initRadioButton();

		labelPanel.add(playerOneLabel);
		labelPanel.add(playerOneName);
		labelPanel.add(playerTwoLabel);
		labelPanel.add(playerTwoName);
		buttonsPanel1.add(gameType);
		buttonsPanel2.add(difficultyLevel);
		buttonsPanel1.add(humanVShuman);
		buttonsPanel2.add(easy);
		buttonsPanel1.add(humanVScomputer);
		buttonsPanel2.add(normal);
		buttonsPanel1.add(computerVScomputer);
		buttonsPanel2.add(hard);
		buttonsPanel1.add(newGame);
		newButtons.add(file);
		newButtons.add(Yes);
		newButtons.add(No);
		newButtons.add(load);
		newButtons.add(stepBystep);
		newButtons.add(allDone);

		parentFrame.add(labelPanel, BorderLayout.NORTH);
		parentFrame.add(buttonsPanel1, BorderLayout.WEST);
		parentFrame.add(buttonsPanel2, BorderLayout.CENTER);
		parentFrame.add(newButtons, BorderLayout.EAST);
		parentFrame.add(newGame, BorderLayout.SOUTH);

	}

	private void initRadioButton() {
		humanVShuman = new JRadioButton("Human vs Human");
		humanVShuman.setActionCommand("Human vs Human");
		humanVShuman.setSelected(true);

		humanVScomputer = new JRadioButton("Human vs Computer");
		humanVScomputer.setActionCommand("Human vs Computer");
		humanVScomputer.setSelected(true);

		computerVScomputer = new JRadioButton("Computer vs Computer");
		computerVScomputer.setSelected(true);
		computerVScomputer.setActionCommand("Computer vs Computer");
		
		player1 = new HumanPlayer(1);
		player2 = new HumanPlayer(2);
		
		easy = new JRadioButton("easy");
		easy.setSelected(true);
		easy.setActionCommand("easy");

		normal = new JRadioButton("normal");
		normal.setSelected(true);
		normal.setActionCommand("normal");

		hard = new JRadioButton("hard");
		hard.setSelected(true);
		hard.setActionCommand("hard");

		Yes = new JRadioButton("Yes");
		No = new JRadioButton("No");
		Yes.setSelected(true);
		Yes.setActionCommand("Yes");
		No.setSelected(true);
		No.setActionCommand("No");

		stepBystep = new JRadioButton("step By step");
		allDone = new JRadioButton("all Done");

		stepBystep.setSelected(true);
		stepBystep.setActionCommand("StepByStep");
		allDone.setSelected(true);
		allDone.setActionCommand("allDone");
		Yes.setEnabled(false);
		No.setEnabled(false);
		stepBystep.setEnabled(false);
		allDone.setEnabled(false);
		easy.setEnabled(false);
		hard.setEnabled(false);
		normal.setEnabled(false);

		fileLoad.add(Yes);
		fileLoad.add(No);

		game.add(stepBystep);
		game.add(allDone);

		gameTypeButtons.add(humanVShuman);
		gameTypeButtons.add(humanVScomputer);
		gameTypeButtons.add(computerVScomputer);

		difficultyButtons.add(easy);
		difficultyButtons.add(hard);
		difficultyButtons.add(normal);
		addListeners();
	}
	
	/*
	 * @listener radio buttons & newGame
	 */
	private void addListeners() {

		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				board.setP1(player1);
				board.setP2(player2);
				board.getP1().setName(playerOneName.getText());
				board.getP2().setName(playerTwoName.getText());
				board.setDificulty(dificultyLvl);
				
				new GameBoard(board);
			}
		});

		humanVShuman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playerOneName.setEnabled(true);
				playerTwoName.setEnabled(true);
				playerOneName.setText("Player 1");
				playerTwoName.setText("Player 2");
				Yes.setEnabled(false);
				No.setEnabled(false);
				stepBystep.setEnabled(false);
				allDone.setEnabled(false);
				easy.setEnabled(false);
				hard.setEnabled(false);
				normal.setEnabled(false);
				dificultyLvl = -1;
				player1 = new HumanPlayer(1);
				player2 = new HumanPlayer(2);
				board.setBothComputer(false);
			}

		});

		humanVScomputer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerTwoName.setEnabled(false);
				playerOneName.setText("Player 1");
				playerTwoName.setText("Computer 1");
				playerOneName.setEnabled(true);
				Yes.setEnabled(false);
				No.setEnabled(false);
				stepBystep.setEnabled(false);
				allDone.setEnabled(false);
				easy.setEnabled(true);
				hard.setEnabled(true);
				normal.setEnabled(true);
				player1 = new HumanPlayer(1);			
				player2 = new AdvancedOpponent(2);
				board.setBothComputer(false);
			}

		});

		computerVScomputer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerTwoName.setEnabled(false);
				playerOneName.setEnabled(false);
				playerOneName.setText("Computer 1");
				playerTwoName.setText("Computer 2");
				Yes.setEnabled(true);
				No.setEnabled(true);
				stepBystep.setEnabled(true);
				allDone.setEnabled(true);
				easy.setEnabled(true);
				hard.setEnabled(true);
				normal.setEnabled(true);
				player1 = new AdvancedOpponent(1);
				player2 = new AdvancedOpponent(2);
				board.setBothComputer(true);
			}
		});

		easy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dificultyLvl = 0;
			}
		});

		normal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dificultyLvl = 1;
			}
		});

		hard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dificultyLvl = 2;
			}
		});
		
		stepBystep.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				board.setStepBystep(true);
			}
			
		});
		allDone.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				board.setStepBystep(false);
			}			
		});

	}

}
