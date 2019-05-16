package Screens;

import java.awt.EventQueue;
import java.sql.Connection;

import javax.swing.JFrame;


public class MainScreen {

	private JFrame frame;
	public static sqlConnection dbConnection;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		dbConnection = sqlConnection.getInstance();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dbConnection.getDivers();
					MainScreen window = new MainScreen();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
