package Screens;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import org.jdesktop.swingx.JXDatePicker;

import Classes.Course;
import Classes.Diver;
import net.miginfocom.swing.MigLayout;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;
import javax.swing.event.PopupMenuEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class CourseRegistrationScreen {

	private JFrame frame;
	private sqlConnection dbConnection;
	private JComboBox<String> coursesList;
	private JXDatePicker startDatePicker;
	private JXDatePicker endDatePicker;
	private JComboBox diversCombo;
	/**
	 * Launch the application.
	 */
	

public void updateCoursesList()
{
	coursesList.removeAllItems();
	dbConnection = sqlConnection.getInstance();
    List<Course> courses = dbConnection.getCourses();
    DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
    
    for(int i=0;i<courses.size();i++)
    {
    	if(courses.get(i).getStartDay().compareTo(startDatePicker.getDate()) >=0  && courses.get(i).getEndDay().compareTo(endDatePicker.getDate())<= 0 && courses.get(i).getCurrentAmount() < courses.get(i).getMaxDivers())
    		
    		coursesList.addItem(courses.get(i).getName() + " " + outputFormatter.format(courses.get(i).getEndDay()) +  " - " + outputFormatter.format(courses.get(i).getStartDay()) + " ("+ courses.get(i).getId()+")");
    }
}

public void updateDiversList()
{
	
	dbConnection = sqlConnection.getInstance();//connection to the DB
	List<Diver>diversList = dbConnection.getDivers();
	((JLabel)diversCombo.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
    for(int i =0 ; i<diversList.size();i++)
    {
    	diversCombo.addItem( "(" + diversList.get(i).getId()+")"+diversList.get(i).getFirstName());
    }
}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Creating new registration window
					CourseRegistrationScreen window = new CourseRegistrationScreen();
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
	public CourseRegistrationScreen() {
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
		startDatePicker = new JXDatePicker();
		
		
		startDatePicker.setDate(Calendar.getInstance().getTime());
		startDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        datePanel.add(startDatePicker);
        
        endDatePicker = new JXDatePicker();
        endDatePicker.setDate(Calendar.getInstance().getTime());
		endDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        endDatePanel.add(endDatePicker);
        
        JLabel startDateLabel = new JLabel("\u05EA\u05D0\u05E8\u05D9\u05DA \u05D4\u05EA\u05D7\u05DC\u05D4");
        frame.getContentPane().add(startDateLabel, "cell 10 1,alignx right");
        
        
        
        JLabel endDateLabel = new JLabel("\u05EA\u05D0\u05E8\u05D9\u05DA \u05E1\u05D9\u05D5\u05DD");
        endDateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.getContentPane().add(endDateLabel, "cell 10 2,alignx right");
        
        
     
        coursesList = new JComboBox<String>();
        ((JLabel)coursesList.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);

        coursesList.addPopupMenuListener(new PopupMenuListener() {
        	public void popupMenuCanceled(PopupMenuEvent arg0) {
        	}
        	public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
        	 updateCoursesList();
        	}
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
        
        
        
        
        
        frame.getContentPane().add(coursesList, "cell 7 3 2 1,growx");
        
       
                
                
        JLabel nameLabel = new JLabel("\u05E7\u05D5\u05E8\u05E1");
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.getContentPane().add(nameLabel, "cell 10 3,alignx right");
        
        
        diversCombo = new JComboBox();
        updateDiversList();
        frame.getContentPane().add(diversCombo, "cell 7 4 2 1,growx");
        
        JLabel diverLabel = new JLabel("\u05E6\u05D5\u05DC\u05DC\u05DF");
        frame.getContentPane().add(diverLabel, "cell 10 4,alignx right");
        
        JButton confirmButton = new JButton("\u05D4\u05D5\u05E1\u05E4\u05D4");
        confirmButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		
        		int courseID = 0;
        		String diverID = "";
        		Matcher m = Pattern.compile("\\((.*?)\\)").matcher(coursesList.getSelectedItem().toString());
        		if(m.find()) {
        		    courseID = Integer.valueOf(m.group(1));
        		}
        		m = Pattern.compile("\\((.*?)\\)").matcher(diversCombo.getSelectedItem().toString());
        		if(m.find()) {
        		    diverID = m.group(1);
        		}
        		dbConnection.registerCourse(courseID,diverID);
        	}
        });
        frame.getContentPane().add(confirmButton, "cell 7 6 2 1,growx");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);// prevent closing all windows whsen closing this window
	}

}
