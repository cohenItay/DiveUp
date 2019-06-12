package Screens;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Classes.Course;
import Classes.Dive;
import Controllers.CoursesController;
import Controllers.DiverController;
import Controllers.DivesController;
import Models.courseSqlQueries;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DTable;
import res.DTextPane;
import res.UIConstants;

public class DiveBookScreen {

	private JFrame frame;
	private DTextPane jtp;
	private Document doc;
	private DefaultTableModel model;
	private JTable divesTable;
	private JScrollPane divesPane;
	private List<Dive> divesList;
	private DTable tableDesign;
	private DivesController divesController;
	private DiverController diverController;
	private int currentDive=-1;
	private String diverID="";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DiveBookScreen window = new DiveBookScreen("");
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
	public DiveBookScreen(String diverID) {
		this.diverID= diverID;
		diverController = new DiverController();
		initialize();
	}
	
	public void updateDivesList(int row)
	{
		model.setRowCount(0);//Clearing the table data
		List<Dive> divesList = divesController.getDivesBook(diverID);
	    DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
	    for(int i=0;i<divesList.size();i++)
	    {
	    		    		
	        model.addRow(new Object[] {divesList.get(i).getAirEnd(), divesList.get(i).getAirStart(), divesList.get(i).getEndTime(), divesList.get(i).getStartTime(), divesList.get(i).getMaxDepth(), outputFormatter.format(divesList.get(i).getDate()), divesList.get(i).getLocation(), divesList.get(i).getDiver(), divesList.get(i).getDiveNum() 
	    		});
	    		
	    }
	    if(row != -1)
	    	divesTable.setRowSelectionInterval(row, row);
	}
	
	
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
		frame.setTitle("מסך צלילות");
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
		String[] colHeadings = {"סיום אוויר","תחילת אוויר","שעת סיום","שעת התחלה","עומק מקסימלי","תאריך","מיקום","צוללן","מזהה"};
		int numRows = 0 ;
		model = new DefaultTableModel(numRows, colHeadings.length)
				{
			 		public boolean isCellEditable(int row, int column)
			 			{
			 				return false;//This causes all cells to be not editable
			 			}
				};
		model.setColumnIdentifiers(colHeadings);
		divesTable = new JTable(model);
		tableDesign= new DTable();
		divesTable = tableDesign.designTable(divesTable,DTable.Mode.PRIMARY);

		
		/*Add listener in order to update the table data when pressed*/
		divesTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = divesTable.rowAtPoint(evt.getPoint());
		        int col = divesTable.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		        	currentDive = (Integer)model.getValueAt(row, 8);
		            updateDivesList(row);
		            
		        }
		    }
		    
		    
		});
		
		
		
		JScrollPane scrollPane = new JScrollPane(divesTable);//add scroll bar to the table
		frame.getContentPane().add(scrollPane, "cell 0 0 5 1,growx");//add scroll bar to the frame
		
		/*Create buttons for activities*/
		
		DButton addDiveButton = new DButton("v",DButton.Mode.PRIMARY);
		addDiveButton.setText("הוספת צלילה");
				addDiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				AddDiveScreen dadd = new AddDiveScreen(null,diverController.getDiverByID(diverID));
				updateDivesList(-1);
			}
		});
		DButton updateDiveButton = new DButton("\u05E2\u05D3\u05DB\u05D5\u05DF \u05E4\u05E8\u05D8\u05D9 \u05DC\u05E7\u05D5\u05D7",DButton.Mode.PRIMARY);
		updateDiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentDive != -1)
				{
				boolean found = false;
				List<Dive> dList = divesController.getAllDives();
				for( int i =0;i<dList.size();i++)
				{
					if(dList.get(i).getDiveNum()== currentDive)
					{
						AddDiveScreen dadd=new AddDiveScreen(dList.get(i),diverController.getDiverByID(diverID));
						found=true;
					}
				}
				if(!found)
				{
					AddDiveScreen dadd=new AddDiveScreen(null,diverController.getDiverByID(diverID));		
				}
				updateDivesList(-1);
				}
				else
				{
					errorMessage("לא נבחרה צלילה", "שגיאה");
				}
			}
		});
		updateDiveButton.setText("עדכון צלילה");
		
		
		frame.getContentPane().add(updateDiveButton, "cell 3 2,alignx right,growy");
		frame.getContentPane().add(addDiveButton, "cell 4 2,alignx trailing,growy");
		frame.setVisible(true);
		divesController = new DivesController();
		updateDivesList(currentDive);
		
	}

}
