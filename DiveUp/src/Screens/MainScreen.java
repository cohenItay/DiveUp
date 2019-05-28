package Screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Models.SendEmailTLS;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.UIConstants;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainScreen {

	private JFrame frmDiveup;
	private JLabel clockLabel;
	private SendEmailTLS se;
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
		frmDiveup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		frmDiveup.setTitle("DiveUp"); 
		frmDiveup.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		
		
		
		Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/images/snorkel.PNG"));
			frmDiveup.setIconImage(image);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		frmDiveup.getContentPane().setBackground(Color.WHITE);
		frmDiveup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDiveup.getContentPane().setLayout(new MigLayout("", "[20%][5%][20%][20%][20%][20%]", "[][100px][30px:n][60px:n][80px][60px:n][80px][60px:n][80px][60px:n][80px][60px:n][]"));
		frmDiveup.setTitle("DiveUp");
		clockLabel = new JLabel("");
		clockLabel.setFont(new Font("Tahoma", Font.BOLD, 45));
		clockLabel.setForeground(UIConstants.HOVER_SELECTED_MAIN_BACKGROUND);
		frmDiveup.getContentPane().add(clockLabel, "cell 3 1,alignx center");
		Date d = new Date();
		Runnable helloRunnable = new Runnable() {
		    public void run() {
		    	Date d = new Date();
		    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy (HH:mm:ss)");
		    	clockLabel.setText(dateFormat.format(d));
		    }
		};

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(helloRunnable, 0, 1, TimeUnit.SECONDS);
		
		
		
		JLabel titleLabel = new JLabel("DiveUp - \u05E2\u05DE\u05D5\u05D3 \u05E8\u05D0\u05E9\u05D9");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 60));
		titleLabel.setForeground(UIConstants.SELECTED_BTN);
		frmDiveup.getContentPane().add(titleLabel, "cell 3 0,alignx center");
		
		DButton diversButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05DC\u05E7\u05D5\u05D7\u05D5\u05EA",DButton.Mode.PRIMARY);
		diversButton.setFont(new Font("Dialog", Font.BOLD, 40));
		diversButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			CustomerScreen c = new CustomerScreen();
			}
		});
		

		diversButton.setText("\u05DC\u05E7\u05D5\u05D7\u05D5\u05EA");
		frmDiveup.getContentPane().add(diversButton, "cell 3 3,grow");
		
		DButton coursesButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05DE\u05DC\u05D0\u05D9",DButton.Mode.PRIMARY);
		coursesButton.setFont(new Font("Dialog", Font.BOLD, 40));
		coursesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			CoursesScreen cs = new CoursesScreen();
			}
		});
		coursesButton.setText("\u05E7\u05D5\u05E8\u05E1\u05D9\u05DD");
		frmDiveup.getContentPane().add(coursesButton, "cell 3 5,grow");
		
		DButton salesButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05E7\u05D5\u05E8\u05E1\u05D9\u05DD",DButton.Mode.PRIMARY);
		salesButton.setFont(new Font("Dialog", Font.BOLD, 40));
		salesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			SaleScreen sc = new SaleScreen();
			}
		});
		salesButton.setText("\u05DE\u05DB\u05D9\u05E8\u05D5\u05EA");
		frmDiveup.getContentPane().add(salesButton, "cell 3 7,grow");
		
		DButton adminButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05D4\u05D6\u05DE\u05E0\u05D5\u05EA",DButton.Mode.PRIMARY);
		adminButton.setFont(new Font("Dialog", Font.BOLD, 40));
		adminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "אזור מוגן בסיסמא לטובת הבדיקה נא הכנס admin","דרושה סיסמא", JOptionPane.INFORMATION_MESSAGE);
				String password= JOptionPane.showInputDialog("אנא הכנס סיסמא");
				ManagerScreen m ;
				if(password != null)
					if(password.equals("admin"))
						m = new ManagerScreen();
				else
				{
					JOptionPane.showMessageDialog(null,"סיסמא שגויה","סיסמה שגויה", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		adminButton.setText("\u05DB\u05E0\u05D9\u05E1\u05EA \u05DE\u05E0\u05D4\u05DC");
		frmDiveup.getContentPane().add(adminButton, "cell 3 9,grow");
		
		DButton exitButton = new DButton("\u05D3\u05D5\u05D7\u05D5\u05EA",DButton.Mode.PRIMARY);
		exitButton.setFont(new Font("Dialog", Font.BOLD, 40));
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmDiveup.dispatchEvent(new WindowEvent(frmDiveup, WindowEvent.WINDOW_CLOSING));//close window
			}
		});
		exitButton.setText("\u05D9\u05E6\u05D9\u05D0\u05D4");
		frmDiveup.getContentPane().add(exitButton, "cell 3 11,grow");
		frmDiveup.pack();
	}

}


