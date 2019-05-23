package Screens;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
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
import Controllers.CoursesController;
import Models.sqlConnection;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DLabel;
import res.DTable;
import res.UIConstants;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.Caret;
import javax.swing.event.PopupMenuEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;

public class CourseRegistrationScreen {

	
	private JFrame frame;
	private sqlConnection dbConnection;
	private JXDatePicker startDatePicker;
	private JXDatePicker endDatePicker;
	private JComboBox diversCombo;
	private DefaultTableModel model;
	private JTable coursesTable;
	private boolean table_loaded = false;
	private String diverID;
	private Integer currentCourse=-1;
	private CoursesController courseController;
	/**
	 * Launch the application.
	 */
	
	
	
	
public void updateCoursesList(int row)
{
	model.setRowCount(0);//Clearing the table data
	dbConnection = sqlConnection.getInstance();
    List<Course> courses = dbConnection.getCourses();
    DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
    for(int i=0;i<courses.size();i++)
    {
    	if(courses.get(i).getStartDay().compareTo(startDatePicker.getDate()) >=0  && courses.get(i).getEndDay().compareTo(endDatePicker.getDate())<= 0 && courses.get(i).getCurrentAmount() < courses.get(i).getMaxDivers())
    		model.addRow(new Object[] {courses.get(i).getId(), courses.get(i).getDesc(),
    				courses.get(i).getName(),courses.get(i).getInstructor(),
    				courses.get(i).getCurrentAmount(),courses.get(i).getMaxDivers(),courses.get(i).getPrice(),
    				outputFormatter.format(courses.get(i).getStartDay()),outputFormatter.format(courses.get(i).getEndDay())});
    		
    }
    if(row != -1)
    	coursesTable.setRowSelectionInterval(row, row);
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


public int getCurrentCourse()
{
	return currentCourse;
}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Creating new registration window
					CourseRegistrationScreen window = new CourseRegistrationScreen("");
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
	public CourseRegistrationScreen(String diverID) {
		this.diverID = diverID;
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
		frame.setSize(width/2+150, height/2+150);
		frame.setLocation(screenSize.width/2-frame.getSize().width/2-50, screenSize.height/2-frame.getSize().height/2-50);
		
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
		frame.getContentPane().setLayout(new MigLayout("", "[][][][][][-58.00][78.00][-60.00][46.00][366.00,grow][::80px]", "[174.00][fill][fill][14.00,grow][][][][15.00][][grow]"));
		
		JPanel datePanel = new JPanel();
		frame.getContentPane().add(datePanel, "cell 9 1,alignx right,growy");
		JPanel endDatePanel = new JPanel();
        frame.getContentPane().add(endDatePanel, "cell 9 2,alignx right,growy");
        
        frame.getContentPane().setBackground(Color.WHITE);
        datePanel.setBackground(Color.WHITE);
        endDatePanel.setBackground(Color.WHITE);
		
		/* adding fields to the form */
		startDatePicker = new JXDatePicker();
		startDatePicker.getEditor().setHorizontalAlignment(SwingConstants.RIGHT);
		startDatePicker.getEditor().setBackground(Color.WHITE);
		startDatePicker.getEditor().setForeground(Color.black);
		startDatePicker.getEditor().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		startDatePicker.getEditor().addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
			if(table_loaded)
				updateCoursesList(-1);
			}
		});
		
		
		startDatePicker.setDate(Calendar.getInstance().getTime());
		startDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        datePanel.add(startDatePicker);
        
        endDatePicker = new JXDatePicker();
        endDatePicker.getEditor().setHorizontalAlignment(SwingConstants.RIGHT);
        endDatePicker.getEditor().setHorizontalAlignment(SwingConstants.RIGHT);
		endDatePicker.getEditor().setBackground(Color.WHITE);
		endDatePicker.getEditor().setForeground(Color.black);
		endDatePicker.getEditor().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        endDatePicker.setDate(Calendar.getInstance().getTime());
		endDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        endDatePanel.add(endDatePicker);
        
                DLabel startDateLabel = new DLabel("\u05EA\u05D0\u05E8\u05D9\u05DA \u05D4\u05EA\u05D7\u05DC\u05D4");
        frame.getContentPane().add(startDateLabel, "cell 10 1,alignx right");
        
        
        
        DLabel endDateLabel = new DLabel("\u05EA\u05D0\u05E8\u05D9\u05DA \u05E1\u05D9\u05D5\u05DD");
        endDateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.getContentPane().add(endDateLabel, "cell 10 2,alignx right");
        
        
        String[] colHeadings = {"ID","Name","Desc","Instructor","Amount","Max Amount","Price","Start Date","End Date"};
		int numRows = 0 ;
		model = new DefaultTableModel(numRows, colHeadings.length)
				{
			 		public boolean isCellEditable(int row, int column)
			 			{
			 				return false;//This causes all cells to be not editable
			 			}
				};
		model.setColumnIdentifiers(colHeadings);
		coursesTable = new JTable(model);
		DTable designTable = new DTable();
		coursesTable = designTable.designTable(coursesTable);
		
		coursesTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = coursesTable.rowAtPoint(evt.getPoint());
		        int col = coursesTable.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		        	currentCourse = (Integer)model.getValueAt(row, 0);
		            updateCoursesList(row);
		            
		            
		        }
		    }
		    
		    
		});
		
		
		
        JScrollPane scrollPane = new JScrollPane(coursesTable);
        scrollPane.getViewport().setBackground(UIConstants.BTN_INLINE_FONT_DEFUALT);
        frame.getContentPane().add(scrollPane, "cell 4 0 7 1,grow");
        
        
        diversCombo = new JComboBox();
        diversCombo.setBackground(UIConstants.SELECTED_BTN);
        diversCombo.setForeground(Color.black);
        diversCombo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        updateDiversList();
        diversCombo.getModel().setSelectedItem(diverID);;
        diversCombo.setBackground(Color.WHITE);
        
        frame.getContentPane().add(diversCombo, "cell 9 3,growx");
        
        DLabel diverLabel = new DLabel("\u05E6\u05D5\u05DC\u05DC\u05DF");
        frame.getContentPane().add(diverLabel, "cell 10 3,alignx right");
        
        DButton confirmButton = new DButton("\u05D4\u05E8\u05E9\u05DE\u05D4",DButton.Mode.PRIMARY);
        confirmButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		String diverID="";
        		
        		Pattern pattern = Pattern.compile("\\((.*?)\\)");
        		Matcher matcher = pattern.matcher(diversCombo.getSelectedItem().toString());
        		
        		if (matcher.find())
        		{
        		    diverID = matcher.group(1);
        		}
        		
        		if(courseController.validateCourseRegistration(currentCourse, diverID))
        			courseController.registerNewCourse(currentCourse, diverID);
        		else
        		{
        			JOptionPane.showMessageDialog(null, "אנא בחר צוללן וקורס", "בעיה בהרשמה " + "פרטים חסרים", JOptionPane.ERROR_MESSAGE);
        		}
        	}
        });
        frame.getContentPane().add(confirmButton, "cell 9 8,growx");
        
        
        
        courseController = new CoursesController();
        updateCoursesList(-1);
        table_loaded = true;
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);// prevent closing all windows whsen closing this window
	}

}
