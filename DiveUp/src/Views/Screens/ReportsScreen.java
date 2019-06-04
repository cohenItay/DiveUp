package Views.Screens;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Controllers.Reporter;
import net.miginfocom.swing.MigLayout;
import Views.DButton;
import Views.UIConstants;

import javax.imageio.ImageIO;

public class ReportsScreen {

	private JFrame frame;
	private Reporter r;
	private JLabel clockLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReportsScreen window = new ReportsScreen();
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
	public ReportsScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("DiveUp"); 
		Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/Resources/snorkel.PNG"));
			frame.setIconImage(image);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[20%,fill][20%,fill][20%][20%,fill][20%,fill]", "[][90px][30px:n][50px:n][80px][50px:n][80px][50px:n][80px][50px:n][80px][50px:n][80px][50px:n][]"));
		
		JLabel titleLabel = new JLabel("DiveUp - \u05D3\u05D5\u05D7\u05D5\u05EA");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 65));
		titleLabel.setForeground(UIConstants.SELECTED_BTN);
		frame.getContentPane().add(titleLabel, "cell 2 0,alignx center");
		clockLabel = new JLabel("");
		clockLabel.setFont(new Font("Tahoma", Font.BOLD, 45));
		clockLabel.setForeground(UIConstants.HOVER_SELECTED_MAIN_BACKGROUND);
		frame.getContentPane().add(clockLabel, "cell 2 1,alignx center");
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
		
		
		
		
		
		
		
		DButton diversButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05DC\u05E7\u05D5\u05D7\u05D5\u05EA",DButton.Mode.PRIMARY);
		diversButton.setFont(new Font("Dialog", Font.BOLD, 40));
		diversButton.setText("\u05DC\u05E7\u05D5\u05D7\u05D5\u05EA");
		diversButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			CustomerScreen c = new CustomerScreen();
			}
		});
		frame.getContentPane().add(diversButton, "cell 2 3,grow");
		
		DButton coursesButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05DE\u05DC\u05D0\u05D9",DButton.Mode.PRIMARY);
		coursesButton.setFont(new Font("Dialog", Font.BOLD, 40));
		coursesButton.setText("\u05E7\u05D5\u05E8\u05E1\u05D9\u05DD");
		frame.getContentPane().add(coursesButton, "cell 2 5,grow");
		
		DButton salesButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05E7\u05D5\u05E8\u05E1\u05D9\u05DD",DButton.Mode.PRIMARY);
		salesButton.setFont(new Font("Dialog", Font.BOLD, 40));
		salesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				r.createSalesReport(r.getSaleController().getSales(), "ALL");
			}
		});
		salesButton.setText("\u05DE\u05DB\u05D9\u05E8\u05D5\u05EA");
		frame.getContentPane().add(salesButton, "cell 2 7,grow");
		
		DButton itemsButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05D4\u05D6\u05DE\u05E0\u05D5\u05EA",DButton.Mode.PRIMARY);
		itemsButton.setFont(new Font("Dialog", Font.BOLD, 40));
		itemsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			r.createEmployeesReport(r.getEmployeeController().getEmployees(), "ALL");
			}
		});
		itemsButton.setText("\u05E2\u05D5\u05D1\u05D3\u05D9\u05DD");
		frame.getContentPane().add(itemsButton, "cell 2 9,grow");
		
		DButton folderButton = new DButton("\u05EA\u05D9\u05E7\u05D9\u05D9\u05EA \u05D3\u05D5\u05D7\u05D5\u05EA",DButton.Mode.PRIMARY);
		folderButton.setFont(new Font("Dialog", Font.BOLD, 40));
		folderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().open(new File(UIConstants.reportsPath));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		frame.getContentPane().add(folderButton, "cell 2 11,grow");
		
		DButton exitButton = new DButton("\u05D9\u05E6\u05D9\u05D0\u05D4",DButton.Mode.PRIMARY);
		exitButton.setFont(new Font("Dialog", Font.BOLD, 40));
		frame.getContentPane().add(exitButton, "cell 2 13,grow");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));//close window
			}
		});
		r = new Reporter();
		frame.setBounds(50, 50, UIConstants.width-100, UIConstants.height-100);
		frame.setVisible(true);
		
	}
	}


