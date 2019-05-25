package Screens;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import res.DButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class MainScreen {

	private JFrame frmDiveup;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen window = new MainScreen();
					window.frmDiveup.setVisible(true);
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
		frmDiveup = new JFrame();
		frmDiveup.setTitle("DiveUp"); 
		frmDiveup.setBounds(100, 100, 1200, 900);
		frmDiveup.getContentPane().setBackground(Color.WHITE);
		frmDiveup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDiveup.getContentPane().setLayout(new MigLayout("", "[200][200][186.00][200][200]", "[][100px][][80px][][80px][][80px][][80px][][]"));
		
		JLabel titleLabel = new JLabel("DiveUp - \u05E2\u05DE\u05D5\u05D3 \u05E8\u05D0\u05E9\u05D9");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
		frmDiveup.getContentPane().add(titleLabel, "cell 2 0,alignx center");
		
		DButton diversButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05DC\u05E7\u05D5\u05D7\u05D5\u05EA",DButton.Mode.PRIMARY);
		diversButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			CustomerScreen c = new CustomerScreen();
			}
		});
		diversButton.setText("\u05DC\u05E7\u05D5\u05D7\u05D5\u05EA");
		frmDiveup.getContentPane().add(diversButton, "cell 2 2,growx");
		
		DButton coursesButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05DE\u05DC\u05D0\u05D9",DButton.Mode.PRIMARY);
		coursesButton.setText("\u05E7\u05D5\u05E8\u05E1\u05D9\u05DD");
		frmDiveup.getContentPane().add(coursesButton, "cell 2 4,growx");
		
		DButton salesButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05E7\u05D5\u05E8\u05E1\u05D9\u05DD",DButton.Mode.PRIMARY);
		salesButton.setText("\u05DE\u05DB\u05D9\u05E8\u05D5\u05EA");
		frmDiveup.getContentPane().add(salesButton, "cell 2 6,growx");
		
		DButton adminButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05D4\u05D6\u05DE\u05E0\u05D5\u05EA",DButton.Mode.PRIMARY);
		adminButton.setText("\u05DB\u05E0\u05D9\u05E1\u05EA \u05DE\u05E0\u05D4\u05DC");
		frmDiveup.getContentPane().add(adminButton, "cell 2 8,growx");
		
		DButton exitButton = new DButton("\u05D3\u05D5\u05D7\u05D5\u05EA",DButton.Mode.PRIMARY);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmDiveup.dispatchEvent(new WindowEvent(frmDiveup, WindowEvent.WINDOW_CLOSING));//close window
			}
		});
		exitButton.setText("\u05D9\u05E6\u05D9\u05D0\u05D4");
		frmDiveup.getContentPane().add(exitButton, "cell 2 10,growx");
		
	}

}


