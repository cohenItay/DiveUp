package Screens;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Classes.Dive;
import Classes.Diver;
import Controllers.CoursesController;
import Controllers.DiverController;
import Controllers.DivesController;

import java.awt.Window.Type;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.UIConstants;

import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManagerScreen {

	private JFrame frame;
	private JLabel clockLabel;
	private DivesController dc;
	private DiverController dController;
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

	
	public long compareDates(Date d1, Date d2)
	{
		long diff = d1.getTime() - d2.getTime();
	    long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		return days;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();

		frame.setTitle("DiveUp"); 
		Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/images/snorkel.PNG"));
			frame.setIconImage(image);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[20%][34%][20%][20%][20%][20%]", "["+UIConstants.width/100+"px:n][]["+UIConstants.width/30+"px]["+UIConstants.width/100+"px:n]["+UIConstants.width/38+"px:n]["+UIConstants.width/30+"px]["+UIConstants.width/38+"px:n]["+UIConstants.width/30+"px]["+UIConstants.width/38+"px:n]["+UIConstants.width/30+"px]["+UIConstants.width/38+"px:n]["+UIConstants.width/30+"px]["+UIConstants.width/38+"px:n]["+UIConstants.width/30+"px]["+UIConstants.width/38+"px:n]"));
		frame.setResizable(false);
		JLabel titleLabel = new JLabel("DiveUp - \u05E2\u05DE\u05D5\u05D3 \u05DE\u05E0\u05D4\u05DC");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 65));
		titleLabel.setForeground(UIConstants.SELECTED_BTN);
		frame.getContentPane().add(titleLabel, "cell 2 0,alignx center");
		clockLabel = new JLabel("");
		clockLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		clockLabel.setForeground(UIConstants.HOVER_SELECTED_MAIN_BACKGROUND);
		frame.getContentPane().add(clockLabel, "cell 2 2,alignx center");
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
		diversButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
				List<Diver> diversList = dController.getDivers();
				Date today = new Date();
				for(int i=0;i<diversList.size();i++)
				{
					if(diversList.get(i)!= null)
					{
						List<Dive> divesList = dc.getDivesBook(diversList.get(i).getId());
						if(divesList.size()>0)
						{
							System.out.println("Diver is :"+diversList.get(i).getFirstName()+" Last Dive:"+outputFormatter.format(divesList.get(divesList.size()-1).getDate()));
							long days = compareDates(today, divesList.get(divesList.size()-1).getDate());
							System.out.println("Days since last dive "+ days);
						}
					
					}
					
				}
			}
		});
		frame.getContentPane().add(diversButton, "cell 2 4,grow");
		
		DButton coursesButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05DE\u05DC\u05D0\u05D9",DButton.Mode.PRIMARY);
		coursesButton.setFont(new Font("Dialog", Font.BOLD, 40));
		coursesButton.setText("\u05E0\u05D9\u05D4\u05D5\u05DC \u05E7\u05D5\u05E8\u05E1\u05D9\u05DD");
		frame.getContentPane().add(coursesButton, "cell 2 6,grow");
		
		DButton employeesButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05E7\u05D5\u05E8\u05E1\u05D9\u05DD",DButton.Mode.PRIMARY);
		employeesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeesScreen es = new EmployeesScreen();
			}
		});
		employeesButton.setFont(new Font("Dialog", Font.BOLD, 40));
		employeesButton.setText("\u05E0\u05D9\u05D4\u05D5\u05DC \u05E2\u05D5\u05D1\u05D3\u05D9\u05DD");
		frame.getContentPane().add(employeesButton, "cell 2 8,grow");
		
		DButton itemsButton = new DButton("\u05E0\u05D9\u05D4\u05D5\u05DC \u05D4\u05D6\u05DE\u05E0\u05D5\u05EA",DButton.Mode.PRIMARY);
		itemsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InventoryScreen is = new InventoryScreen();
			}
		});
		itemsButton.setFont(new Font("Dialog", Font.BOLD, 40));
		itemsButton.setText("\u05E0\u05D9\u05D4\u05D5\u05DC \u05DE\u05DC\u05D0\u05D9");
		frame.getContentPane().add(itemsButton, "cell 2 10,grow");
		
		DButton reportsButton = new DButton("\u05D3\u05D5\u05D7\u05D5\u05EA",DButton.Mode.PRIMARY);
		reportsButton.setFont(new Font("Dialog", Font.BOLD, 40));
		reportsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReportsScreen r = new ReportsScreen();
			}
		});
		
		frame.getContentPane().add(reportsButton, "cell 2 12,grow");
		
		DButton exitButton = new DButton("\u05D9\u05E6\u05D9\u05D0\u05D4",DButton.Mode.PRIMARY);
		exitButton.setFont(new Font("Dialog", Font.BOLD, 40));
		frame.getContentPane().add(exitButton, "cell 2 14,grow");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));//close window
			}
		});
		dc = new DivesController();
		dController = new DiverController();
		frame.setVisible(true);
		frame.setBounds(50, 50, UIConstants.width-100, UIConstants.height-100);
	}
	

}
