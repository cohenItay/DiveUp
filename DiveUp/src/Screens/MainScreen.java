package Screens;

import java.awt.EventQueue;
import java.sql.Connection;

import javax.swing.JFrame;

import org.sqlite.core.DB;

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
					Connection conn = dbConnection.Connect();
					dbConnection.addDiver(conn, "111111111", "a", "a", "1234","a@a.com","1111111111");
					dbConnection.addEmployee(conn, "111111111", "a", "a", "Senior","a@a.com","1111111111");
					dbConnection.runQuery(conn, "SELECT * from Employee");
					dbConnection.removeEmployee(conn, "111111111");
					dbConnection.addEmployee(conn, "111111111", "a", "a", "Senior","a@a.com","1111111111");
					dbConnection.runQuery(conn, "SELECT * from Employee");
					dbConnection.runQuery(conn, "SELECT * from Diver");
					
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
