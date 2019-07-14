package Views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Controllers.DiverController;
import Controllers.ItemController;
import Controllers.SaleController;
import Models.Diver;
import Models.Item;
import Models.diverSqlQueries;
import Models.itemSqlQueries;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DTable;
import res.DTextField;
import res.DTextPane;
import res.UIConstants;

public class InventoryScreen {

	private JFrame frame;
	private DefaultTableModel model;
	private JTable itemsTable;
	private List<Item> itemsList;
	private DTable tableDesign;
	public int currentItem = -1;
	private ItemController iController;
	private boolean firstTime = true;
	private DTextPane jtp;
	private Document doc;
	private JTextField idTextField;
	private JTextField nameTextField;
	private JTextField descTextField;
	private JTextField priceTextField;
	private JTextField loandTextField;
	private JTextField amountTextField;
	private JLabel idLabel;
	private JLabel nameLabel;
	private JLabel descLabel;
	private JLabel priceLabel;
	private JLabel loanLabel;
	private JLabel amountLabel;
	private DButton editButton;
	private JButton addButton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InventoryScreen window = new InventoryScreen();
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
		
		itemsList = iController.getItems();
		for (int i = 0; i < itemsList.size(); i++) {
			if(itemsList.get(i).getAmount()>0) {
			model.addRow(new Object[] {itemsList.get(i).getAmount() , itemsList.get(i).getLoanPrice() ,
					itemsList.get(i).getPrice(),itemsList.get(i).getDesc(),itemsList.get(i).getName()
					,itemsList.get(i).getId() });
			
			if(i==currentItem-1)
			{
				
				idTextField.setText(String.valueOf(itemsList.get(i).getId()));
				nameTextField.setText(itemsList.get(i).getName());
				descTextField.setText(itemsList.get(i).getDesc());
				priceTextField.setText(String.valueOf(itemsList.get(i).getPrice()));
				loandTextField.setText(String.valueOf(itemsList.get(i).getLoanPrice()));
				amountTextField.setText(String.valueOf(itemsList.get(i).getAmount()));
				editButton.setText("עדכן");
				editButton.setVisible(true);
				idTextField.setVisible(true);
				nameTextField.setVisible(true);
				descTextField.setVisible(true);
				priceTextField.setVisible(true);
				loandTextField.setVisible(true);
				amountTextField.setVisible(true);
				idLabel.setVisible(true);
				nameLabel.setVisible(true);
				descLabel.setVisible(true);
				priceLabel.setVisible(true);
				loanLabel.setVisible(true);
				amountLabel.setVisible(true);
				
			}
			}
			
		}
		if (row != -1)
		{
			itemsTable.setRowSelectionInterval(row, row);
			
		}
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
	 * Create the application.
	 */
	public InventoryScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(UIConstants.miniScreenx, UIConstants.miniScreeny, UIConstants.miniScreenWidth,UIConstants.miniScreenHeight);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//Title and icon add
		frame.setTitle("Sale screen");
		Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/images/snorkel.PNG"));
			frame.setIconImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.getContentPane().setLayout(new MigLayout("", "[400][400][400][400,right][80px:n][250px:n]", "[150px:n][::5px][5px:n][40px:n][5px:n][][10px:n][17.00][10px:n][30px:n][10px:n][][10px:n][][10px:n][30px:n][20px:n][40px:n][100px:n][40px:n][30px:n][10px:n][30px:n][]"));
		frame.getContentPane().setBackground(Color.WHITE);
		/* Creating the table model and the table for the divers information */
		String[] colHeadings = { "כמות", "עלות השכרה", "עלות קניה", "תיאור", "שם", "מזהה" };
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
					
				}
			}

		});

		JScrollPane scrollPane = new JScrollPane(itemsTable);// add scroll bar to the table
		frame.getContentPane().add(scrollPane, "cell 0 0 6 2,growx");
		




		editButton = new DButton("עדכן פריט",DButton.Mode.PRIMARY);
		editButton.setVisible(false);
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				if(editButton.getText().equals("הוספה"))
				{
					iController.addNewItem(nameTextField.getText(),descTextField.getText(),Double.valueOf(priceTextField.getText()),Double.valueOf(loandTextField.getText()),Integer.valueOf(amountTextField.getText()));
					updateItemList(-1);
				}
				else
				{
					iController.updateItem(Integer.valueOf(idTextField.getText()), nameTextField.getText(),descTextField.getText(),Double.valueOf(priceTextField.getText()),Double.valueOf(loandTextField.getText()),Integer.valueOf(amountTextField.getText()));
					updateItemList(-1);
				}
			}
		});
		
		addButton = new DButton("הוספת פריט",DButton.Mode.PRIMARY);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTextField.setText(String.valueOf(iController.getNewItemID()));
				nameTextField.setText("");
				descTextField.setText("");
				priceTextField.setText("");
				loandTextField.setText("");
				amountTextField.setText("");
				editButton.setText("הוספה");
				
				editButton.setVisible(true);
				idTextField.setVisible(true);
				nameTextField.setVisible(true);
				descTextField.setVisible(true);
				priceTextField.setVisible(true);
				loandTextField.setVisible(true);
				amountTextField.setVisible(true);
				idLabel.setVisible(true);
				nameLabel.setVisible(true);
				descLabel.setVisible(true);
				priceLabel.setVisible(true);
				loanLabel.setVisible(true);
				amountLabel.setVisible(true);
			}
		});
		frame.getContentPane().add(addButton, "cell 5 3,grow");
		
		idTextField = new DTextField(20);
		frame.getContentPane().add(idTextField, "cell 4 5");
		idTextField.setColumns(10);
		idTextField.setVisible(false);
		idTextField.setEditable(false);
		idTextField.setEnabled(false);
		
		idLabel = new JLabel("מזהה");
		idLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setForeground(UIConstants.BORDER_DARK);
		idLabel.setVisible(false);
		frame.getContentPane().add(idLabel, "cell 5 5,alignx right");
		
		nameTextField = new DTextField(20);
		frame.getContentPane().add(nameTextField, "cell 4 7,growx");
		nameTextField.setColumns(10);
		nameTextField.setVisible(false);
		
		
		nameLabel = new JLabel("שם");
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setForeground(UIConstants.BORDER_DARK);
		nameLabel.setVisible(false);
		frame.getContentPane().add(nameLabel, "cell 5 7,alignx right,aligny center");
		
		descTextField = new DTextField(20);
		frame.getContentPane().add(descTextField, "cell 4 9");
		descTextField.setColumns(10);
		descTextField.setVisible(false);
		
		descLabel = new JLabel("תיאור");
		descLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		descLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		descLabel.setForeground(UIConstants.BORDER_DARK);
		descLabel.setVisible(false);
		frame.getContentPane().add(descLabel, "cell 5 9,alignx right");
		
		priceTextField = new DTextField(20);
		frame.getContentPane().add(priceTextField, "cell 4 11,growx");
		priceTextField.setColumns(10);
		priceTextField.setVisible(false);
		
		priceLabel = new JLabel("מחיר");
		priceLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		priceLabel.setForeground(UIConstants.BORDER_DARK);
		priceLabel.setVisible(false);
		frame.getContentPane().add(priceLabel, "cell 5 11,alignx right");
		
		loandTextField = new DTextField(20);
		frame.getContentPane().add(loandTextField, "cell 4 13,growx");
		loandTextField.setColumns(10);
		loandTextField.setVisible(false);
		
		loanLabel = new JLabel("מחיר השכרה");
		loanLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		loanLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		loanLabel.setForeground(UIConstants.BORDER_DARK);
		loanLabel.setVisible(false);
		frame.getContentPane().add(loanLabel, "cell 5 13,alignx right");
		
		amountTextField = new DTextField(20);
		frame.getContentPane().add(amountTextField, "cell 4 15,growx");
		amountTextField.setColumns(10);
		amountTextField.setVisible(false);
		
		amountLabel = new JLabel("כמות");
		amountLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		amountLabel.setForeground(UIConstants.BORDER_DARK);
		amountLabel.setVisible(false);
		frame.getContentPane().add(amountLabel, "cell 5 15,alignx right,aligny top");
		frame.getContentPane().add(editButton, "cell 4 17 2 1,growx,aligny center");

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

		iController = new ItemController();
		updateItemList(currentItem);
		itemsTable.clearSelection();
		frame.setVisible(true);
		
		
	}
}
