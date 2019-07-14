package Views;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import org.jdesktop.swingx.JXDatePicker;

import Controllers.CoursesController;
import Controllers.DiverController;
import Controllers.DivesController;
import Models.Course;
import Models.Diver;
import Models.courseSqlQueries;
import Models.diverSqlQueries;
import Models.sqlConnection;
import Utilities.SendEmailTLS;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DLabel;
import res.DTable;
import res.DTextField;
import res.DTextPane;
import res.UIConstants;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
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
import java.awt.event.WindowEvent;

import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.TextUI;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.View;
import javax.swing.text.Position.Bias;
import javax.swing.event.PopupMenuEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/******** Course registration Screen view ******/

public class CourseRegistrationScreen {

	
	private JFrame frame;
	private diverSqlQueries dbConnection;
	private courseSqlQueries dbConnection2;
	private JXDatePicker startDatePicker;
	private JXDatePicker endDatePicker;
	private JComboBox diversCombo;
	private DefaultTableModel model;
	private JTable coursesTable;
	private boolean table_loaded = false;
	private String diverID;
	private Integer currentCourse=-1;
	private CoursesController courseController;
	private DiverController diverController;
	private JTextPane jtp;
	private Document doc;
	private JTextField filterTextField;
	private int courseID=-1;
	/**
	 * Launch the application.
	 */
	
	
	
//getting courses list from the DB, get only the courses that are not full , and fit to the selected dates	
public void updateCoursesList(int row)
{
	model.setRowCount(0);//Clearing the table data
	dbConnection2 = new courseSqlQueries();
    List<Course> courses = dbConnection2.getCourses();
    DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
    if(courseID ==-1)
	{
	    for(int i=0;i<courses.size();i++)
	    {
	    	
	    	
		    	if(compareDates(courses.get(i).getStartDay(), startDatePicker.getDate())>=0 && compareDates(courses.get(i).getStartDay(), endDatePicker.getDate())<=0  && compareDates(courses.get(i).getEndDay(), endDatePicker.getDate())<=0 && compareDates(courses.get(i).getEndDay(), startDatePicker.getDate())>=0)
		    	{
		    	if(courses.get(i).getCurrentAmount() < courses.get(i).getMaxDivers() && (courses.get(i).getName().contains(filterTextField.getText()) || courses.get(i).getDesc().contains(filterTextField.getText()) || courses.get(i).getInstructor().contains(filterTextField.getText())))
		    		model.addRow(new Object[] {outputFormatter.format(courses.get(i).getEndDay()),outputFormatter.format(courses.get(i).getStartDay()),courses.get(i).getPrice(),courses.get(i).getMaxDivers(),
		    				courses.get(i).getCurrentAmount(),courses.get(i).getInstructor(),courses.get(i).getDesc(),courses.get(i).getName()
		    				,courses.get(i).getId()});
		    	}
	    }
	}
    	else
    	{
    		Course c=null;
			try {
			model.setRowCount(0);
				c = courseController.getCourseByID(courseID);
				model.addRow(new Object[] {c.getId(), c.getDesc(),
	    				c.getName(),c.getInstructor(),
	    				c.getCurrentAmount(),c.getMaxDivers(),c.getPrice(),
	    				outputFormatter.format(c.getStartDay()),outputFormatter.format(c.getEndDay())});
				    	coursesTable.setRowSelectionInterval(0, 0);
				    
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   for(int i=0;i<courses.size();i++)
			    {
			    	
			    	
				    	if(compareDates(courses.get(i).getStartDay(), startDatePicker.getDate())>=0 && compareDates(courses.get(i).getStartDay(), endDatePicker.getDate())<=0  && compareDates(courses.get(i).getEndDay(), endDatePicker.getDate())<=0 && compareDates(courses.get(i).getEndDay(), startDatePicker.getDate())>=0)
				    	{
				    	if(courses.get(i).getCurrentAmount() < courses.get(i).getMaxDivers() && (courses.get(i).getName().contains(filterTextField.getText()) || courses.get(i).getDesc().contains(filterTextField.getText()) || courses.get(i).getInstructor().contains(filterTextField.getText())))
				    		model.addRow(new Object[] {courses.get(i).getId(), courses.get(i).getDesc(),
				    				courses.get(i).getName(),courses.get(i).getInstructor(),
				    				courses.get(i).getCurrentAmount(),courses.get(i).getMaxDivers(),courses.get(i).getPrice(),
				    				outputFormatter.format(courses.get(i).getStartDay()),outputFormatter.format(courses.get(i).getEndDay())});
				    	}
			    }
			   
    		
    	}
   
    if(row != -1)
    	coursesTable.setRowSelectionInterval(row, row);
}

//function to calculate number of days between two dates
public long compareDates(Date d1, Date d2)
{
	long diff = d1.getTime() - d2.getTime();
    long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	return days;
}

//Update divers list combobox
public void updateDiversList()
{
	
	dbConnection = new diverSqlQueries();//connection to the DB
	List<Diver>diversList = dbConnection.getDivers();
	((JLabel)diversCombo.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
    for(int i =0 ; i<diversList.size();i++)
    {
    	diversCombo.addItem( "(" + diversList.get(i).getId()+")"+diversList.get(i).getFirstName());
    }
}

//get the selected course from the JTable
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
	//Constructor that called from the divers screen
	public CourseRegistrationScreen(String diverID) {
		this.diverID = diverID;
		initialize();
	}
	//constructor the called from courses screen
	public CourseRegistrationScreen(int courseID) {
		this.courseID = courseID;
		initialize();
	}

	//function to pop a message to screen
	public void message(String infoMessage, String titleBar)
    {
		jtp = new DTextPane();
		
	    doc = jtp.getStyledDocument();
	    try {
			doc.insertString(doc.getLength(), infoMessage, new SimpleAttributeSet());
		    JOptionPane.showMessageDialog(null, jtp, titleBar, JOptionPane.INFORMATION_MESSAGE);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
	//function to pop an error message to the screen
	public void errorMessage(String infoMessage, String titleBar)
    {
		jtp = new DTextPane();
	    doc = jtp.getDocument();
	    SimpleAttributeSet right = new SimpleAttributeSet();
	    StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
	    ((StyledDocument) doc).setParagraphAttributes(0, doc.getLength(), right, false);
	    try {
			doc.insertString(doc.getLength(), infoMessage, new SimpleAttributeSet());
		    JOptionPane.showMessageDialog(null, jtp, titleBar, JOptionPane.ERROR_MESSAGE);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();//create jframe
		/* set the size and the location of the frame */
		frame.setBounds(UIConstants.miniScreenx, UIConstants.miniScreeny, UIConstants.miniScreenWidth,UIConstants.miniScreenHeight);
		
		//Title and icon add
		frame.setTitle("הרשמה לקורס");
		Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/images/snorkel.PNG"));
			frame.setIconImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][60px:n][60px:n][60px:n][60px:n][60px:n][60px:n][80px:n][60px:n][60px:n][60px:n][300px:n,grow]", "[40px:n][][::200px][][30px:n][][14.00][][][][15.00][40px:n][]"));
		
		
		
		//Creating form fields 
		
		
		JLabel titleLabel = new JLabel("הרשמה לקורס");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		titleLabel.setForeground(UIConstants.SELECTED_BTN);
		frame.getContentPane().add(titleLabel, "cell 7 0,alignx center");
	
		
		JPanel datePanel = new JPanel();
		frame.getContentPane().add(datePanel, "cell 10 6,alignx right,growy");
		JPanel endDatePanel = new JPanel();
        frame.getContentPane().add(endDatePanel, "cell 10 7,alignx right,growy");
        
        frame.getContentPane().setBackground(Color.WHITE);
        datePanel.setBackground(Color.WHITE);
        endDatePanel.setBackground(Color.WHITE);
		
        
        
        Calendar c = Calendar.getInstance();
		c.setTime(new Date());
        
		/* adding fields to the form */
		startDatePicker = new JXDatePicker();
		startDatePicker.getEditor().setFont(new Font("Tahoma", Font.PLAIN, 24));
		startDatePicker.getEditor().setHorizontalAlignment(SwingConstants.RIGHT);
		startDatePicker.getEditor().setBackground(Color.WHITE);
		startDatePicker.getEditor().setForeground(UIConstants.BORDER_DARK);
		startDatePicker.getEditor().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		//check that selected startdate is before selected end date
		startDatePicker.getEditor().addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				Date d = new Date();
				long diff = startDatePicker.getDate().getTime() - d.getTime();
                long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        		if(days < 0)
        		{
        			errorMessage("תאריך התחלה לא תקין", "בחירה שגויה");
        			c.setTime(new Date());
        			startDatePicker.setDate(c.getTime());
        			c.add(Calendar.DATE, 1);
        			endDatePicker.setDate(c.getTime());
        			c.add(Calendar.DATE, -1);
        			
        		}
        		else
        		{
        			if(table_loaded)
    					updateCoursesList(-1);
    				
        		}
				
			}
		});
		
	
		
		startDatePicker.setDate(Calendar.getInstance().getTime());
		startDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        datePanel.add(startDatePicker);
        
        
        
		c.add(Calendar.DATE, 1);
        endDatePicker = new JXDatePicker();
        //check that selected end date is after selected start date
        endDatePicker.getEditor().addPropertyChangeListener(new PropertyChangeListener() {
        	public void propertyChange(PropertyChangeEvent evt) {
        	if(table_loaded)
        	{
        		long diff = endDatePicker.getDate().getTime() - startDatePicker.getDate().getTime();
                long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        		if(days < 0)
        		{
        			errorMessage("תאריך התחלה וסיום לא תקינים", "בחירה שגויה");
        			c.setTime(new Date());
        			startDatePicker.setDate(c.getTime());
        			c.add(Calendar.DATE, 1);
        			endDatePicker.setDate(c.getTime());
        			c.add(Calendar.DATE, -1);
        		    
        		}
        		updateCoursesList(-1);
        	}
        	}
        });
        endDatePicker.getEditor().setFont(new Font("Tahoma", Font.PLAIN, 24));
        endDatePicker.getEditor().setHorizontalAlignment(SwingConstants.RIGHT);
        endDatePicker.getEditor().setHorizontalAlignment(SwingConstants.RIGHT);
		endDatePicker.getEditor().setBackground(Color.WHITE);
		endDatePicker.getEditor().setForeground(UIConstants.BORDER_DARK);
		endDatePicker.getEditor().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        endDatePicker.setDate(c.getTime());
		endDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        endDatePanel.add(endDatePicker);
        
                DLabel startDateLabel = new DLabel("\u05EA\u05D0\u05E8\u05D9\u05DA \u05D4\u05EA\u05D7\u05DC\u05D4");
                startDateLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
                startDateLabel.setForeground(UIConstants.BORDER_DARK);
        frame.getContentPane().add(startDateLabel, "cell 11 6,alignx right");
        
        
        
        DLabel endDateLabel = new DLabel("\u05EA\u05D0\u05E8\u05D9\u05DA \u05E1\u05D9\u05D5\u05DD");
        endDateLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
        endDateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        endDateLabel.setForeground(UIConstants.BORDER_DARK);
        frame.getContentPane().add(endDateLabel, "cell 11 7,alignx right");
        
        
        String[] colHeadings = {"תאריך סיום","תאריך התחלה","מחיר","כמות מקסימלית","כמות","מדריך","תיאור","שם","מזהה"};
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
		coursesTable = designTable.designTable(coursesTable,DTable.Mode.PRIMARY);
		//select the course that selected from the JTable
		coursesTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = coursesTable.rowAtPoint(evt.getPoint());
		        int col = coursesTable.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		        	currentCourse = (Integer)model.getValueAt(row, 8);
		            updateCoursesList(row);
		            
		        }
		    }
		    
		    
		});
		
		
		
        JScrollPane scrollPane = new JScrollPane(coursesTable);
        scrollPane.getViewport().setBackground(UIConstants.BTN_INLINE_FONT_DEFUALT);
        frame.getContentPane().add(scrollPane, "cell 0 2 12 1,grow");
        
        
        diversCombo = new JComboBox();
        diversCombo.setFont(new Font("Tahoma", Font.PLAIN, 22));
        diversCombo.setBackground(UIConstants.SELECTED_BTN);
        diversCombo.setForeground(Color.black);
        diversCombo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        updateDiversList();
        diversCombo.getModel().setSelectedItem(diverID);;
        diversCombo.setBackground(Color.WHITE);
        diversCombo.setForeground(UIConstants.BORDER_DARK);
        frame.getContentPane().add(diversCombo, "cell 10 8,alignx center");
        
        DLabel diverLabel = new DLabel("\u05E6\u05D5\u05DC\u05DC\u05DF");
        diverLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
        diverLabel.setForeground(UIConstants.BORDER_DARK);
        frame.getContentPane().add(diverLabel, "cell 11 8,alignx right");
        
        
        DButton confirmButton = new DButton("\u05D4\u05E8\u05E9\u05DE\u05D4",DButton.Mode.PRIMARY);
        confirmButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		//if diver is selected from the list
        		if(!diversCombo.getSelectedItem().toString().isEmpty())
        		{
        			//get seperate diverID and name from the selected item
	        		String diverID="";
	        		String diverName = "";
	        		Pattern pattern = Pattern.compile("\\((.*?)\\)");
	        		Matcher matcher = pattern.matcher(diversCombo.getSelectedItem().toString());
	        		if (matcher.find())
	        		{
	        		    diverID = matcher.group(1);
	        		    diverName = diversCombo.getSelectedItem().toString().replace("("+matcher.group(1)+")","");
	        		}
	        		
	        		//check that the diver is having an insurance
	        		if(diverController.getDiverByID(diverID).getInsurance().equals("YES"))
	        		{
	        				//input validation
			        		if(courseController.validateCourseRegistration(currentCourse, diverID))
			        		{
			        				boolean isRegistered = false;
			        				boolean succeed=true;
			        				List<Course> clientCourses = courseController.getCoursesByID(diverID);
			        				//check that the diver is not registered to this course , or to another course on the selected dates
			        				for(int i =0;i<clientCourses.size();i++)
			        				{
			        				
			        					if(clientCourses.get(i).getId() == currentCourse)
			        						isRegistered = true;
			        					
			        					try {
			        						if(compareDates(courseController.getCourseByID(currentCourse).getStartDay(), clientCourses.get(i).getStartDay())> 0 &&  compareDates(courseController.getCourseByID(currentCourse).getStartDay(), clientCourses.get(i).getEndDay())< 0  || compareDates( courseController.getCourseByID(currentCourse).getEndDay(), clientCourses.get(i).getEndDay())<0  && compareDates( courseController.getCourseByID(currentCourse).getEndDay(), clientCourses.get(i).getStartDay())>0 )
			        							isRegistered=true;
			        					} catch (ParseException e) {
			        						// TODO Auto-generated catch block
			        						e.printStackTrace();
									}
			        			}
			        			
			        			
			        			if(!isRegistered)
			        				succeed = courseController.registerNewCourse(currentCourse, diverID);
			        			else
			        			{
			        				errorMessage("הלקוח כבר רשום לקורס בתאריך זה", "שגיאה בהרשמה לקורס");
			        				succeed = false;
			        			}
			        				
			        			
			        			//if registration succeed, send a mail about the course registration
			        			if(succeed)
			        			{
			
			        					SendEmailTLS sm = new SendEmailTLS(diverController.getDiverByID(diverID).getEmail(), "ברכות על ההרשמה לקורס "+courseController.getCourseName(currentCourse), "היי "+diverName +"<br> תודה שנרשמת לקורס "+courseController.getCourseName(currentCourse)
			        					+" בתאריך " +courseController.getCourseStartDay(currentCourse)+"<br>"+"מצפים לראותך");
			        					message("ההרשמה התבצעה בהצלחה","ההרשמה התבצעה בהצלחה");
			        					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));//close window
			        			}
			        			
			        		
			        			try {
									if(courseController.getCourseByID(currentCourse).getCurrentAmount()==courseController.getCourseByID(currentCourse).getMaxDivers())
									{
										errorMessage("הקורס הנוכחי מלא", "בעיה בהרשמה");
									
									}
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			        		updateCoursesList(-1);
			        		}
			        		else
			        		{
			        			errorMessage("אנא בחר צוללן וקורס", "פרטים חסרים");
			        		}
			        	
	        		}
	        		else
	        		{
	        			errorMessage("לקוח לא מבוטח", "שגיאה בהרשמה");
	        		}
	        	}
        		else
        		{
        			errorMessage("אנא בחר צוללן וקורס", "חסרים פרטים");
        		}
        	}
	        });
        frame.getContentPane().add(confirmButton, "cell 11 11,grow");
        
        
        diverController = new DiverController();
        courseController = new CoursesController();
        updateCoursesList(-1);
        table_loaded = true;
	
		
		filterTextField = new JTextField();
		filterTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			updateCoursesList(-1);
			}
		});
		
		filterTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		
		frame.getContentPane().add(filterTextField, "cell 10 4 2 1,alignx right");
		filterTextField.setColumns(10);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);// prevent closing all windows whsen closing this window
		filterTextField.setText("");
		filterTextField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		filterTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		filterTextField.requestFocusInWindow();
		courseController= new CoursesController();
	}

}
