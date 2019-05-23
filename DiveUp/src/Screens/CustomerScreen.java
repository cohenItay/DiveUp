package Screens;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Dive;
import Classes.Diver;
import Controllers.DiverController;
import Controllers.DivesController;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DTable;
import res.UIConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

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
		model.addRow(new Object[] {diversList.get(i).getId(), diversList.get(i).getFirstName(),
				diversList.get(i).getLastName(),diversList.get(i).getLicenseID(),
				diversList.get(i).getEmail(),diversList.get(i).getPhone(),diversList.get(i).getInsurance()});
		}
	}
	
	public void updateDiveBook(String id, int row)
	{	
		modeldives.setRowCount(0);
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
			modeldives.addRow(new Object[] {divesList.get(i).getDiveNum(),divesList.get(i).getLocation(),divesList.get(i).getDate()
					,divesList.get(i).getMaxDepth(),divesList.get(i).getMaxDepth(),divesList.get(i).getStartTime()
					,divesList.get(i).getEndTime(),divesList.get(i).getAirStart(),divesList.get(i).getAirEnd()});
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[400,grow,fill][400,grow,fill][400,grow,fill][400,grow,fill][400,grow,fill]", "[270,grow][106.00,grow][270,grow][260,grow][250,grow]"));
		frame.getContentPane().setBackground(Color.WHITE);
		/*Creating the table model and the table for the divers information*/
		String[] colHeadings = {"ID","First Name","Last Name","License ID","Email","Phone","Insurance"};
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
		diversTable = tableDesign.designTable(diversTable);

		
		/*Add listener in order to update the table data when pressed*/
		diversTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = diversTable.rowAtPoint(evt.getPoint());
		        int col = diversTable.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		        	currentDiver = "("+(String)model.getValueAt(row, 1)+")"+(String)model.getValueAt(row, 0);
		            updateDiversTable();
		            updateDiveBook((String)model.getValueAt(row, 0), row);
		            
		        }
		    }
		    
		    
		});
		
		
		
		JScrollPane scrollPane = new JScrollPane(diversTable);//add scroll bar to the table
		frame.getContentPane().add(scrollPane, "cell 0 0 5 1,growx");//add scroll bar to the frame
		
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
			}
		});
		
		frame.getContentPane().add(updateDiverButton, "cell 2 1,alignx right");
		
		DButton addDiverButton = new DButton("\u05D4\u05D5\u05E1\u05E4\u05EA \u05E6\u05D5\u05DC\u05DC\u05DF",DButton.Mode.PRIMARY);
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
				DiverRegistrationScreen register = new DiverRegistrationScreen();
			}
		});
		frame.getContentPane().add(addDiverButton, "cell 4 1,alignx trailing");
		
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
		
	
		frame.getContentPane().add(courseRegisterButton, "cell 3 1,alignx right");
		
		
		
		String[] Headings = {"Dive Number","Location","Date","Max Depth","Start Time","End Time","Start Air","End Air"};
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
	    tableDesign.designTable(divesTable);
		
		
		JTableHeader header2 = divesTable.getTableHeader();
	     header2.setBackground(UIConstants.SELECTED_BTN);
	     header2.setForeground(Color.white);
		
		divesPane = new JScrollPane(divesTable);
		divesPane.setBackground(Color.lightGray);
		frame.getContentPane().add(divesPane, "cell 0 2 5 1,growx");
		divesPane.setVisible(false);
		
		diversController = new DiverController();
		divesControler = new DivesController();
		updateDiversTable();
	}
}
