package Screens;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Window.Type;
import net.miginfocom.swing.MigLayout;
import res.DButton;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;

public class ManagerScreen {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerScreen window = new ManagerScreen();
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
	public ManagerScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("DiveUp"); 
		frame.setBounds(100, 100, 1200, 900);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[200][200][186.00][200][200]", "[][90px][][80px][][80px][][80px][][80px][][80px][]"));
		
		JLabel titleLabel = new JLabel("DiveUp - \u05E2\u05DE\u05D5\u05D3 \u05DE\u05E0\u05D4\u05DC");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
		frame.getContentPane().add(titleLabel, "cell 2 0,alignx center");
		
		DButton diversButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05DC\u05E7\u05D5\u05D7\u05D5\u05EA",DButton.Mode.PRIMARY);
		diversButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			CustomerScreen c = new CustomerScreen();
			}
		});
		frame.getContentPane().add(diversButton, "cell 2 2,growx");
		
		DButton coursesButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05DE\u05DC\u05D0\u05D9",DButton.Mode.PRIMARY);
		coursesButton.setText("\u05E0\u05D9\u05D4\u05D5\u05DC \u05E7\u05D5\u05E8\u05E1\u05D9\u05DD");
		frame.getContentPane().add(coursesButton, "cell 2 4,growx");
		
		DButton salesButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05E7\u05D5\u05E8\u05E1\u05D9\u05DD",DButton.Mode.PRIMARY);
		salesButton.setText("\u05E0\u05D9\u05D4\u05D5\u05DC \u05DE\u05DB\u05D9\u05E8\u05D5\u05EA");
		frame.getContentPane().add(salesButton, "cell 2 6,growx");
		
		DButton itemsButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05D4\u05D6\u05DE\u05E0\u05D5\u05EA",DButton.Mode.PRIMARY);
		itemsButton.setText("\u05E0\u05D9\u05D4\u05D5\u05DC \u05DE\u05DC\u05D0\u05D9");
		frame.getContentPane().add(itemsButton, "cell 2 8,growx");
		
		DButton reportsButton = new DButton("\u05D3\u05D5\u05D7\u05D5\u05EA",DButton.Mode.PRIMARY);
		
		frame.getContentPane().add(reportsButton, "cell 2 10,growx");
		
		DButton exitButton = new DButton("\u05D9\u05E6\u05D9\u05D0\u05D4",DButton.Mode.PRIMARY);
		frame.getContentPane().add(exitButton, "cell 2 12,growx");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));//close window
			}
		});
	}


}
