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

import Classes.Diver;
import Classes.Item;
import Controllers.DiverController;
import Controllers.ItemController;
import Models.diverSqlQueries;
import Models.itemSqlQueries;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DTable;
import res.UIConstants;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SaleScreen {

	private JFrame frame;
	private DefaultTableModel model;
	private DefaultTableModel modelCart;
	private JTable itemsTable;
	private JTable cartTable;
	private List<Item> itemsList;
	private List<Item> cart;
	private List<Diver> diversList;
	private DTable tableDesign;
	public int currentItem=0;
	private itemSqlQueries dbConnection;
	private diverSqlQueries diverConnection;
	private ItemController iController;
	private DiverController dController;
	private JTextField textField;
	private JComboBox diverComboBox;
	private JComboBox amountComboBox;
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
	    itemsList = dbConnection.getItems();
	    
	    for(int i=0;i<itemsList.size();i++)
	    {
	    		model.addRow(new Object[] {itemsList.get(i).getId(), itemsList.get(i).getName(),
	    				itemsList.get(i).getDesc(),itemsList.get(i).getPrice(),
	    				itemsList.get(i).getLoanPrice(),itemsList.get(i).getAmount()});
	    		
	    }
	    itemsTable.setRowSelectionInterval(row, row);
	}
	
	
	public void updateDivers()
	{
		diversList = diverConnection.getDivers();
		for(int i = 0 ;i<diversList.size();i++)
		{
			diverComboBox.addItem(diversList.get(i).getFirstName() +"("+diversList.get(i).getId()+")");
		}
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
		frame.getContentPane().setLayout(new MigLayout("", "[400][400][400][400][40px][::100px]", "[136.00][::5px][5px:n][25.00][][17.00][30px:n][30px:n][80px:n][30px:n][10px:n][30px:n][31.00]"));
		frame.getContentPane().setBackground(Color.WHITE);
		/*Creating the table model and the table for the divers information*/
		String[] colHeadings = {"ID","Name","Description","Sale Price","Loan Price","Amount"};
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
		frame.getContentPane().add(scrollPane, "cell 0 0 6 2,growx");
		
		JLabel diverLabel = new JLabel("\u05DC\u05E7\u05D5\u05D7");
		frame.getContentPane().add(diverLabel, "cell 5 3,alignx center");
		
		JCheckBox isLoaned = new JCheckBox("\u05D4\u05E9\u05DB\u05E8\u05D4");
		isLoaned.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(isLoaned, "cell 5 5,alignx right");
		
		diverComboBox = new JComboBox();
		frame.getContentPane().add(diverComboBox, "cell 3 3 2 1,alignx right,growx");
		
		JButton addToCartButton = new JButton("\u05D4\u05D5\u05E1\u05E3 \u05DC\u05E1\u05DC \u05D4\u05E7\u05E0\u05D9\u05D5\u05EA");
		addToCartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//adding item from the items table to the cartTable
				Item i = dbConnection.getItemByID(currentItem);
				double price=0;
				String productName = "";
				if(isLoaned.isSelected())
				{
					price = i.getLoanPrice();
					productName = i.getName() +"(השכרה)";
				}
				else
				{
					price = i.getPrice();
					productName = i.getName();
				}
				modelCart.addRow(new Object[] {price,amountComboBox.getSelectedItem().toString(),productName});
				
				
			}
		});
		frame.getContentPane().add(addToCartButton, "cell 4 7 2 1,growx");
		
		amountComboBox = new JComboBox();
		amountComboBox.addItem("1");
		amountComboBox.setSelectedItem("1");
		frame.getContentPane().add(amountComboBox, "cell 4 6,growx");
		
		JLabel amountLabel = new JLabel("\u05DB\u05DE\u05D5\u05EA");
		frame.getContentPane().add(amountLabel, "cell 5 6,alignx center");
		
		
		String[] colHeadingsCart = {"Price","Amount","Name"};
		int numRowsCart = 0 ;
		modelCart = new DefaultTableModel(numRowsCart, colHeadingsCart.length)
				{
			 		public boolean isCellEditable(int row, int column)
			 			{
			 				return false;//This causes all cells to be not editable
			 			}
				};
		modelCart.setColumnIdentifiers(colHeadingsCart);

		
		cartTable = new JTable(modelCart);
		tableDesign= new DTable();
		cartTable = tableDesign.designTable(cartTable);
		
		JScrollPane cartScroll = new JScrollPane(cartTable);
		frame.getContentPane().add(cartScroll, "cell 0 8 6 1,grow");
		
		textField = new JTextField();
		textField.setEditable(false);
		frame.getContentPane().add(textField, "cell 4 9,growx");
		textField.setColumns(10);
		
		JLabel sumLabel = new JLabel("\u05E1\u05DB\u05D5\u05DD \u05DB\u05D5\u05DC\u05DC");
		frame.getContentPane().add(sumLabel, "cell 5 9,alignx center");
		
		JButton saleButton = new JButton("\u05DE\u05DB\u05D9\u05E8\u05D4");
		frame.getContentPane().add(saleButton, "cell 4 11 2 1,alignx right");
				
		
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

		
		
		
		
		
		diverConnection = new diverSqlQueries();
		iController = new ItemController();
		updateCoursesList(currentItem);
		updateDivers();
	}

	}


