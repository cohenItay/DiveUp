package Views;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.poi.hssf.record.DConRefRecord;

import Controllers.CoursesController;
import Controllers.DiverController;
import Controllers.DivesController;
import Models.Course;
import Models.Dive;
import Models.Diver;
import Models.Sale;
import Utilities.Reporter;
import Utilities.SendEmailTLS;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DTable;
import res.DTextField;
import res.DTextPane;
import res.UIConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/******** Customer Screen view ******/
public class CustomerScreen {

	private JFrame frame;
	private DefaultTableModel model;
	private DefaultTableModel modeldives;
	private JTable diversTable;
	private JTable divesTable;
	private JScrollPane divesPane;
	private List<Diver> diversList;
	private List<Dive> divesList;
	private DTable tableDesign;
	public boolean isFocused = true;
	public String currentDiver;
	private DiverController diversController;
	private DivesController divesControler;
	private CoursesController coursesController;
	private Diver currentDiverInstance;
	private DTextPane jtp;
	private Document doc;
	private JTextField filterTextField;
	/**
	 * Launch the application.
	 */
	
	
	public String getCurrentDiverID()
	{
		return currentDiver;
	}
	/*This Function will query the database to get all divers information and update in the table view*/
	public void updateDiversTable() {
		model.setRowCount(0);//Clearing the table data
		diversList = diversController.getDivers();//Getting divers list from the DB
		for(int i=0;i<diversList.size();i++)//For every diver add its information to the table
		{
			if(diversList.get(i).getId().contains(filterTextField.getText())   ||  diversList.get(i).getFirstName().contains(filterTextField.getText()) || diversList.get(i).getLastName().contains(filterTextField.getText()) 
					|| diversList.get(i).getLicenseID().contains(filterTextField.getText()) || diversList.get(i).getEmail().contains(filterTextField.getText()) || diversList.get(i).getPhone().contains(filterTextField.getText()))
			{
					model.addRow(new Object[] {diversList.get(i).getId(), diversList.get(i).getFirstName(),
							diversList.get(i).getLastName(),diversList.get(i).getLicenseID(),
							diversList.get(i).getEmail(),diversList.get(i).getPhone(),diversList.get(i).getInsurance()});
					}
		}
	}
	//Get all dives of the driver
	public void updateDiveBook(String id, int row)
	{	
		modeldives.setRowCount(0);
		DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
		divesList = divesControler.getDivesBook(id);
		if(divesList.size()>0)
		{
			divesPane.setVisible(true);
		}
		else
		{
			divesPane.setVisible(false);
		}
		for(int i=0;i<divesList.size();i++)
		{
			modeldives.addRow(new Object[] {divesList.get(i).getAirEnd(), divesList.get(i).getAirStart(), divesList.get(i).getEndTime(), divesList.get(i).getStartTime(), divesList.get(i).getMaxDepth(), outputFormatter.format(divesList.get(i).getDate()), divesList.get(i).getLocation(), divesList.get(i).getDiver(), divesList.get(i).getDiveNum() 
    		});
		}
		
		diversTable.setRowSelectionInterval(row, row);
		}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/*creating new CustomerScreen*/
					CustomerScreen window = new CustomerScreen();
					window.frame.setTitle("Divers Screen");
					Image image = ImageIO.read(this.getClass().getResource("/images/snorkel.PNG"));
					window.frame.setIconImage(image);

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
	public CustomerScreen() {
		initialize();
	}
	//function to pop a message to the screen
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
		frame = new JFrame();
		frame.setTitle("עמוד לקוחות");
		Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/images/snorkel.PNG"));
			frame.setIconImage(image);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frame.setBounds(UIConstants.miniScreenx, UIConstants.miniScreeny, UIConstants.miniScreenWidth,UIConstants.miniScreenHeight);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[20%,fill][20%:n,fill][20%,fill][20%,fill][20%,fill]", "[160px][30px:110px][250px:380][40px:n][50px:n,grow][40px:n][200px:n][40px:n][40px:n]"));
		frame.getContentPane().setBackground(Color.WHITE);
		/*Creating the table model and the table for the divers information*/
		String[] colHeadings = {"ת.ז","שם פרטי","שם משפחה","רישיון צלילה","מייל","טלפון","ביטוח"};
		int numRows = 0 ;
		model = new DefaultTableModel(numRows, colHeadings.length)
				{
			 		public boolean isCellEditable(int row, int column)
			 			{
			 				return false;//This causes all cells to be not editable
			 			}
				};
		model.setColumnIdentifiers(colHeadings);
		diversTable = new JTable(model);
		tableDesign= new DTable();
		diversTable.setRowSorter(new TableRowSorter(model));
		diversTable = tableDesign.designTable(diversTable,DTable.Mode.PRIMARY);

		
		/*Add listener in order to update the table data when pressed*/
		diversTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = diversTable.rowAtPoint(evt.getPoint());
		        int col = diversTable.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		        	currentDiver = "("+(String)model.getValueAt(row, 0)+")"+(String)model.getValueAt(row, 1);
		            updateDiversTable();
		            updateDiveBook((String)model.getValueAt(row, 0), row);
		            
		        }
		    }
		    
		    
		});
		
		JLabel titleLabel = new JLabel("עמוד לקוחות");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
		titleLabel.setForeground(UIConstants.SELECTED_BTN);
		frame.getContentPane().add(titleLabel, "cell 2 0,alignx center");
		
		
		
		JScrollPane scrollPane = new JScrollPane(diversTable);//add scroll bar to the table
		frame.getContentPane().add(scrollPane, "cell 0 2 5 1,growx");//add scroll bar to the frame
		
		/*Create buttons for activities*/
		DButton updateDiverButton = new DButton("\u05E2\u05D3\u05DB\u05D5\u05DF \u05E4\u05E8\u05D8\u05D9 \u05DC\u05E7\u05D5\u05D7",DButton.Mode.PRIMARY);
		
			updateDiverButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
			frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			updateDiverButton.setBackground(UIConstants.BTN_INLINE_HOVER_DEFUALT);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				updateDiverButton.setBackground(UIConstants.SELECTED_BTN);
			}
		});
		
		
		updateDiverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date today = new Date();
				List<Diver> l = diversController.getDivers();
				for(int i=0;i<l.size();i++)
				{
					List<Course> c = coursesController.getCoursesByID(l.get(i).getId());
					
					for( int j=0;j<c.size();j++)
					{
						
						long difference = c.get(j).getStartDay().getTime() - today.getTime();
					    float daysBetween = (difference / (1000*60*60*24));
						daysBetween++;
						if(daysBetween ==2)
						{
							SendEmailTLS send= new SendEmailTLS(l.get(i).getEmail(), "תזכורת קורס צלילה - "+c.get(j).getName()+"("+dateFormat.format(c.get(j).getStartDay())+")", "שלום "+l.get(i).getFirstName() +"<br>"
							+ "זוהי תזכורת בנוגע לקורס "+c.get(j).getName()+" שיתקיים בתאריך "+dateFormat.format(c.get(j).getStartDay())+"<br> !בהצלחה");	
						
						}
						
					}
				}
				
				
				
				Pattern pattern = Pattern.compile("\\((.*?)\\)");
				if(currentDiver != null)
				{
					Matcher matcher = pattern.matcher(currentDiver);
					String diverID="";
					if (matcher.find())
					{
						diverID = matcher.group(1);
					}
					currentDiverInstance = diversController.getDiverByID(diverID);
					CustomerEditScreen ce = new CustomerEditScreen(currentDiverInstance);
				}
				else
				{
					errorMessage("אנא בחר לקוח", "לא נבחר לקוח");
				}
			}
		});
		
		DButton bookButton = new DButton("ניהול יומן צלילות",DButton.Mode.PRIMARY);
		bookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String diverID ="";
				Pattern pattern = Pattern.compile("\\((.*?)\\)");
				if(currentDiver != null && !currentDiver.equals(""))
				{
					Matcher matcher = pattern.matcher(currentDiver);
					diverID="";
					if (matcher.find())
					{
						diverID = matcher.group(1);
					}
					DiveBookScreen dbs = new DiveBookScreen(diverID);
				
					
				
				}
				else
					errorMessage("לא נבחר צוללן", "שגיאה");
		}});
		frame.getContentPane().add(bookButton, "cell 1 4,growy");
		
		frame.getContentPane().add(updateDiverButton, "cell 2 4,grow");
		
		DButton addDiverButton = new DButton("\u05D4\u05D5\u05E1\u05E4\u05EA \u05E6\u05D5\u05DC\u05DC\u05DF",DButton.Mode.PRIMARY);
		addDiverButton.setText("הוספת לקוח");
		addDiverButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
			frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			addDiverButton.setBackground(UIConstants.BTN_INLINE_HOVER_DEFUALT);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				addDiverButton.setBackground(UIConstants.SELECTED_BTN);
			}
		});

		
		addDiverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				diversController.start();
			}
		});
		frame.getContentPane().add(addDiverButton, "cell 4 4,grow");
		
		DButton courseRegisterButton = new DButton("\u05D4\u05E8\u05E9\u05DE\u05D4 \u05DC\u05E7\u05D5\u05E8\u05E1",DButton.Mode.PRIMARY);
		courseRegisterButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
			frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			courseRegisterButton.setBackground(UIConstants.BTN_INLINE_HOVER_DEFUALT);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				courseRegisterButton.setBackground(UIConstants.SELECTED_BTN);
			}
		});

		
		courseRegisterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CourseRegistrationScreen register = new CourseRegistrationScreen(getCurrentDiverID());

			}
		});
		
	
		frame.getContentPane().add(courseRegisterButton, "cell 3 4,grow");
		
		
		
		String[] Headings = {"אוויר בסיום","אוויר בהתחלה","שעת סיום","שעת התחלה","עומק מקסימלי","תאריך","מיקום","צוללן","מזהה"};
		int numRow = 0 ;
		modeldives = new DefaultTableModel(numRow, Headings.length)
				{
			 		public boolean isCellEditable(int row, int column)
			 			{
			 				return false;//This causes all cells to be not editable
			 			}
				};
				
		modeldives.setColumnIdentifiers(Headings);
		divesTable= new JTable(modeldives);
	    tableDesign.designTable(divesTable,DTable.Mode.SECONDERY);
		
		
		JTableHeader header2 = divesTable.getTableHeader();
	     header2.setBackground(UIConstants.SELECTED_BTN);
	     header2.setForeground(Color.white);
		
		divesPane = new JScrollPane(divesTable);
		divesPane.setBackground(Color.lightGray);
		frame.getContentPane().add(divesPane, "cell 0 6 5 1,growy");
		divesPane.setVisible(false);
		
		diversController = new DiverController();
		divesControler = new DivesController();
		coursesController = new CoursesController();
		
		filterTextField = new JTextField();
		filterTextField.setFont(new Font("Tahoma", Font.BOLD, 16));
		filterTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateDiversTable();
				
			}
		});
		
		
		
		filterTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(filterTextField, "cell 4 3,alignx right,growy");
		filterTextField.setColumns(10);
		filterTextField.setText("");
		filterTextField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		updateDiversTable();
		frame.setVisible(true);
		filterTextField.requestFocusInWindow();
	}
}
