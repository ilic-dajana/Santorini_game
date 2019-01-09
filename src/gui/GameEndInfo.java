package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameEndInfo extends JFrame {
	private String poruka;
	private JLabel porukaLabela;
	private JFrame frame;

	public GameEndInfo(String msg, JButton[] board) {
		super();
		frame = new JFrame("Game Info");
		porukaLabela = new JLabel(msg);
		frame.setLayout(new GridBagLayout());
		JPanel panel = new JPanel();
		panel.add(porukaLabela);
		frame.setVisible(true);
		frame.add(panel, new GridBagConstraints());
		frame.setSize(200, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		for (int i = 0; i < 25; i++) {
			board[i].setEnabled(false);
		}
		// frame.setResizable(false);
	}
}
