package Screens;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Classes.Course;
import Classes.Dive;
import Classes.Diver;
import Controllers.CoursesController;
import Controllers.DiverController;
import Controllers.DivesController;
import Models.courseSqlQueries;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DTable;
import res.DTextPane;
import res.UIConstants;
/******** Courses Screen view ******/
public class CoursesScreen {

	private JFrame frame;
	private DefaultTableModel model;
	private JTable coursesTable;
	private JScrollPane divesPane;
	private List<Course> coursesList;
	private DTable tableDesign;
	public int currentCourse=-1;
	private courseSqlQueries dbConnection;
	private CoursesController courseController;
	private DivesController divesControler;
	private DTextPane jtp;
	private Document doc;
	private JTextField filterTextField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CoursesScreen window = new CoursesScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//function to pop a message
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
	//function to pop up an error message
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
	
	public int getCurrentcourseID()
	{
		return currentCourse;
	}
	/*This Function will query the database to get all courses information and update in the table view*/
	public void updateCoursesList(int row)
	{
		model.setRowCount(0);//Clearing the table data
		dbConnection = new courseSqlQueries();
	    List<Course> courses = dbConnection.getCourses();
	    DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
	    for(int i=0;i<courses.size();i++)
	    {
	    	if(courses.get(i).getName().contains(filterTextField.getText()) || courses.get(i).getDesc().contains(filterTextField.getText()) || courses.get(i).getInstructor().contains(filterTextField.getText()))	
		        model.addRow(new Object[] {outputFormatter.format(courses.get(i).getEndDay()),outputFormatter.format(courses.get(i).getStartDay()),
		    				courses.get(i).getPrice(),courses.get(i).getMaxDivers(),courses.get(i).getCurrentAmount(),courses.get(i).getInstructor(),courses.get(i).getDesc(),
		    				courses.get(i).getName(),courses.get(i).getId()
		    		});
	    		
	    }
	    if(row != -1)
	    	coursesTable.setRowSelectionInterval(row, row);
	}
	
	/**
	 * Create the application.
	 */
	public CoursesScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame(); //create JFrame
		frame.setTitle("מסך קורסים");//change frame title
		Image image;//add icon to the frame
		try {
			image = ImageIO.read(this.getClass().getResource("/images/snorkel.PNG"));
			frame.setIconImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setBounds(UIConstants.miniScreenx, UIConstants.miniScreeny, UIConstants.miniScreenWidth,UIConstants.miniScreenHeight);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[400,grow,fill][400,grow,fill][300px:n,fill][300px:n,fill][300px:n,fill]", "[270px:n][30px:n][50px:n][][270,grow][260,grow][250,grow]"));
		frame.getContentPane().setBackground(Color.WHITE);
		/*Creating the table model and the table for the Courses information*/
		String[] colHeadings = {"תאריך סיום","תאריך התחלה","מחיר","כמות מקסימלית","כמות נוכחית","מדריך","תיאור","שם","מזהה"};
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
		tableDesign= new DTable();
		coursesTable = tableDesign.designTable(coursesTable,DTable.Mode.PRIMARY);

		
		/*Add listener in order to update the table data when pressed*/
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
		
		
		
		JScrollPane scrollPane = new JScrollPane(coursesTable);//add scroll bar to the table
		frame.getContentPane().add(scrollPane, "cell 0 0 5 1,growx");//add scroll bar to the frame
		
		/*Create buttons for activities*/
		DButton updateCourseButton = new DButton("\u05E2\u05D3\u05DB\u05D5\u05DF \u05E4\u05E8\u05D8\u05D9 \u05DC\u05E7\u05D5\u05D7",DButton.Mode.PRIMARY);
		updateCourseButton.setText("\u05E2\u05D3\u05DB\u05D5\u05DF \u05E4\u05E8\u05D8\u05D9 \u05E7\u05D5\u05E8\u05E1");
		updateCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(currentCourse !=-1)
				{
					try {
						CourseEditScreen ce = new CourseEditScreen(courseController.getCourseByID(currentCourse));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					errorMessage("אנא בחר קורס", "שגיאה");
				}
			}
		});
		//Add filter field to search on the JTable
		filterTextField = new JTextField();
		filterTextField.setFont(new Font("Tahoma", Font.BOLD, 16));
		filterTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateCoursesList(-1);
				
			}
		});
		
		
		
		filterTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(filterTextField, "cell 4 1,alignx right,growy");
		filterTextField.setColumns(10);
		filterTextField.setText("");
		filterTextField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		frame.getContentPane().add(updateCourseButton, "cell 2 2,alignx right,growy");
		
		DButton addCourseButton = new DButton("\u05D4\u05D5\u05E1\u05E4\u05EA \u05E6\u05D5\u05DC\u05DC\u05DF",DButton.Mode.PRIMARY);
		addCourseButton.setText("\u05D4\u05D5\u05E1\u05E4\u05EA \u05E7\u05D5\u05E8\u05E1");
				addCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			CourseAddScreen cadd=new CourseAddScreen();	
			}
		});
		frame.getContentPane().add(addCourseButton, "cell 4 2,alignx trailing,growy");
		
		DButton courseRegisterButton = new DButton("\u05D4\u05E8\u05E9\u05DE\u05D4 \u05DC\u05E7\u05D5\u05E8\u05E1",DButton.Mode.PRIMARY);	
		courseRegisterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CourseRegistrationScreen courseRegistration = new CourseRegistrationScreen(currentCourse);
			}
		});
	
		frame.getContentPane().add(courseRegisterButton, "cell 3 2,alignx right,growy");
		frame.setVisible(true);
		filterTextField.requestFocusInWindow();
		courseController = new CoursesController();
		updateCoursesList(currentCourse);
		
	}

	}


