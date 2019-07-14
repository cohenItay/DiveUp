package Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Controllers.EmployeeController;
import Models.Course;
import Models.Dive;
import Models.Diver;
import Models.Employee;
import Utilities.Reporter;
import Utilities.SendEmailTLS;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DTable;
import res.DTextPane;
import res.UIConstants;

public class EmployeesScreen {

	private JFrame frame;
	private DefaultTableModel model;
	private JTable employeesTable;
	private List<Employee> employeesList;
	private DTable tableDesign;
	public boolean isFocused = true;
	public String currentEmployee="";
	private EmployeeController employeesController;
	private Employee currentEmployeeInstance;
	private DTextPane jtp;
	private Document doc;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeesScreen window = new EmployeesScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String getCurrentEmployeeID()
	{
		return currentEmployee;
	}
	
	
	/*This Function will query the database to get all e,ployees information and update in the table view*/
	public void updateEmployeesTable(int row) {
		model.setRowCount(0);//Clearing the table data
		employeesList = employeesController.getEmployees();//Getting divers list from the DB
		for(int i=0;i<employeesList.size();i++)//For every employee add its information to the table
		{
				model.addRow(new Object[] {employeesList.get(i).getSalary(), employeesList.get(i).getPhone(),
				employeesList.get(i).getEmail(),
				employeesList.get(i).getSeniority(),employeesList.get(i).getLastName(),employeesList.get(i).getFirstName(),employeesList.get(i).getId()});
		}
		if(currentEmployee!="")
			employeesTable.setRowSelectionInterval(row, row);
	}
	
	
	
	
	/**
	 * Create the application.
	 */
	public EmployeesScreen() {
		initialize();
	}

	
	
	public void message(String infoMessage, String titleBar)
    {
		jtp = new DTextPane();
		
	    doc = jtp.getStyledDocument();
	    try {
			doc.insertString(doc.getLength(), infoMessage, new SimpleAttributeSet());
		    JOptionPane.showMessageDialog(null, jtp, "Title", JOptionPane.INFORMATION_MESSAGE);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
	public void errorMessage(String infoMessage, String titleBar)
    {
		jtp = new DTextPane();
	    doc = jtp.getDocument();
	    SimpleAttributeSet right = new SimpleAttributeSet();
	    StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
	    ((StyledDocument) doc).setParagraphAttributes(0, doc.getLength(), right, false);
	    try {
			doc.insertString(doc.getLength(), infoMessage, new SimpleAttributeSet());
		    JOptionPane.showMessageDialog(null, jtp, "Title", JOptionPane.ERROR_MESSAGE);
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
		frame.getContentPane().setLayout(new MigLayout("", "[20%,fill][20%,fill][20%,fill][20%,fill][20%,fill]", "[160px][30px:110px][250px:380][40px:n][50px:n,grow][40px:n][200px:n][260][250]"));
		frame.getContentPane().setBackground(Color.WHITE);
		/*Creating the table model and the table for the divers information*/
		String[] colHeadings = {"משכורת","טלפון","מייל","ותק","שם משפחה","שם","ת.ז"};
		int numRows = 0 ;
		model = new DefaultTableModel(numRows, colHeadings.length)
				{
			 		public boolean isCellEditable(int row, int column)
			 			{
			 				return false;//This causes all cells to be not editable
			 			}
				};
		model.setColumnIdentifiers(colHeadings);
		employeesTable = new JTable(model);
		tableDesign= new DTable();
		employeesTable = tableDesign.designTable(employeesTable,DTable.Mode.PRIMARY);

		
		/*Add listener in order to update the table data when pressed*/
		employeesTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = employeesTable.rowAtPoint(evt.getPoint());
		        int col = employeesTable.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		        	currentEmployee = "("+(String)model.getValueAt(row, 6)+")"+(String)model.getValueAt(row, 5);
		            updateEmployeesTable(row);
		           
		            
		        }
		    }
		    
		    
		});
		
		
		
		
		
		JScrollPane scrollPane = new JScrollPane(employeesTable);//add scroll bar to the table
		frame.getContentPane().add(scrollPane, "cell 0 2 5 1,growx");//add scroll bar to the frame
		
		JLabel titleLabel = new JLabel("ניהול עובדים");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
		titleLabel.setForeground(UIConstants.SELECTED_BTN);
		frame.getContentPane().add(titleLabel, "cell 2 0,alignx center");
		
		
		
		/*Create buttons for activities*/
		DButton updateEmployeeButton = new DButton("\u05E2\u05D3\u05DB\u05D5\u05DF \u05E4\u05E8\u05D8\u05D9 \u05DC\u05E7\u05D5\u05D7",DButton.Mode.PRIMARY);
		updateEmployeeButton.setText("עדכון פרטי עובד");
		
			updateEmployeeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
			frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			updateEmployeeButton.setBackground(UIConstants.BTN_INLINE_HOVER_DEFUALT);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				updateEmployeeButton.setBackground(UIConstants.SELECTED_BTN);
			}
		});
		
		
		updateEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				
				Pattern pattern = Pattern.compile("\\((.*?)\\)");
				if(currentEmployee == null)
				{
					errorMessage("נא לבחור עובד", "לא נבחר עובד");
				}
				else
				{
					Matcher matcher = pattern.matcher(currentEmployee);
					String employeeID="";
					if (matcher.find())
					{
						employeeID = matcher.group(1);
					}
				
					currentEmployeeInstance = employeesController.getEmployeeByID(employeeID);
					EmployeeEditScreen ce = new EmployeeEditScreen(currentEmployeeInstance);
				}
			}
		});
		
		frame.getContentPane().add(updateEmployeeButton, "cell 2 4,grow");
		
		DButton addEmployeeButton = new DButton("\u05D4\u05D5\u05E1\u05E4\u05EA \u05E6\u05D5\u05DC\u05DC\u05DF",DButton.Mode.PRIMARY);
		addEmployeeButton.setText("הוספת עובד");
		

		
		addEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EmployeeAddScreen ea = new EmployeeAddScreen();
			}
		});
		frame.getContentPane().add(addEmployeeButton, "cell 4 4,grow");
		
		DButton messageButton = new DButton("\u05D4\u05E8\u05E9\u05DE\u05D4 \u05DC\u05E7\u05D5\u05E8\u05E1",DButton.Mode.PRIMARY);
		messageButton.setText("שליחת מייל");
		messageButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
			frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			messageButton.setBackground(UIConstants.BTN_INLINE_HOVER_DEFUALT);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				messageButton.setBackground(UIConstants.SELECTED_BTN);
			}
		});

		
		messageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
					JPanel pan=new JPanel();
					pan.setBorder(new EmptyBorder(2, 2, 2, 2));
				    pan.setLayout(new BorderLayout(0, 0));
					pan.setSize(UIConstants.miniScreenWidth/3, UIConstants.miniScreenHeight/3);
					JTextArea textToSend = new JTextArea();
					textToSend.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
					pan.add(textToSend);
					JScrollPane scrollPane = new JScrollPane(textToSend, 
			                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
			                   JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					textToSend.setRows(30);
					textToSend.setColumns(50);
					
			        pan.add(scrollPane, BorderLayout.CENTER);
			        DButton sendButton = new DButton("שלח", DButton.Mode.PRIMARY);
					pan.add(sendButton,BorderLayout.SOUTH);
					
					JDialog dialog = new JDialog();
					dialog.setBounds(UIConstants.miniScreenx/2+UIConstants.width/4, UIConstants.miniScreeny/2+UIConstants.height/4, UIConstants.miniScreenWidth/2,UIConstants.miniScreenHeight/2);
					dialog.add(pan);
					dialog.setTitle("שליחת מייל");
					Image image;
					try {
						image = ImageIO.read(this.getClass().getResource("/images/snorkel.PNG"));
						dialog.setIconImage(image);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dialog.setVisible(true);
					dialog.requestFocusInWindow();
					textToSend.requestFocusInWindow();
					sendButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							
							
							List<Employee> elist = employeesController.getEmployees();
							for(int i=0;i<elist.size();i++)
							{
								SendEmailTLS se = new SendEmailTLS(elist.get(i).getEmail(), "הודעה מהנהלת DiveUp", textToSend.getText());	
							}
							
							dialog.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));//close window
						}
					});
				
				
	
				
			}
		});
		
	
		frame.getContentPane().add(messageButton, "cell 3 4,grow");
		
		
		
		DButton exitButton = new DButton("יציאה",DButton.Mode.PRIMARY);
		frame.getContentPane().add(exitButton, "cell 2 8,growx");
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
			frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			exitButton.setBackground(UIConstants.BTN_INLINE_HOVER_DEFUALT);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				exitButton.setBackground(UIConstants.SELECTED_BTN);
			}
		});
		
		employeesController = new EmployeeController();
		updateEmployeesTable(-1);
		frame.setVisible(true);
		
	}


}
