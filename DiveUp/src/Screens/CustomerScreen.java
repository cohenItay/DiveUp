package Screens;
import java.awt.EventQueue;
import java.awt.Image;
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
	private JTable diversTable;
	private List<Diver> diversList;
	private JButton button;
	public boolean isFocused = true;
	/**
	 * Launch the application.
	 */
	
	/*This Function will query the database to get all divers information and update in the table view*/
	public void updateDiversTable() {
		model.setRowCount(0);//Clearing the table data
		sqlConnection dbConnection = sqlConnection.getInstance();//connection to the DB
		diversList = dbConnection.getDivers();//Getting divers list from the DB
		for(int i=0;i<diversList.size();i++)//For every diver add its information to the table
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
					/*creating new CustomerScreen*/
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
		frame.getContentPane().setLayout(new MigLayout("", "[400,grow,fill][400,grow,fill][400,grow,fill][400,grow,fill][400,grow,fill]", "[270,grow][106.00,grow][270,grow][260,grow][250,grow]"));

		/*Creating the table model and the table for the divers information*/
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
		diversTable = new JTable(model);
		
		/*Add listener in order to update the table data when pressed*/
		diversTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = diversTable.rowAtPoint(evt.getPoint());
		        int col = diversTable.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		            updateDiversTable();

		        }
		    }
		});
		
		
		
		JScrollPane scrollPane = new JScrollPane(diversTable);//add scroll bar to the table
		frame.getContentPane().add(scrollPane, "cell 0 0 5 1,growx");//add scroll bar to the frame
		
		/*Create buttons for activities*/
		JButton updateDiverButton = new JButton("\u05E2\u05D3\u05DB\u05D5\u05DF \u05E4\u05E8\u05D8\u05D9 \u05DC\u05E7\u05D5\u05D7");
		frame.getContentPane().add(updateDiverButton, "cell 2 1,alignx right");
		
		JButton addDiverButton = new JButton("\u05D4\u05D5\u05E1\u05E4\u05EA \u05E6\u05D5\u05DC\u05DC\u05DF");
		addDiverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				diverRegistrationScreen register = new diverRegistrationScreen();
			}
		});
		frame.getContentPane().add(addDiverButton, "cell 4 1,alignx trailing");
		
		JButton courseRegisterButton = new JButton("\u05D4\u05E8\u05E9\u05DE\u05D4 \u05DC\u05E7\u05D5\u05E8\u05E1");
		
	
		frame.getContentPane().add(courseRegisterButton, "cell 3 1,alignx right");
		
		updateDiversTable();
	}
}
