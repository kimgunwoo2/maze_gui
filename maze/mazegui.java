package 실습;

import java.awt.*;
import javax.swing.*;


public class mazegui extends JFrame {
	ImageIcon backgd = new ImageIcon("background.jpg");
	JPanel center_status = new JPanel();
	JPanel top_status = new JPanel();
	JPanel bottom_status = new JPanel();

	public mazegui() {
		super("==미로게임== ");

		setSize(900, 900);
		
		getContentPane().setLayout(new BorderLayout(0, 0));

		getContentPane().add(center_status, BorderLayout.CENTER);
		maingui main = new maingui(center_status, top_status, bottom_status);
		main.mainpaint();
		getContentPane().add(top_status, BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(bottom_status, BorderLayout.SOUTH);
		setVisible(true);
		center_status.requestFocus();
		center_status.setFocusable(true);

	}

	public static void main(String[] args) {
		new mazegui();
	}
}
