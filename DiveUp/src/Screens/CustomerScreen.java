package Screens;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Classes.Diver;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomerScreen {

	private JFrame frame;
	private DefaultTableModel model; 
	private List<Diver> diversList;
	private JButton button;
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
					Image image = ImageIO.read(this.getClass().getResource("/images/icon.png"));
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
		frame.getContentPane().setLayout(new MigLayout("", "[407.00][370,right][380,fill][390,fill][400,fill]", "[210.00][134.00][][][123.00][][][418px]"));

		
		String[] colHeadings = {"ID","First Name","Last Name","License ID","Email","Phone"};
		int numRows = 0 ;
		model = new DefaultTableModel(numRows, colHeadings.length)
				{
			 		public boolean isCellEditable(int row, int column)
			 			{
			 				return false;//This causes all cells to be not editable
			 			}
				};
				
		model.setColumnIdentifiers(colHeadings);
		JTable diversTable = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(diversTable);
		frame.getContentPane().add(scrollPane, "cell 0 0 5 1,growx,aligny center");
		
		JButton updateDiverButton = new JButton("\u05E2\u05D3\u05DB\u05D5\u05DF \u05E4\u05E8\u05D8\u05D9 \u05DC\u05E7\u05D5\u05D7");
		frame.getContentPane().add(updateDiverButton, "cell 2 1,alignx right");
		
		JButton addDiverButton = new JButton("\u05D4\u05D5\u05E1\u05E4\u05EA \u05E6\u05D5\u05DC\u05DC\u05DF");
		addDiverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFrame diverRegisterScreen = new diverRegisterScreen().getFrame();
				diverRegisterScreen.setVisible(true);
			}
		});
		frame.getContentPane().add(addDiverButton, "cell 4 1,alignx trailing");
		
		JButton courseRegisterBurron = new JButton("\u05D4\u05E8\u05E9\u05DE\u05D4 \u05DC\u05E7\u05D5\u05E8\u05E1");
		courseRegisterBurron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFrame courseRegisterScreen = new courseRegistrationScreen().getFrame();
				courseRegisterScreen.setVisible(true);
			}
		});
		frame.getContentPane().add(courseRegisterBurron, "cell 3 1,alignx right");
		
		updateDiversTable();
	}
}
