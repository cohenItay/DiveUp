package Screens;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.sqlite.core.DB;

import Classes.Diver;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

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
		frame.setBounds(100, 100, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String[] colHeadings = {"ID","First Name","Last Name","License ID","Email","Phone"};
		int numRows = 0 ;
		model = new DefaultTableModel(numRows, colHeadings.length) ;
		model.setColumnIdentifiers(colHeadings);
		frame.getContentPane().setLayout(new MigLayout("", "[407.00][][74.00][244.00][570px]", "[210.00][134.00][][][123.00][][][418px]"));
		JTable diversTable = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(diversTable);
		frame.getContentPane().add(scrollPane, "cell 0 0 5 1,growx,aligny center");
		
		JButton addDiverButton = new JButton("\u05D4\u05D5\u05E1\u05E4\u05EA \u05E6\u05D5\u05DC\u05DC\u05DF");
		frame.getContentPane().add(addDiverButton, "cell 4 1");
		
		JButton divingBookButton = new JButton("\u05D4\u05E6\u05D2 \u05D9\u05D5\u05DE\u05DF \u05E6\u05DC\u05D9\u05DC\u05D4");
		frame.getContentPane().add(divingBookButton, "cell 3 1");
		
		updateDiversTable();
	}
}
