package Screens;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Classes.Item;
import Controllers.ItemController;
import Models.itemSqlQueries;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DTable;
import res.UIConstants;

public class SaleScreen {

	private JFrame frame;
	private DefaultTableModel model;
	private JTable itemsTable;
	private List<Item> itemsList;
	private DTable tableDesign;
	public int currentItem=0;
	private itemSqlQueries dbConnection;
	private ItemController iController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SaleScreen window = new SaleScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	
	public int getCurrentcourseID()
	{
		return currentItem;
	}
	/*This Function will query the database to get all divers information and update in the table view*/
	public void updateCoursesList(int row)
	{
		model.setRowCount(0);//Clearing the table data
		dbConnection = new itemSqlQueries();
	    List<Item> items = dbConnection.getItems();
	    
	    for(int i=0;i<items.size();i++)
	    {
	    		model.addRow(new Object[] {items.get(i).getId(), items.get(i).getName(),
	    				items.get(i).getDesc(),items.get(i).getPrice(),
	    				items.get(i).getLoanPrice(),items.get(i).getAmount()});
	    		
	    }
	    itemsTable.setRowSelectionInterval(row, row);
	}
	/**
	 * Create the application.
	 */
	public SaleScreen() {
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
		String[] colHeadings = {"ID","Name","Sale Price","Loan Price","Amount"};
		int numRows = 0 ;
		model = new DefaultTableModel(numRows, colHeadings.length)
				{
			 		public boolean isCellEditable(int row, int column)
			 			{
			 				return false;//This causes all cells to be not editable
			 			}
				};
		model.setColumnIdentifiers(colHeadings);
		itemsTable = new JTable(model);
		tableDesign= new DTable();
		itemsTable = tableDesign.designTable(itemsTable);

		
		/*Add listener in order to update the table data when pressed*/
		itemsTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = itemsTable.rowAtPoint(evt.getPoint());
		        int col = itemsTable.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		        	currentItem = (Integer)model.getValueAt(row, 0);
		            updateCoursesList(row);
		            
		        }
		    }
		    
		    
		});
		
		
		
		JScrollPane scrollPane = new JScrollPane(itemsTable);//add scroll bar to the table
		frame.getContentPane().add(scrollPane, "cell 0 0 5 1,growx");//add scroll bar to the frame
		
		/*Create buttons for activities*/
		DButton updateDiverButton = new DButton("\u05E2\u05D3\u05DB\u05D5\u05DF \u05E4\u05E8\u05D8\u05D9 \u05DC\u05E7\u05D5\u05D7",DButton.Mode.PRIMARY);
		updateDiverButton.setText("\u05E2\u05D3\u05DB\u05D5\u05DF \u05E4\u05E8\u05D8\u05D9\u05DD");
		
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
		addDiverButton.setText("\u05D4\u05D5\u05E1\u05E4\u05EA \u05E4\u05E8\u05D9\u05D8");
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
				
			}
		});
		frame.getContentPane().add(addDiverButton, "cell 4 1,alignx trailing");
		
		DButton courseRegisterButton = new DButton("\u05D4\u05E8\u05E9\u05DE\u05D4 \u05DC\u05E7\u05D5\u05E8\u05E1",DButton.Mode.PRIMARY);
		courseRegisterButton.setText("\u05DE\u05DB\u05D9\u05E8\u05D4 \u05DC\u05DC\u05E7\u05D5\u05D7");
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
				
			}
		});
		
	
		frame.getContentPane().add(courseRegisterButton, "cell 3 1,alignx right");
				
		iController = new ItemController();
		updateCoursesList(currentItem);
	}

	}


