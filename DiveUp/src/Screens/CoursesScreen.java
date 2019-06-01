package Screens;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

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
import res.UIConstants;

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

	
	
	
	public int getCurrentcourseID()
	{
		return currentCourse;
	}
	/*This Function will query the database to get all divers information and update in the table view*/
	public void updateCoursesList(int row)
	{
		model.setRowCount(0);//Clearing the table data
		dbConnection = new courseSqlQueries();
	    List<Course> courses = dbConnection.getCourses();
	    DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
	    for(int i=0;i<courses.size();i++)
	    {
	    		    		
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
		frame = new JFrame(); 
		frame.setTitle("מסך קורסים");
		Image image;
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
		/*Creating the table model and the table for the divers information*/
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
		DButton updateDiverButton = new DButton("\u05E2\u05D3\u05DB\u05D5\u05DF \u05E4\u05E8\u05D8\u05D9 \u05DC\u05E7\u05D5\u05D7",DButton.Mode.PRIMARY);
		updateDiverButton.setText("\u05E2\u05D3\u05DB\u05D5\u05DF \u05E4\u05E8\u05D8\u05D9 \u05E7\u05D5\u05E8\u05E1");
		updateDiverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		frame.getContentPane().add(updateDiverButton, "cell 2 2,alignx right,growy");
		
		DButton addDiverButton = new DButton("\u05D4\u05D5\u05E1\u05E4\u05EA \u05E6\u05D5\u05DC\u05DC\u05DF",DButton.Mode.PRIMARY);
		addDiverButton.setText("\u05D4\u05D5\u05E1\u05E4\u05EA \u05E7\u05D5\u05E8\u05E1");
				addDiverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			CourseAddScreen cadd=new CourseAddScreen();	
			}
		});
		frame.getContentPane().add(addDiverButton, "cell 4 2,alignx trailing,growy");
		
		DButton courseRegisterButton = new DButton("\u05D4\u05E8\u05E9\u05DE\u05D4 \u05DC\u05E7\u05D5\u05E8\u05E1",DButton.Mode.PRIMARY);	
		courseRegisterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CourseRegistrationScreen courseRegistration = new CourseRegistrationScreen("");
			}
		});
		
	
		frame.getContentPane().add(courseRegisterButton, "cell 3 2,alignx right,growy");
		frame.setVisible(true);
		courseController = new CoursesController();
		updateCoursesList(currentCourse);
		
	}

	}


