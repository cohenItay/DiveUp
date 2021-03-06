package Views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext.SmallAttributeSet;
import javax.swing.text.StyledDocument;

import Controllers.DiverController;
import Controllers.ItemController;
import Controllers.SaleController;
import Models.Diver;
import Models.Item;
import Models.diverSqlQueries;
import Models.itemSqlQueries;
import Utilities.Reporter;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DTable;
import res.DTextPane;
import res.UIConstants;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

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
	public int currentItem = -1;
	private itemSqlQueries dbConnection;
	private diverSqlQueries diverConnection;
	private ItemController iController;
	private DiverController dController;
	private SaleController sController;
	private JTextField sumTextField;
	private JComboBox diverComboBox;
	private JComboBox amountComboBox;
	private boolean firstTime = true;
	private DTextPane jtp;
	private Document doc;
	private boolean bought=false;
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

	public int getCurrentitemID() {
		return currentItem;
	}

	/*
	 * This Function will query the database to get all divers information and
	 * update in the table view
	 */
	public void updateItemList(int row) {
		model.setRowCount(0);// Clearing the table data
		dbConnection = new itemSqlQueries();
		itemsList = dbConnection.getItems();

		for (int i = 0; i < itemsList.size(); i++) {
			if(itemsList.get(i).getAmount()>0) {
			model.addRow(new Object[] {itemsList.get(i).getAmount() , itemsList.get(i).getLoanPrice(),
					itemsList.get(i).getPrice(),itemsList.get(i).getDesc(), itemsList.get(i).getName(),
					itemsList.get(i).getId() });
			}
		}
		if (row != -1)
			itemsTable.setRowSelectionInterval(row, row);
	}

	public void updateDivers() {
		diversList = diverConnection.getDivers();
		for (int i = 0; i < diversList.size(); i++) {
			diverComboBox.addItem(diversList.get(i).getFirstName() + "(" + diversList.get(i).getId() + ")");
		}
	}

	public void updateAmountCombo() {
		amountComboBox.setModel(new DefaultComboBoxModel());
		for (int i = 1; i <= iController.getItemAmount(currentItem); i++) {
			amountComboBox.addItem(i);
			amountComboBox.setSelectedItem("1");
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
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(UIConstants.miniScreenx, UIConstants.miniScreeny, UIConstants.miniScreenWidth,UIConstants.miniScreenHeight);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//Title and icon add
		frame.setTitle("עמוד מכירות");
		Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/images/snorkel.PNG"));
			frame.setIconImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.getContentPane().setLayout(new MigLayout("", "[400][400][400][400,right][30px:n][::70px]", "[150px:n][::5px][5px:n][25.00][][17.00][5px:n][30px:n][10px:n][30px:n][100px:n][30px:n][10px:n][30px:n][]"));
		frame.getContentPane().setBackground(Color.WHITE);
		/* Creating the table model and the table for the divers information */
		String[] colHeadings = { "כמות", "מחיר השכרה", "מחיר", "תיאור", "מוצר", "מזהה" };
		int numRows = 0;
		model = new DefaultTableModel(numRows, colHeadings.length) {
			public boolean isCellEditable(int row, int column) {
				return false;// This causes all cells to be not editable
			}
		};
		model.setColumnIdentifiers(colHeadings);
		itemsTable = new JTable(model);
		itemsTable.setFont(new Font("Arial", Font.PLAIN, 18));
		tableDesign = new DTable();
		itemsTable = tableDesign.designTable(itemsTable,DTable.Mode.PRIMARY);

		/* Add listener in order to update the table data when pressed */
		itemsTable.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = itemsTable.rowAtPoint(evt.getPoint());
				int col = itemsTable.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {
					currentItem = (Integer) model.getValueAt(row, 5);
					updateItemList(row);
					updateAmountCombo();
					amountComboBox.setSelectedItem("1");
				}
			}

		});

		JScrollPane scrollPane = new JScrollPane(itemsTable);// add scroll bar to the table
		frame.getContentPane().add(scrollPane, "cell 0 0 6 2,growx");
		
				diverComboBox = new JComboBox();	
				diverComboBox.setFont(new Font("Arial", Font.BOLD, 18));
				frame.getContentPane().add(diverComboBox, "cell 3 3 2 1,alignx right");
				diverComboBox.setBackground(Color.white);
				diverComboBox.setForeground(UIConstants.SELECTED_BTN);
				((JLabel)diverComboBox.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);

		JLabel diverLabel = new JLabel("לקוח");
		diverLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		frame.getContentPane().add(diverLabel, "cell 5 3,alignx right");

		JCheckBox isLoaned = new JCheckBox("\u05D4\u05E9\u05DB\u05E8\u05D4");
		isLoaned.setFont(new Font("Arial", Font.PLAIN, 18));
		isLoaned.setHorizontalAlignment(SwingConstants.RIGHT);
		isLoaned.setHorizontalTextPosition(SwingConstants.LEFT);
		isLoaned.setBackground(Color.white);

		frame.getContentPane().add(isLoaned, "cell 5 5,alignx right");


		JButton addToCartButton = new JButton(
				"הוסף לסל");
		addToCartButton.setFont(new Font("Arial", Font.PLAIN, 18));
		addToCartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// adding item from the items table to the cartTable
				if(currentItem!=-1)
				{
					
					if(firstTime)
					{
						message("על מנת למחוק פריט יש לסמן אותו בעגלה וללחוץ על המקש delete","הודעה");
						firstTime = false;
					}
					Item i = dbConnection.getItemByID(currentItem);
					double price = 0;
					String productName = "";
					if (isLoaned.isSelected()) {
						price = i.getLoanPrice() * Integer.valueOf(amountComboBox.getSelectedItem().toString());
						productName = i.getName() + "(השכרה)";
					} else {
						price = i.getPrice() * Integer.valueOf(amountComboBox.getSelectedItem().toString());
						productName = i.getName();
					}
				
				iController.updateAmount(i.getId(), Integer.valueOf(amountComboBox.getSelectedItem().toString()));
				modelCart.addRow(new Object[] { price, amountComboBox.getSelectedItem().toString(), productName });
				sumTextField.setText(String.valueOf(sController.priceCalculate(cartTable)));
				updateItemList(-1);
				}
				else
				{
					errorMessage("אנא בחר מוצר","לא נבחר מוצר");
				}
			}
		});
		frame.getContentPane().add(addToCartButton, "cell 4 9 2 1,alignx right,gapx 0");

		amountComboBox = new JComboBox();
		amountComboBox.setFont(new Font("Arial", Font.BOLD, 18));
		frame.getContentPane().add(amountComboBox, "cell 4 7,alignx right");
		amountComboBox.addItem("0");
		amountComboBox.setSelectedItem("0");
		JLabel amountLabel = new JLabel("כמות");
		amountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		amountComboBox.setBackground(Color.white);
		amountComboBox.setForeground(UIConstants.SELECTED_BTN);
		((JLabel)amountComboBox.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);


		frame.getContentPane().add(amountLabel, "cell 5 7,alignx right");

		String[] colHeadingsCart = { "מחיר", "כמות", "מוצר" };
		int numRowsCart = 0;
		modelCart = new DefaultTableModel(numRowsCart, colHeadingsCart.length) {
			public boolean isCellEditable(int row, int column) {
				return false;// This causes all cells to be not editable
			}
		};
		modelCart.setColumnIdentifiers(colHeadingsCart);

		cartTable = new JTable(modelCart);
		cartTable.setFont(new Font("Arial", Font.PLAIN, 18));
		cartTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					
					Pattern pattern = Pattern.compile("\\((.*?)\\)");
					String itemName = (String) cartTable.getValueAt(cartTable.getSelectedRow(), 2);
	        		Matcher matcher = pattern.matcher(itemName);
	        		
	        		if (matcher.find())
	        		{
	        		    itemName = itemName.replace("("+matcher.group(1)+")","");
	        		}
					
					int id = iController.getID(itemName);
					
					iController.updateAmount(id, Integer.valueOf(amountComboBox.getSelectedItem().toString()) * -1);
					modelCart.removeRow(cartTable.getSelectedRow());
					sumTextField.setText(String.valueOf(sController.priceCalculate(cartTable)));
					updateItemList(-1);
				}
			}
		});
		tableDesign = new DTable();
		cartTable = tableDesign.designTable(cartTable,DTable.Mode.SECONDERY);
		JScrollPane cartScroll = new JScrollPane(cartTable);
		frame.getContentPane().add(cartScroll, "cell 0 10 6 1,grow");

		sumTextField = new JTextField();
		sumTextField.setFont(new Font("Arial", Font.BOLD, 18));
		sumTextField.setEditable(false);
		frame.getContentPane().add(sumTextField, "cell 3 11 2 1,alignx right");
		sumTextField.setColumns(10);
		sumTextField.setBackground(UIConstants.SELECTED_BTN);
		sumTextField.setForeground(Color.white);
		sumTextField.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel sumLabel = new JLabel("\u05E1\u05DB\u05D5\u05DD \u05DB\u05D5\u05DC\u05DC");
		sumLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		frame.getContentPane().add(sumLabel, "cell 5 11,alignx right");

		JButton saleButton = new JButton("בצע מכירה");
		saleButton.setFont(new Font("Arial", Font.PLAIN, 18));
		saleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				String customerID = diverComboBox.getSelectedItem().toString();
				String items = sController.getAllItems(cartTable);
				if(customerID.equals("") || items.equals("") || (itemsTable.getSelectedRow() == -1 && items.equals("")))
                    errorMessage("עגלת הקניות ריקה", "שגיאה");
				else
				{
					sController.addSale(customerID,items ,dateFormat.format(date), Double.valueOf(sumTextField.getText()));
				message("הקניה בוצעה בהצלחה","הודעה");
				bought = true;
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));//close window
				}
			}
		});
		frame.getContentPane().add(saleButton, "cell 4 13 2 1,alignx right");

		/* Add listener in order to update the table data when pressed */
		itemsTable.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = itemsTable.rowAtPoint(evt.getPoint());
				int col = itemsTable.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {
					currentItem = (Integer) model.getValueAt(row, 5);
					updateItemList(row);

				}
			}

		});

		diverConnection = new diverSqlQueries();
		iController = new ItemController();
		sController = new SaleController();
		updateItemList(currentItem);
		updateDivers();
		itemsTable.clearSelection();
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
	         @Override
	         public void windowClosing(WindowEvent e) {
	            if(cartTable.getRowCount() > 0 && !bought)
	            {
	            	errorMessage("לא ניתן לסגור את החלון לפני הסרת כל המוצרים מהעגלה", "שגיאה");
	            }
	            else
	            {
	            	frame.dispose();
	            }
	         }
	      });
	}

}
