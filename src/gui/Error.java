package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Error extends JFrame{
	private String msg="";
	private JFrame frame = new JFrame();
	private JLabel porukaLabela = new JLabel();
	
	
	
	public Error(String msg) {
		super();
		this.msg = msg;
		frame = new JFrame("Game Info");
		porukaLabela = new JLabel(msg);
		frame.setLayout(new GridBagLayout());
		JPanel panel = new JPanel();
		panel.add(porukaLabela);
		frame.setVisible(true);
		frame.add(panel, new GridBagConstraints());
		frame.setSize(600, 100);
		frame.pack();
		}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
