package Screens;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.sqlite.core.DB;

import Classes.Diver;

public class CustomerScreen {

	private JFrame frame;
	private JTable diversTable;
	private DefaultTableModel model; 
	private List<Diver> diversList;
	/**
	 * Launch the application.
	 */
	
	public void updateDiversTable() {
		sqlConnection dbConnection = sqlConnection.getInstance();
		diversList = dbConnection.getDivers();
		for(int i=0;i<diversList.size();i++)
		{
		model.addRow(new Object[] {diversList.get(i).getId(), diversList.get(i).getFirstName(),
				diversList.get(i).getLastName(),diversList.get(i).getLicenseID(),
				diversList.get(i).getEmail(),diversList.get(i).getPhone()});
		}
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerScreen window = new CustomerScreen();
					window.frame.setTitle("Divers Screen");
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
		frame.setBounds(100, 100, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		String[] colHeadings = {"ID","First Name","Last Name","License ID","Email","Phone"};
		int numRows = 0 ;
		model = new DefaultTableModel(numRows, colHeadings.length) ;
		model.setColumnIdentifiers(colHeadings);
		diversTable = new JTable(model);		
		JScrollPane scroll = new JScrollPane(diversTable);
		updateDiversTable();
		frame.getContentPane().add(scroll);
	}
}
