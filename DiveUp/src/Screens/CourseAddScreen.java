package Screens;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;

import org.jdesktop.swingx.JXDatePicker;

import net.miginfocom.swing.MigLayout;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CourseAddScreen {

	private JFrame frame;
	private JTextField amountTextField;
	private JTextField descTextField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Creating new registration window
					CourseAddScreen window = new CourseAddScreen();
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
	public CourseAddScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		/* set the size and the location of the frame */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		frame.setSize(width/2, height/2);
		frame.setLocation(screenSize.width/2-frame.getSize().width/2, screenSize.height/2-frame.getSize().height/2);
		
		//Title and icon add
		frame.setTitle("Course Registration");
		Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/images/snorkel.PNG"));
			frame.setIconImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][][][][][-58.00][78.00][-60.00,grow][46.00,fill][31.00][]", "[][fill][fill][14.00][][][][15.00][][grow]"));
		
		JPanel datePanel = new JPanel();
		frame.getContentPane().add(datePanel, "cell 7 1 2 1,grow");
		JPanel endDatePanel = new JPanel();
        frame.getContentPane().add(endDatePanel, "cell 7 2 2 1,grow");
        
		
		
		/* adding fields to the form */
		JXDatePicker startDatePicker = new JXDatePicker();
		startDatePicker.setDate(Calendar.getInstance().getTime());
		startDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        datePanel.add(startDatePicker);
        
        JXDatePicker endDatePicker = new JXDatePicker();
        endDatePicker.setDate(Calendar.getInstance().getTime());
		endDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        endDatePanel.add(endDatePicker);
        
        JLabel startDateLabel = new JLabel("\u05EA\u05D0\u05E8\u05D9\u05DA \u05D4\u05EA\u05D7\u05DC\u05D4");
        frame.getContentPane().add(startDateLabel, "cell 10 1,alignx right");
        
        
        
        JLabel endDateLabel = new JLabel("\u05EA\u05D0\u05E8\u05D9\u05DA \u05E1\u05D9\u05D5\u05DD");
        endDateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.getContentPane().add(endDateLabel, "cell 10 2,alignx right");
        
        JList coursesList = new JList();
        frame.getContentPane().add(coursesList, "cell 7 3 2 1,grow");
        
        JLabel nameLabel = new JLabel("\u05E7\u05D5\u05E8\u05E1");
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.getContentPane().add(nameLabel, "cell 10 3,alignx right");
        
        JList employeesList = new JList();
        frame.getContentPane().add(employeesList, "cell 7 4 2 1,grow");
        
        JLabel employeeLabel = new JLabel("\u05DE\u05D3\u05E8\u05D9\u05DA");
        frame.getContentPane().add(employeeLabel, "cell 10 4,alignx right");
        
        amountTextField = new JTextField();
        frame.getContentPane().add(amountTextField, "cell 7 5 2 1,growx");
        amountTextField.setColumns(10);
        
        JLabel amountLabel = new JLabel("\u05DE\u05E1\u05E4\u05E8 \u05DE\u05E9\u05EA\u05EA\u05E4\u05D9\u05DD \u05DE\u05E7\u05E1\u05D9\u05DE\u05DC\u05D9");
        frame.getContentPane().add(amountLabel, "cell 10 5,alignx right");
        
        descTextField = new JTextField();
        frame.getContentPane().add(descTextField, "cell 7 6 2 1,growx");
        descTextField.setColumns(10);
        
        JLabel descLabel = new JLabel("\u05EA\u05D9\u05D0\u05D5\u05E8");
        frame.getContentPane().add(descLabel, "cell 10 6,alignx right");
        
        JButton confirmButton = new JButton("\u05D4\u05D5\u05E1\u05E4\u05D4");
        confirmButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        	}
        });
        frame.getContentPane().add(confirmButton, "cell 7 8 2 1,growx");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);// prevent closing all windows when closing this window
	}

}
