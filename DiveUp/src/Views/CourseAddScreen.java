package Views;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.jdesktop.swingx.JXDatePicker;

import Controllers.CoursesController;
import Controllers.DiverController;
import Controllers.EmployeeController;
import Models.Diver;
import Models.Employee;
import Models.diverSqlQueries;
import Utilities.SendEmailTLS;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DNotification;
import res.DTextField;
import res.DTextPane;
import res.UIConstants;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/******** Add Course Screen view ******/

public class CourseAddScreen {

	private JFrame frame;
	private DTextField maxTextField;
	private DTextField priceTextField;
	private diverSqlQueries dbConnection;
	private DTextPane jtp;
	private Document doc;
	private DTextField descTextField;
	private JXDatePicker startDatePicker;
	private JXDatePicker endDatePicker;
	private boolean table_loaded;
	private JComboBox typeComboBox;
	private JComboBox employeeComboBox;
	private CoursesController cController;
	private EmployeeController eController;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseAddScreen window = new CourseAddScreen();
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
	public CourseAddScreen() {
		initialize();
	}

	
	//Get all courses type from the database
	public void updateTypes()
	{
		List<String>typesList = cController.getTypes();
		((JLabel)typeComboBox.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
	    for(int i =0 ; i<typesList.size();i++)
	    {
	    	typeComboBox.addItem(typesList.get(i));
	    }
	}
	//get a list of the instructors from the databse
	public void updateEmployees()
	{
		List<Employee>employeesList = eController.getEmployees();
		((JLabel)typeComboBox.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
	    for(int i =0 ; i<employeesList.size();i++)
	    {
	    	employeeComboBox.addItem("("+employeesList.get(i).getId() +")"+employeesList.get(i).getFirstName());
	    }
	}
	
	//function to pop a message to the screen
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
	//function to pop an error to the screen
	public void errorMessage(String infoMessage, String titleBar)
    {
		jtp = new DTextPane();
	    doc = jtp.getDocument();
	    SimpleAttributeSet right = new SimpleAttributeSet();
	    StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
	    ((StyledDocument) doc).setParagraphAttributes(0, doc.getLength(), right, false);
	    try {
			doc.insertString(doc.getLength(), infoMessage, new SimpleAttributeSet());
		    JOptionPane.showMessageDialog(null, jtp,titleBar, JOptionPane.ERROR_MESSAGE);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();//creating the jframe
		/* set the size and the location of the frame */
		frame.setBounds(UIConstants.miniScreenx, UIConstants.miniScreeny, UIConstants.miniScreenWidth,UIConstants.miniScreenHeight);
		frame.getContentPane().setBackground(Color.WHITE);
		//Title and icon add
		frame.setTitle("הוספת קורס");
		Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/images/snorkel.PNG"));
			frame.setIconImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.getContentPane().setLayout(new MigLayout("", "[20%,fill][][20%,fill][20%][100px:n,fill][20%:n,grow,fill][20px:n][][10px:n][80px][200px:n][100px:n]", "[][70px:n][40px:n][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][::35px][::10px][40px:n][::10px][40px:n][30px:n][40px:n]"));
		
		
		
		/* adding fields to the form */
		
		JLabel titleLabel = new JLabel("הוספת קורס");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(UIConstants.SELECTED_BTN);
		frame.getContentPane().add(titleLabel, "cell 5 0 2 1,alignx right");
		
		DTextField nameTextField = new DTextField(20);
		nameTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(nameTextField, "cell 5 2,grow");
		
		JLabel nameLabel = new JLabel("שם קורס");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setForeground(new Color(112, 112, 112));
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		frame.getContentPane().add(nameLabel, "cell 7 2,alignx right");
		
		typeComboBox = new JComboBox();
		typeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 22));
		typeComboBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		typeComboBox.setBackground(Color.WHITE);
		typeComboBox.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(typeComboBox, "cell 5 3,growx");
		
		JLabel typeLabel = new JLabel("סוג");
		typeLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		typeLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(typeLabel, "cell 7 3,alignx right");
		
		employeeComboBox = new JComboBox();
		employeeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 22));
		employeeComboBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		employeeComboBox.setBackground(Color.WHITE);
		employeeComboBox.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(employeeComboBox, "cell 5 5,growx");
		
		
		
		JLabel instructorLabel = new JLabel("מדריך");
		instructorLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		instructorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		instructorLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(instructorLabel, "cell 7 5,alignx right");
		
		maxTextField = new DTextField(20);
		maxTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(maxTextField, "cell 5 7,grow");
		maxTextField.setColumns(10);
		maxTextField.setToolTipText("הכנס כמות משתתפים מקסימלית");
		
		JLabel maxAmountLabel = new JLabel("כמות מקסימלית");
		maxAmountLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		maxAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		maxAmountLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(maxAmountLabel, "cell 7 7,alignx right");
		
		priceTextField = new DTextField(20);
		priceTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(priceTextField, "cell 5 9,grow");
		priceTextField.setColumns(10);
		priceTextField.setToolTipText("מחיר קורס");
		
		JLabel priceLabel = new JLabel("מחיר");
		priceLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		priceLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(priceLabel, "cell 7 9,alignx right");
		JLabel startLabel = new JLabel("תאריך התחלה");
		startLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		startLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		startLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(startLabel, "cell 7 11,alignx right");
		
		
		
		JPanel datePanel = new JPanel();
		frame.getContentPane().add(datePanel, "cell 5 11,alignx right,growy");
		JPanel endDatePanel = new JPanel();
        frame.getContentPane().add(endDatePanel, "cell 5 13,alignx right,growy");
        
        frame.getContentPane().setBackground(Color.WHITE);
        datePanel.setBackground(Color.WHITE);
        endDatePanel.setBackground(Color.WHITE);
		
		
		//Creating calendar
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
        
		/* adding fields to the form */
		startDatePicker = new JXDatePicker();
		startDatePicker.getEditor().setFont(new Font("Tahoma", Font.PLAIN, 24));
		startDatePicker.getEditor().setHorizontalAlignment(SwingConstants.RIGHT);
		startDatePicker.getEditor().setBackground(Color.WHITE);
		startDatePicker.getEditor().setForeground(UIConstants.BORDER_DARK);
		startDatePicker.getEditor().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		startDatePicker.getEditor().addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				Date d = new Date();
				long diff = startDatePicker.getDate().getTime() - d.getTime();
                long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        		if(days < 0)
        		{
        			errorMessage("תאריך התחלה לא תקין", "בחירה שגויה");
        			c.setTime(new Date());
        			startDatePicker.setDate(c.getTime());
        			c.add(Calendar.DATE, 1);
        			endDatePicker.setDate(c.getTime());
        			c.add(Calendar.DATE, -1);
        		}
        		else
        		{
        			;
        		}
				
			}
		});
		
	
		
		startDatePicker.setDate(Calendar.getInstance().getTime());
		startDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        datePanel.add(startDatePicker);
        
        
        
        //if date was change, check that the date is valid
		c.add(Calendar.DATE, 1);
        endDatePicker = new JXDatePicker();
        endDatePicker.getEditor().addPropertyChangeListener(new PropertyChangeListener() {
        	public void propertyChange(PropertyChangeEvent evt) {
        	if(table_loaded)
        	{
        		long diff = endDatePicker.getDate().getTime() - startDatePicker.getDate().getTime();
                long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        		if(days < 0)
        		{
        			errorMessage("תאריך התחלה וסיום לא תקינים", "בחירה שגויה");
        			c.setTime(new Date());
        			startDatePicker.setDate(c.getTime());
        			c.add(Calendar.DATE, 1);
        			endDatePicker.setDate(c.getTime());
        			c.add(Calendar.DATE, -1);
        			
        		}
        	}
        	}
        });
        endDatePicker.getEditor().setFont(new Font("Tahoma", Font.PLAIN, 24));
        endDatePicker.getEditor().setHorizontalAlignment(SwingConstants.RIGHT);
        endDatePicker.getEditor().setHorizontalAlignment(SwingConstants.RIGHT);
		endDatePicker.getEditor().setBackground(Color.WHITE);
		endDatePicker.getEditor().setForeground(UIConstants.BORDER_DARK);
		endDatePicker.getEditor().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        endDatePicker.setDate(c.getTime());
		endDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        endDatePanel.add(endDatePicker);
    
		
		
		
		
		
		JLabel endLabel = new JLabel("תאריך סיום");
		endLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		endLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		endLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(endLabel, "cell 7 13,alignx right");
		
		
		//limit description field to 20 characters
		descTextField = new DTextField(20);
		descTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
			if(descTextField.getText().length()>=20)
				e.consume();
			}
		});
		descTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        

		frame.getContentPane().add(descTextField, "cell 5 15,grow");
		descTextField.setColumns(10);
		descTextField.setToolTipText("תיאור הקורס");
		
		
		JLabel insuranceLabel = new JLabel("תיאור הקורס");
		insuranceLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		insuranceLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(insuranceLabel, "cell 7 15,alignx right");
		DButton confirmButton = new DButton("הוספה",DButton.Mode.PRIMARY);
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				
				//Input validation
				Map<Integer,String> violations = cController.checkCourseAdd(nameTextField.getText(),typeComboBox.getSelectedItem().toString(), employeeComboBox.getSelectedItem().toString(),maxTextField.getText(),priceTextField.getText(),startDatePicker.getDate(),endDatePicker.getDate(),descTextField.getText());

			//if any violation found, update gui and sent an error message
                if(violations.size()>0){
                   
                    for(Integer vcode:violations.keySet()){

                    	if(vcode==cController.invalid_courseName ){
                            nameTextField.setViolatedBorder(true);
                        }
                    	
                    	
                        //if type violated
                        if(vcode==cController.type_empty ){
                            typeComboBox.setBorder(BorderFactory.createLineBorder(Color.red,3));
                        }

                        //if employee violated
                        if(vcode==cController.firstName_empty){
                        	employeeComboBox.setBorder(BorderFactory.createLineBorder(Color.red,3));
                        }
                        //if max violated
                        if(vcode==cController.max_amount_wrong){
                            maxTextField.setViolatedBorder(true);
                        }
                        
                        //if price violated
                        if(vcode==cController.price_wrong){
                            priceTextField.setViolatedBorder(true);
                        }
                        //start date violated
                        if(vcode==cController.invalid_dates){
                            startDatePicker.setBorder(BorderFactory.createLineBorder(Color.red,2));
                            endDatePicker.setBorder(BorderFactory.createLineBorder(Color.red,2));
                        }
                        //desc violated
                        if(vcode==cController.desc_empty){
                            descTextField.setViolatedBorder(true);
                        }
                    }
                    errorMessage("אנא תקן את השדות המסומנים באדום", "פרטים שגויים");
                    
                }
                //No violations found, add the course to the DB
                else {
                cController.addCourse(nameTextField.getText(),typeComboBox.getSelectedItem().toString(),employeeComboBox.getSelectedItem().toString(),maxTextField.getText(),priceTextField.getText(),startDatePicker.getDate(),endDatePicker.getDate(),descTextField.getText());		
                message("הקורס נוסף בהצלחה", "הוספה הצליחה");
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));//close window
			}
		}});
		
		cController = new CoursesController();//create course controller instance
		eController = new EmployeeController();//create employee controller instance
		updateTypes();//update course types list
		updateEmployees();//update employees list
		frame.getContentPane().add(confirmButton, "cell 5 17,grow");
		frame.setVisible(true);
		dbConnection = new diverSqlQueries();
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);// prevent closing all windows when closing this window
		
	}

}
